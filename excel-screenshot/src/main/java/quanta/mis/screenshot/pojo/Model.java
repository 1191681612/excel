package quanta.mis.screenshot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author by.陈
 * @since 2022/5/30 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model implements Cloneable{
    /**
     * 部门的id
     */
    private Integer id;

    /**
     * 部门的名称
     */
    private String name;

    /**
     * 投入设备的总数量
     */
    private Integer inputQty;

    /**
     * 报废总金额
     */
    private Double scrapTotalAmount;
    /**
     * 平均每台金额
     */
    private Double scrapAverageAmount;
    /**
     * 部门下包含的品名集合
     */
    private List<ProductionName> productionNameList;

    /**
     * 部门存在于excel表格坐标信息
     */
    private Coordinate coordinate;

    @Override
    public Model clone() throws CloneNotSupportedException {
        Model model = (Model) super.clone();
        List<ProductionName> productionNames = model.getProductionNameList();
        if (productionNames != null) {
            ArrayList<ProductionName> productionNameArrayList = new ArrayList<>();
            productionNames.forEach(s -> {
                try {
                    productionNameArrayList.add(s.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            });
            model.setProductionNameList(productionNameArrayList);
        }
        Coordinate coordinate = model.getCoordinate();
        if (coordinate != null) {
            Coordinate clone = coordinate.clone();
            model.setCoordinate(clone);
        }
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model that = (Model) o;
        return this.productionNameList.containsAll(that.productionNameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionNameList);
    }
}
