package quanta.mis.screenshot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pu implements Cloneable {
    /**
     * pu的id
     */
    private Integer id;
    /**
     * pu的名称
     */
    private String name;


    /**
     * pu包含的部门list集合
     */
    private List<Model> modelList;

    @Override
    public Pu clone() throws CloneNotSupportedException {
        Pu pu = (Pu) super.clone();
        List<Model> departments1 = pu.getModelList();
        if (departments1 != null) {
            ArrayList<Model> list = new ArrayList<>();
            departments1.forEach(s -> {
                try {
                    list.add(s.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            });
            pu.setModelList(list);
        }
        return pu;
    }
}
