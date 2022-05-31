package quanta.mis.screenshot.controller;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import quanta.mis.screenshot.VO.ChartVo;
import quanta.mis.screenshot.VO.ResultVO;
import quanta.mis.screenshot.config.HtmlConfig;
import quanta.mis.screenshot.exception.ScreenException;
import quanta.mis.screenshot.pojo.MailOutLookMsg;
import quanta.mis.screenshot.pojo.Pu;
import quanta.mis.screenshot.service.CreatePicService;
import quanta.mis.screenshot.service.ProcessExcelService;
import quanta.mis.screenshot.service.SendEmailService;
import quanta.mis.screenshot.utils.DirectoryUtil;
import quanta.mis.screenshot.utils.ResultVOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author by.陈
 * @since 2022/5/10 18:28
 */
@RestController
public class SendExcelController {
    @Autowired
    private ProcessExcelService processExcelServiceImpl;
    @Autowired
    private CreatePicService createPicServiceImpl;
    @Autowired
    private HtmlConfig htmlConfig;
    @Autowired
    private SendEmailService sendEmailServiceImpl;

    @PostMapping("/upload")
    public ResultVO<Object> index(Model model, MultipartFile file, String email) {
        //判断是否上传了邮件
        if (file == null || file.isEmpty()) {
            throw new ScreenException(1, "未上传excel!");
        }
        //判断是否填写了邮箱
        if (email == null || email.isEmpty() || !email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
            throw new ScreenException(1, "请输入正确邮箱!");
        }
        //获得邮件名称
        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$"))) {
            throw new ScreenException(1, "上传文件格式不正确!");
        }
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            //第1页
            int sheetNum = 0;
            int startRowNum = 0;
            Sheet sheet = workbook.getSheetAt(sheetNum);
            //读取模板文件
            HashMap<String, List<Pu>> puMap = processExcelServiceImpl.readFromExcel(sheet, startRowNum);

            ArrayList<String> list = new ArrayList<>(puMap.keySet());
            String title = list.get(0);
            List<Pu> puList = puMap.get(title);

            //获取puList的名称
            String modelName = createPicServiceImpl.getModelName(title, puList);
            String detailOutPath = htmlConfig.getDetailOutPath();
            //判断图片是否存在
            String dateDirectory = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String pngPath = detailOutPath + File.separator + dateDirectory + File.separator + modelName + ".png";
            boolean existFlag = DirectoryUtil.existFile(pngPath);
            MailOutLookMsg mailOutLookMsg = new MailOutLookMsg();
            mailOutLookMsg.setSubject(modelName);
            String[] arr = new String[]{email};
            mailOutLookMsg.setReceiver(arr);
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            if (existFlag) {
                //如果存在,直接发送图片
                mailOutLookMsg.setSubject(title);
                String[] arr1 = new String[]{email};
                mailOutLookMsg.setReceiver(arr1);
                stringStringHashMap.put(modelName, pngPath);
                mailOutLookMsg.setImageMap(stringStringHashMap);
                sendEmailServiceImpl.sendContentIncludeImgEmail(mailOutLookMsg);
                return ResultVOUtils.success();
            }
            //从数据库中读取模板对应的数据
            ArrayList<Pu> puArrayList = processExcelServiceImpl.getDataByExcelTemplate(puMap);

            //获取生成图表的信息
            ChartVo chartMsg = processExcelServiceImpl.getChartMsg(puList, puArrayList, title);

            model.addAttribute("chartVo", chartMsg);
            String htmlPath = createPicServiceImpl.createHtml("scrap", modelName, chartMsg);
            //将页面截图
            String picturePath = createPicServiceImpl.createPicture(htmlPath);
            System.out.println(picturePath);
            // 发送邮件
            mailOutLookMsg.setSubject(title);
            String[] arr1 = new String[]{email};
            mailOutLookMsg.setReceiver(arr1);
            stringStringHashMap.put(modelName, picturePath);
            mailOutLookMsg.setImageMap(stringStringHashMap);
            sendEmailServiceImpl.sendContentIncludeImgEmail(mailOutLookMsg);
            return ResultVOUtils.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
