package quanta.mis.screenshot.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import quanta.mis.screenshot.pojo.Coordinate;

import java.util.*;

public class ExcelUtils {
    private final ExcelObjectFactory excelObjectFactory;

    {
        excelObjectFactory = ExcelObjectFactory.getInstance();
    }

    private ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        return new ExcelUtils();
    }

    /**
     * 获取department对象，只有1行2列
     *
     * @param sheet sheet对象
     * @param row   行号
     * @param col   列号
     * @return
     */
    public HashMap<String, Coordinate> getTwoPreColValueByUnMerged(Sheet sheet, int row, int col) {
        //获得第row行，第col(0)列数据 即部门department名字
        String firstName = getString(sheet, row, col);
        //获得第row行，第col(1)列数据 即品名description名字
        String secondName = getString(sheet, row, col + 1);
        //如果没有数据,直接返回null
        if (firstName.isEmpty() && secondName.isEmpty()) {
            return null;
        }
        HashMap<String, Coordinate> stringStringHashMap = new HashMap<>();
        ArrayList<Coordinate> stringArrayList = new ArrayList<>();
        //创建包含第一列坐标信息的对象
        Coordinate coordinateInstance = excelObjectFactory.getCoordinateInstance();
        coordinateInstance.setAllTheInfo(row, row, col, col, firstName, stringArrayList);
        //创建包含第二列坐标信息的对象
        Coordinate coordinateInstance1 = excelObjectFactory.getCoordinateInstance();
        coordinateInstance1.setAllTheInfo(row, row, col + 1, col + 1, secondName, null);
        stringArrayList.add(coordinateInstance1);
        stringStringHashMap.put(firstName, coordinateInstance);
        return stringStringHashMap;
    }

    /**
     * 获取excel第firstRow+row行的前两列的数据
     *
     * @param sheet
     * @param row   从第几行开始
     * @return 一个map集合, 包含excel表格前两列的数据, key为第一列, value为第二列
     */
    public Map<String, Coordinate> getTwoPreviousValue(Sheet sheet, int row) {
        //得到第一行的行号
        int firstRowNum = sheet.getFirstRowNum();

        //获取总行数
        int lastRowNum = sheet.getLastRowNum();
        HashMap<String, Coordinate> map = new LinkedHashMap<>();

        //循环遍历excel第firstRow+2行的前两列的数据
        for (int row1 = firstRowNum + 2; row1 < lastRowNum; ) {
            //判断是否是合并单元格
            HashMap<Boolean, Coordinate> inMergedMap = isInMerged(sheet, row1, 0);
            boolean isMerged = inMergedMap.containsKey(true);
            if (isMerged) {
                Coordinate coordinate = inMergedMap.get(isMerged);
                //是合并单元格,获取excel前两列内容
                Map<String, Coordinate> twoPreColValueByMerged = getTwoPreColValueByMerged(sheet, coordinate);
                row1 = twoPreColValueByMerged.get("row").getFirstRow();
                twoPreColValueByMerged.remove("row");
                Set<String> strings = twoPreColValueByMerged.keySet();
                strings.forEach(s -> {
                    Coordinate coordinate1 = twoPreColValueByMerged.get(s);
                    map.put(s, coordinate1);
                });
                continue;
            }
            //不是合并单元格,获取前2列的数据
            HashMap<String, Coordinate> twoPreColValueByUnMerged = getTwoPreColValueByUnMerged(sheet, row1, 0);
            if (twoPreColValueByUnMerged == null) {
                row1++;
                continue;
            }
            Set<String> strings = twoPreColValueByUnMerged.keySet();
            strings.forEach(s -> {
                Coordinate coordinate = twoPreColValueByUnMerged.get(s);
                map.put(s, coordinate);
            });
            row1++;
        }
        return map;
    }

    /**
     * 获取excel表格前两列的值,默认从第三行开始
     *
     * @param sheet      sheet页对象
     * @param coordinate 合并单元格的坐标对象
     * @return 单元格的值
     */
    public Map<String, Coordinate> getTwoPreColValueByMerged(Sheet sheet, Coordinate coordinate) {
        HashMap<String, Coordinate> map = new LinkedHashMap<>();
        int firstRow = coordinate.getFirstRow();
        int lastRow = coordinate.getLastRow();
        //判断是横向合并还是纵向合并
        int direction = mergeDirection(coordinate);
        //纵向合并
        if (direction == 0) {
            //获取第一列合并单元格的值
            String firstValue = getString(sheet, firstRow, 0);
            ArrayList<Coordinate> secondCoordinateList = new ArrayList<>();
            //存储部门坐标对象的坐标信息
            coordinate.setCellValue(firstValue);
            //获取第一列部门所属的所有品名
            for (int i = firstRow; i <= lastRow; i++) {
                String secondValue = getString(sheet, i, 1);
                try {
                    //存品名对象的坐标信息,即第二列的坐标信息
                    Coordinate clone = coordinate.clone();
                    clone.setFirstRow(i);
                    clone.setLastRow(i);
                    clone.setFirstCol(1);
                    clone.setLastCol(1);
                    clone.setCellValue(secondValue);
                    clone.setCoordinateList(null);
                    secondCoordinateList.add(clone);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            coordinate.setCoordinateList(secondCoordinateList);
            map.put(firstValue, coordinate);
        }
        //横向合并,
        if (direction == 1) {
            ArrayList<Coordinate> secondCoordinateList = new ArrayList<>();
            //说明部门和产品的值一样，获取合并单元格第一行第一列的值即为前两列的值
            String firstValue = getString(sheet, firstRow, 0);
            //横向合并将第一列和第二列坐标的value都设置为第一列的值
            coordinate.setCellValue(firstValue);
            try {
                Coordinate clone = coordinate.clone();
                clone.setCoordinateList(null);
                clone.setFirstCol(1);
                clone.setLastCol(1);
                clone.setCellValue(firstValue);
                secondCoordinateList.add(clone);
                coordinate.setCoordinateList(secondCoordinateList);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            map.put("-1", coordinate);
        }
        try {
            Coordinate clone = coordinate.clone();
            clone.setFirstRow(lastRow + 1);
            //从新的一行开始循环获取
            map.put("row", clone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取excel表格的标题
     *
     * @param sheet excel表格的对象
     * @return 标题
     */
    public static String getTitle(Sheet sheet) {
        XSSFDrawing xssfDrawing = (XSSFDrawing) sheet.createDrawingPatriarch();
        List<XSSFChart> charts = xssfDrawing.getCharts();
        CTChart ctChart = charts.get(0).getCTChart();
        CTTextParagraph[] pArray = ctChart.getTitle().getTx().getRich().getPArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (CTTextParagraph paragraph : pArray) {
            CTRegularTextRun[] runs = paragraph.getRArray();
            for (CTRegularTextRun run : runs) {
                String title = run.getT();
                stringBuilder.append(title);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取单元格的值
     *
     * @param sheet sheet页对象
     * @param row   行号
     * @param col   列号
     * @return 单元格的值
     */
    public static String getString(Sheet sheet, int row, int col) {
        Row row1 = sheet.getRow(row);
        Cell cell = row1.getCell(col);
        CellType cellType = cell.getCellType();
        String strCell;
        switch (cellType) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            case FORMULA:
                try {
                    strCell = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    strCell = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                strCell = "";
                break;
        }
        return strCell;
    }

    ;

    /**
     * 判断某个单元格是否是合并单元格
     *
     * @param sheet  sheet页对象
     * @param row    行号
     * @param column 列号
     * @return 一个map集合, key为flag  true:value的值为合并单元格的坐标, false:value值为null
     */
    public HashMap<Boolean, Coordinate> isInMerged(Sheet sheet, int row, int column) {
        HashMap<Boolean, Coordinate> map = new HashMap<>();
        boolean flag = false;
        //获得合并单元格的数量
        int sheetMergeCount = sheet.getNumMergedRegions();
        ExcelObjectFactory excelObjectFactory = ExcelObjectFactory.getInstance();
        //遍历所有的合并单元格，判断某个单元格是否属于这个合并单元格
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    String value = getString(sheet, row, column);
                    Coordinate coordinateInstance = excelObjectFactory.getCoordinateInstance();
                    coordinateInstance.setAllTheInfo(firstRow, lastRow, firstColumn, lastColumn, value, null);
                    flag = true;
                    map.put(flag, coordinateInstance);
                    return map;
                }
            }
        }
        map.put(flag, null);
        return map;
    }

    /**
     * 判断是横向合并还是纵向合并
     *
     * @param coordinate 合并单元格的坐标信息
     * @return 数字0:纵向合并  数字1:横向合并 数字2:不是合并单元格
     */
    public static int mergeDirection(Coordinate coordinate) {
        //判断是横向合并还是纵向合并
        int firstRow = coordinate.getFirstRow();
        int lastRow = coordinate.getLastRow();
        int firstCol = coordinate.getFirstCol();
        int lastCol = coordinate.getLastCol();
        if (firstCol == lastCol || lastRow - firstRow > 1) {
            //纵向合并
            return 0;
        }
        if (firstRow == lastRow || lastCol - firstCol > 1) {
            //横向合并
            return 1;
        }
        return 2;
    }

    /**
     * 将数据写入到excel表格中
     *
     * @param row   行号
     * @param col   列号
     * @param sheet sheet对象
     * @param value 要输入的Integer整数对象
     */
    public static void writeDataToExcel(int row, int col, Sheet sheet, Integer value) {
        Row row1 = sheet.getRow(row);
        if (row1 != null) {
            Cell cell = row1.getCell(col);
            if (cell != null && value != null && value != 0) {
                cell.setCellValue(value);
            }
        }
    }

    /**
     * 将数据写入到excel表格中
     *
     * @param row   行号
     * @param col   列号
     * @param sheet sheet对象
     * @param value 要输入的Double类型对象
     */
    public static void writeDataToExcel(int row, int col, Sheet sheet, Double value) {
        Row row1 = sheet.getRow(row);
        if (row1 != null) {
            Cell cell = row1.getCell(col);
            if (cell != null && value != null && value != 0.0) {
                cell.setCellValue(value);
            }
        }
    }

    /**
     * 将数据以百分号的形式写入到excel表格中
     *
     * @param row   行号
     * @param col   列号
     * @param sheet sheet对象
     * @param value 要输入的数字,double类型
     */
    public static void writeDataToExcel(int row, int col, Double value, Workbook workbook, Sheet sheet) {
        Row row1 = sheet.getRow(row);
        if (row1 != null) {
            Cell cell = row1.getCell(col);
            if (cell != null && value != null && value != 0) {
                CellStyle style = workbook.createCellStyle();
                style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
                //获取单元格的默认字体
                XSSFFont font = ((XSSFCell) cell).getCellStyle().getFont();
                //设置单元格的字体
                style.setFont(font);
                //设置单元格文字左右居中
                style.setAlignment(HorizontalAlignment.CENTER);
                //设置单元格上下居中
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                //下边框
                style.setBorderBottom(BorderStyle.MEDIUM);
                //左边框
                style.setBorderLeft(BorderStyle.THIN);
                //上边框
                style.setBorderTop(BorderStyle.MEDIUM);
                //右边框
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                cell.setCellValue(value);
            }
        }
    }


}
