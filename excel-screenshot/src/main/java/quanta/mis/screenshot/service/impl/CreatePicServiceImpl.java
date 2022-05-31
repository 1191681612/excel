package quanta.mis.screenshot.service.impl;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import quanta.mis.screenshot.VO.ChartVo;
import quanta.mis.screenshot.config.HtmlConfig;
import quanta.mis.screenshot.enums.ResultEnum;
import quanta.mis.screenshot.exception.ScreenException;
import quanta.mis.screenshot.pojo.Pu;
import quanta.mis.screenshot.service.CreatePicService;
import quanta.mis.screenshot.utils.DirectoryUtil;
import quanta.mis.screenshot.utils.Md5Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CreatePicServiceImpl implements CreatePicService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private HtmlConfig htmlConfig;

    @Override
    public String createHtml(String templateName, String name, ChartVo chartVo) {
        //html页面存放的根目录路径
        String detailOutPath = htmlConfig.getDetailOutPath();
        //html页面的上级文件夹的名称
        String dateDirectoryName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //html页面的上级文件夹的路径
        String dateDirectoryPath = detailOutPath + File.separator + dateDirectoryName;
        // 3.创建文件,输出
        File file = new File(detailOutPath+File.separator+dateDirectoryName+File.separator+name+".html");
        //判断文件夹是否存在
        boolean flagExist = DirectoryUtil.existDirectory(dateDirectoryPath);
        if(!flagExist){
            //如果不存在,就创建
            DirectoryUtil.createDirectory(dateDirectoryPath);
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            // 1.获取页面的数据
            Map<String, Object> data = new HashMap<>();
            data.put("chartVo", chartVo);
            Context thymeLeafContext = new Context();
            // 2.将数据放入上下文
            thymeLeafContext.setVariables(data);
            // 4.执行页面静态化
            templateEngine.process(templateName, thymeLeafContext, printWriter);
        } catch (Exception e) {
            throw new ScreenException(ResultEnum.HTML_CREATE_FAIL);
        }
        return file.getAbsolutePath();
    }
    @Override
    public String getModelName(String title, List<Pu> puList){
        //将对象转为字符串
        String puListStr = String.valueOf(puList);
        //获得请求时候的日期
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return Md5Utils.getStringHashValue(dateTime + title + puListStr);
    }

    @Override
    public String createPicture(String htmlPath) {
        String driverClassPath = htmlConfig.getDriverClassPath();
        String chromePath = htmlConfig.getChromePath();
        System.setProperty("webdriver.chrome.driver",driverClassPath);
        System.setProperty("webdriver.chrome.bin",chromePath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(htmlPath);
        String jsHeight = "return document.body.clientHeight";
        long height = (long) driver.executeScript(jsHeight);
        int k = 1;
        int size = 500;
        while (k * size < height) {
            String jsMove = String.format("window.scrollTo(0,%s)", k * 500);
            driver.executeScript(jsMove);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            height = (long) driver.executeScript(jsHeight);
            k += 1;
        }

        // 通过执行脚本解决Selenium截图不全问题
        long maxWidth = (long) driver.executeScript(
                "return Math.max(document.body.scrollWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth);");
        long maxHeight = (long) driver.executeScript(
                "return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
        Dimension targetSize = new Dimension((int)maxWidth, (int)maxHeight);
        driver.manage().window().setSize(targetSize);
        File img = driver.getScreenshotAs(OutputType.FILE);
        String picPath = htmlPath.substring(0, htmlPath.lastIndexOf("."))+".png";
        try {
            FileUtils.copyFile(img, new File(picPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picPath;
    }
}