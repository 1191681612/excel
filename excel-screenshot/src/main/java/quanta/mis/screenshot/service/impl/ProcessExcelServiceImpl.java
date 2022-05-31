package quanta.mis.screenshot.service.impl;

import quanta.mis.screenshot.VO.ChartVo;
import quanta.mis.screenshot.exception.ScreenException;

import quanta.mis.screenshot.mapper.ScrapMapper;
import quanta.mis.screenshot.pojo.*;
import quanta.mis.screenshot.pojo.query.PuListQuery;
import quanta.mis.screenshot.service.ProcessExcelService;
import quanta.mis.screenshot.utils.CommonUtils;
import quanta.mis.screenshot.utils.ExcelObjectFactory;
import quanta.mis.screenshot.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessExcelServiceImpl implements ProcessExcelService {
    @Autowired
    private ScrapMapper scrapMapper;

    @Override
    public HashMap<String, List<Pu>> readFromExcel(Sheet sheet, int startRow) throws IOException {
        HashMap<String, List<Pu>> stringListHashMap = new HashMap<>();
        //获取excel标题
        String title = ExcelUtils.getTitle(sheet);

        List<Pu> puList = new ArrayList<>();

        //得到第一行的行号
        int firstRowNum = sheet.getFirstRowNum();
        //获取第一行的row
        Row firstRow = sheet.getRow(firstRowNum);
        //获取第一列的列号
        short firstCol = firstRow.getFirstCellNum();
        //获取总列数
        short totalCol = firstRow.getLastCellNum();

        ExcelObjectFactory excelObjectFactory = ExcelObjectFactory.getInstance();
        //获取第一行所有的pu并创建pu对象加入到puList集合中
        for (int col = firstCol + 2; col < totalCol; col++) {
            String puName = ExcelUtils.getString(sheet, firstRowNum, col);
            if (puName != null && !"".equals(puName)) {
                //创建pu对象
                Pu puInstance = excelObjectFactory.getPuInstance();
                puInstance.setName(puName);
                puList.add(puInstance);
            }
        }
        //获取excel表格前两列的数据
        ExcelUtils excelUtils = ExcelUtils.getInstance();
        Map<String, Coordinate> twoPreviousValue = excelUtils.getTwoPreviousValue(sheet, startRow);
        ArrayList<Model> modelArrayList = new ArrayList<>();
        //遍历前两列数据的map,key为部门的名称,value为coordinate坐标信息,
        twoPreviousValue.forEach((department, coordinate) -> {
            List<Coordinate> coordinateList = coordinate.getCoordinateList();
            //说明没有品名的值
            if (coordinateList.size() == 0) {
                Model modelInstance = excelObjectFactory.getModelInstance();
                modelInstance.setName(null);
                modelArrayList.add(excelObjectFactory.getModelInstance());
            }
            //有部门有品名
            Model modelInstance = excelObjectFactory.getModelInstance();
            ArrayList<ProductionName> productionNameList = new ArrayList<>();
            //为每个部门的productionName赋值
            if (coordinateList.size() >= 1) {
                coordinateList.forEach(coordinate1 -> {
                    ProductionName proInstance = excelObjectFactory.getProInstance();
                    proInstance.setName(coordinate1.getCellValue());
                    proInstance.setCoordinate(coordinate1);
                    productionNameList.add(proInstance);
                });

                modelInstance.setName(coordinate.getCellValue());
            }
            modelInstance.setProductionNameList(productionNameList);
            try {
                Coordinate clone = coordinate.clone();
                clone.setCoordinateList(null);
                modelInstance.setCoordinate(clone);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            modelArrayList.add(modelInstance);
        });
        //循环遍历puList集合,为每个pu的部门model属性赋值
        for (Pu pu : puList) {
            ArrayList<Model> list = new ArrayList<>();
            modelArrayList.forEach(s -> {
                list.add(ExcelObjectFactory.getModelInstance(s));
            });
            pu.setModelList(list);
        }
        stringListHashMap.put(title, puList);
        return stringListHashMap;
    }

    @Override
    public ArrayList<Pu> getDataByExcelTemplate(HashMap<String, List<Pu>> puMap) {
        String title = null;
        List<Pu> puList = null;
        Set<Map.Entry<String, List<Pu>>> entries = puMap.entrySet();
        for (Map.Entry<String, List<Pu>> entry : entries) {
            title = entry.getKey();
            puList = entry.getValue();
        }
        if (title == null || puList == null) {
            throw new ScreenException(1, "写入数据失败!模板没有标题");
        }
        Integer[] date = CommonUtils.getMonth(title);
        int year = date[0];
        int month = date[1];
        if (month == 0) {
            throw new ScreenException(1, "写入数据失败!模板标题月份错误");
        }
        DayInfo dayInfo = new DayInfo(year, month);
        //通过puList,查询数据
        PuListQuery puListQuery = new PuListQuery(dayInfo, puList);

        return scrapMapper.findAllPuData(puListQuery);
    }

    /**
     * 构造需要的图表信息对象
     *
     * @param puListExcel 表格对象的所有信息
     * @return 图表对象信息
     */
    @Override
    public ChartVo getChartMsg(List<Pu> puListExcel, List<Pu> puList, String title) {
        //图表的二级标题,即部门的名称, ["", "", "", "{offset|}FA", "", "", "", "", "", "SMT", "", " CAMERA"];
        ArrayList<String> xAxisGroupData = new ArrayList<>();
        //所有的Pu名称
        List<String> legendData = puListExcel.stream().map(Pu::getName).collect(Collectors.toList());
        //图表x轴一级坐标,即品名的名称
        List<String> xAxisData = new ArrayList<>();
        //辅助计算分组分割线的位置
        Map<String, Integer> map = new LinkedHashMap<>();

        //纵坐标的值,即报废率
        ArrayList<ArrayList<Double>> seriesData = new ArrayList<>();

        //将数据库中查出来的数据集合按照excel中pu的名字排序
        List<String> listExcelName = puListExcel.stream().map(Pu::getName).collect(Collectors.toList());
        sortList(listExcelName,puList);
        //从数据库中查出来的聚合
        List<List<Model>> collect1 = puList.stream().map(Pu::getModelList).collect(Collectors.toList());
        //excel获取的集合
        List<List<Model>> allModel = puListExcel.stream().map(Pu::getModelList).collect(Collectors.toList());


        for (int i = 0; i < allModel.size(); i++) {
            //从数据库中查出来的集合,第几个pu中所有的department集合
            List<Model> modelList1 = collect1.get(i);
            List<Model> modelList = allModel.get(i);
            //判断departmentList1中是否包含department,如果包含,就将第一个集合的department中包含的数据设置为第二个中的
            modelList = modelList.stream().
                    peek(m -> modelList1.stream()
                            .filter(s -> {
                                if (s.getName() == null && m.getName() != null || s.getName() != null && m.getName() == null) {
                                    return false;
                                }
                                List<ProductionName> productionNameList = s.getProductionNameList();
                                List<ProductionName> productionNameList1 = m.getProductionNameList();
                                if (productionNameList == null || productionNameList1 == null) {
                                    return false;
                                }

                                return (m.getName() == null && s.getName() == null && productionNameList1.containsAll(productionNameList)) || (m.getName().equals(s.getName()) && productionNameList1.containsAll(productionNameList));
                            }).forEach(s1 -> {
                                m.getProductionNameList().stream()
                                        .peek(mDesc -> s1.getProductionNameList().stream()
                                                .filter(s1Des -> {
                                                    if (s1Des.getName() == null && mDesc.getName() != null || s1Des.getName() != null && mDesc.getName() == null) {
                                                        return false;
                                                    }
                                                    return s1Des.getName().equals(mDesc.getName());
                                                }).forEach(t -> {
                                                    mDesc.setName(t.getName());
                                                    mDesc.setCoordinate(t.getCoordinate());
                                                    mDesc.setId(t.getId());
                                                    mDesc.setScrapQty(t.getScrapQty());
                                                    mDesc.setScrapRate(t.getScrapRate());
                                                })
                                        ).collect(Collectors.toList());
                                m.setCoordinate(s1.getCoordinate());
                                m.setInputQty(s1.getInputQty());
                                m.setScrapTotalAmount(s1.getScrapTotalAmount());
                                m.setScrapAverageAmount(s1.getScrapAverageAmount());
                            }))
                    .collect(Collectors.toList());

            ArrayList<Double> seriesDataList = new ArrayList<>();

            for (Model model : modelList) {
                int size1 = xAxisGroupData.size();

                if (i == 0) {
                    int size = model.getProductionNameList().size();
                    for (int i1 = 0; i1 < size; i1++) {
                        xAxisGroupData.add("");
                    }
                    //判断size是否为偶数 构造此对象["", "", "", "{offset|}FA", "", "", "", "", "", "SMT", "", " CAMERA"]
                    if (size % 2 == 0) {
                        //如果是偶数,就将集合中第(size/2-1)的索引处元素改为"{offset}|部门名称"
                        int index = size / 2 - 1 + size1;
                        xAxisGroupData.set(index, "{offset|}" + model.getName());
                    } else {
                        //如果是奇数,就将集合中第(size/2)的索引处元素改为 "部门名称"
                        int index = size / 2 + size1;
                        String departmentName = model.getName();
                        xAxisGroupData.set(index, departmentName == null ? "" : departmentName);
                    }
                    List<String> collect = model.getProductionNameList().stream().map(ProductionName::getName).collect(Collectors.toList());
                    xAxisData.addAll(collect);
                    if (model.getName() == null && collect.size() != 0) {
                        xAxisData.set(xAxisData.size() - 1, "");
                        xAxisGroupData.set(xAxisGroupData.size() - 1, collect.get(0));
                    }
                    map.put(model.getName(), xAxisGroupData.size());
                }


                List<ProductionName> productionNames = model.getProductionNameList();
                productionNames.forEach(productionName -> {
//                    seriesDataList.add(null);
                    String name = productionName.getName();
                    if (name == null) {
                        seriesDataList.add(null);
                    } else {
                        Double scrapRate = productionName.getScrapRate();
                        seriesDataList.add(scrapRate);
                    }
                });

            }
            //如果excel表格中的部门集合中含有查出来的部门,就将部门的数据存储到excel中
            seriesData.add(seriesDataList);
        }
        //分组分割线
        int size = xAxisData.size() + 1;
        boolean[] groupSeparates = new boolean[size];
        //为数组的每一个值赋值为false
        Arrays.fill(groupSeparates, false);
        //将第一个和最后一根线设置显示
        groupSeparates[0] = true;
        groupSeparates[size - 1] = true;
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            Integer value = entry.getValue();
            //根据x轴第二级分类设置分组分割线
            if (value <= groupSeparates.length - 1) {
                groupSeparates[value] = true;
            }
        }
        ChartVo chartVo = new ChartVo();
        chartVo.setTitle(title);
        chartVo.setGroupSeparates(groupSeparates);
        chartVo.setLegendData(legendData);
        chartVo.setSeriesData(seriesData);
        chartVo.setXAxisData(xAxisData);
        chartVo.setXAxisGroupData(xAxisGroupData);
        chartVo.setPuList(puListExcel);
        return chartVo;
    }


    /**
     * 按照集合名字给集合排序
     * @param bigListName 参照集合的名字
     * @param smallLit  待排序集合
     */
    private void sortList(List<String> bigListName,List<Pu> smallLit){
        smallLit.sort((p1, p2) -> {

            int io1 = bigListName.indexOf(p1.getName());
            int io2 = bigListName.indexOf(p2.getName());

            if (io1 != -1) {
                io1 = smallLit.size() - io1;
            }
            if (io2 != -1) {
                io2 = smallLit.size() - io2;
            }
            return io2 - io1;
        });
    }

}
