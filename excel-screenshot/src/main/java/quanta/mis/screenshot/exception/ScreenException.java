package quanta.mis.screenshot.exception;

import lombok.Getter;
import quanta.mis.screenshot.enums.ResultEnum;

/**
 * @author by.陈
 * @since 2022/5/12 3:56
 */
@Getter
public class ScreenException extends RuntimeException {
    private Integer code;

    public ScreenException() {
        super();
    }

    public ScreenException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public ScreenException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
