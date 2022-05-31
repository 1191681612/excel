package quanta.mis.screenshot.handler;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import quanta.mis.screenshot.VO.ResultVO;
import quanta.mis.screenshot.exception.ScreenException;
import quanta.mis.screenshot.utils.ResultVOUtils;


/**
 * @author by.陈
 * @since
 */
@RestControllerAdvice
public class ScreenExceptionHandler {
    @ExceptionHandler(value = ScreenException.class)
    @ResponseBody
    public ResultVO handleSellException(ScreenException e) {
        e.printStackTrace();
        return ResultVOUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handleSellException(Exception e) {
        e.printStackTrace();
        return ResultVOUtils.error(500,"系统异常!");
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResultVO handleSellException(RuntimeException e) {
        e.printStackTrace();
        return ResultVOUtils.error(500,"系统异常!");
    }
}
