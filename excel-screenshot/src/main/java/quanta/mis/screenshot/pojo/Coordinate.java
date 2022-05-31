package quanta.mis.screenshot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate implements Cloneable {
    /**
     * 开始行
     */
    private Integer firstRow;
    /**
     * 结束行
     */
    private Integer lastRow;
    /**
     * 开始列
     */
    private Integer firstCol;
    /**
     * 结束列
     */
    private Integer lastCol;
    /**
     * 单元格的信息
     */
    private String cellValue;

    private List<Coordinate> coordinateList;

    @Override
    public Coordinate clone() throws CloneNotSupportedException {
        Coordinate coordinate = (Coordinate) super.clone();
        coordinate.setCoordinateList(null);
        return coordinate;
    }

    public void setAllTheInfo(int firstRow, int lastRow, int firstCol, int lastCol, String cellValue, List<Coordinate> coordinateList) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.cellValue = cellValue;
        if (coordinateList != null) {
            this.coordinateList = coordinateList;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        List<Coordinate> coordinateList = that.getCoordinateList();
        boolean coordinateListFlag = true;
        if (coordinateList != null && this.coordinateList != null) {
            coordinateListFlag = this.coordinateList.containsAll(coordinateList);
        }
        return (firstRow.equals(that.firstRow)) && lastRow.equals(that.lastRow) && firstCol.equals(that.firstCol) && lastCol.equals(that.lastCol) && cellValue.equals(that.cellValue) && coordinateListFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstRow, lastRow, firstCol, lastCol, cellValue, coordinateList);
    }
}
