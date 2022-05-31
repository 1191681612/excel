package quanta.mis.screenshot.service;




import quanta.mis.screenshot.VO.ChartVo;
import quanta.mis.screenshot.pojo.Pu;

import java.util.List;

public interface CreatePicService {
    /**
     * 根据thymeleaf模板创建html页面
     * @param templateName 模板在template文件下的名称
     * @param name html页面的名字
     * @param chartVo thymelef模板需要的数据模型
     * @return html页面的本地地址
     */
     String createHtml(String templateName, String name, ChartVo chartVo);

    /**
     * 得到excel数据对象的md5字符串
     * @param title excel表格图表的标题
     * @param puList
     * @return
     */
    String getModelName(String title, List<Pu> puList);

    String createPicture(String htmlPath);
}
