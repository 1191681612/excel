package quanta.mis.screenshot.VO;

import lombok.Data;

/**
 * @author by.陈
 * @since 2022/5/12 3:53
 */
@Data
public class ResultVO<T> {

    private static final long serialVersionUID = 4677857050959012953L;

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体内容.
     * 这个是通用的返回，需要使用泛型
     */
    private T data;
}
