package quanta.mis.screenshot.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanta.mis.screenshot.pojo.DayInfo;
import quanta.mis.screenshot.pojo.Pu;

import java.util.List;

/**
 * @author by.陈
 * @since 2022/5/31 0:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuListQuery {
    private DayInfo dayInfo;
    private List<Pu> puList;
}
