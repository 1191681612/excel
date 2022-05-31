package quanta.mis.screenshot.service;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import quanta.mis.screenshot.VO.ChartVo;
import quanta.mis.screenshot.pojo.Pu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProcessExcelService {
    /**
     * 读取excel模板数据
     *
     * @param sheet excel的sheet对象
     * @return
     * @throws IOException
     */
    HashMap<String, List<Pu>> readFromExcel(Sheet sheet, int startRow) throws IOException;

    /**
     * 根据模板中的数据从数据库获得具体的数据
     *
     * @param puMap key为标题,value为模板数据
     * @return 从数据库中获取的所有详细数据
     */
    ArrayList<Pu> getDataByExcelTemplate(HashMap<String, List<Pu>> puMap);

//    /**
//     *将数据写入excel中
//     * @param puArrayList
//     * @param workbook
//     * @param sheet
//     * @return
//     */
//    boolean writeToExcel(ArrayList<Pu> puArrayList, Workbook workbook, Sheet sheet, int col);
//
//
    /**
     * 构建生成图表和表格thymeleaf所需要信息的对象
     * @param puListExcel excel表格的pu集合对象
     * @param puList 从数据库中查出来的pu集合对象
     * @param title excel标题
     * @return 构建thymeleaf的数据模型对象
     */
    ChartVo getChartMsg(List<Pu> puListExcel, List<Pu> puList, String title);
}
