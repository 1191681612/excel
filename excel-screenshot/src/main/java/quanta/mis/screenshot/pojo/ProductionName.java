package quanta.mis.screenshot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author by.陈
 * @since 2022/5/30 20:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionName implements Cloneable {
    /**
     * 品名的id
     */
    private Integer id;
    /**
     * 品名的名字
     */
    private String name;
    /**
     * 报废数量
     */
    private Integer scrapQty;

    /**
     * 报废率
     */
    private Double scrapRate;

    /**
     * 品名存在于excel表格坐标信息
     */
    private Coordinate coordinate;

    @Override
    public ProductionName clone() throws CloneNotSupportedException {
        ProductionName productionName = (ProductionName) super.clone();
        Coordinate coordinate = productionName.getCoordinate();
        if (coordinate != null) {
            Coordinate clone = coordinate.clone();
            productionName.setCoordinate(clone);
        }
        return productionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionName)) return false;
        ProductionName that = (ProductionName) o;
        return name == null ?(that.name == null): name.equals(that.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
