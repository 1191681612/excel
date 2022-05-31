package quanta.mis.screenshot.utils;


import quanta.mis.screenshot.pojo.Coordinate;
import quanta.mis.screenshot.pojo.Model;
import quanta.mis.screenshot.pojo.ProductionName;
import quanta.mis.screenshot.pojo.Pu;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by.陈
 * @since 2022/5/22 22:52
 */

public class ExcelObjectFactory {
    private final Map<String, Object> map;

    {
        map = new HashMap<>();
    }

    public static ExcelObjectFactory getInstance() {
        return new ExcelObjectFactory();
    }

    private ExcelObjectFactory() {
    }

    /**
     * 获取pu对象
     *
     * @return pu对象
     */
    public Pu getPuInstance() {
        Pu pu = (Pu) map.get("pu");
        if (pu != null) {
            try {
                return pu.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Pu pu1 = new Pu();
        map.put("pu", pu1);
        return pu1;
    }

    /**
     * 获取Model对象
     *
     * @return Model对象
     */
    public Model getModelInstance() {
        Model model = (Model) map.get("model");
        if (model != null) {
            try {
                return model.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Model model1 = new Model();
        map.put("model", model1);
        return model1;
    }

    /**
     * 获取Coordinate对象
     *
     * @return Coordinate对象
     */
    public Coordinate getCoordinateInstance() {
        Coordinate coordinate = (Coordinate) map.get("coordinate");
        if (coordinate != null) {
            try {
                return coordinate.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Coordinate coordinate1 = new Coordinate();
        map.put("coordinate", coordinate1);
        return coordinate1;
    }


    public ProductionName getProInstance() {
        ProductionName productionName = (ProductionName) map.get("ProductionName");
        if (productionName != null) {
            try {
                return productionName.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        ProductionName productionName1 = new ProductionName();
        map.put("ProductionName", productionName1);
        return productionName1;
    }

    public static Pu getPuInstance(Pu pu) {
        try {
            return pu.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Model getModelInstance(Model model) {
        try {
            return model.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProductionName getProInstance(ProductionName productionName) {
        try {
            return productionName.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Coordinate getCoordinateInstance(Coordinate coordinate) {
        try {
            return coordinate.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
