package quanta.mis.screenshot.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanta.mis.screenshot.pojo.Pu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author by.陈
 * @since 2022/5/22 9:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartVo {
    /**
     * 图表的标题(即excel的标题)
     */
    private String title;
    /**
     *
     */
    private List<String> legendData;

    /**
     * 图表的x轴一级横坐标(即品名)
     *
     */
    private List<String> xAxisData;
    /**
     * 图表的x轴二级标题(即部门)
     */
    private List<String> xAxisGroupData;
    /**
     * 图表的y轴(即报废率)
     */
    private List<ArrayList<Double>> seriesData;
    /**
     * 图表的x轴二级标题的刻度线,true表示显示,false表示隐藏
     */
    private boolean[] groupSeparates;

    /**
     * 图表的前两列数据,key为第一列,value为第二列
     */
    private HashMap<String,List<String>> previousCol;
    /**
     *
     */
    private List<Pu> puList;


}
