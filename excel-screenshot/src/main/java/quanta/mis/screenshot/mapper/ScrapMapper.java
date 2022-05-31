package quanta.mis.screenshot.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import quanta.mis.screenshot.pojo.Pu;
import quanta.mis.screenshot.pojo.query.PuListQuery;

import java.util.ArrayList;

/**
 * @author by.陈
 * @since 2022/5/30 22:25
 */
@Repository
public interface ScrapMapper {
    /**
     * 根据excel模板的pu集合查询数据库中对应的信息
     * @param puListQuery excel模板中的pu信息
     * @return  数据库对应的pu集合
     */
    ArrayList<Pu> findAllPuData(@Param("puListQuery") PuListQuery puListQuery);
}
