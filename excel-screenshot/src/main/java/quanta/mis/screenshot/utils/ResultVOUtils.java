package quanta.mis.screenshot.utils;


import quanta.mis.screenshot.VO.ResultVO;


public class ResultVOUtils {
    /**
     * 有返回值的
     *
     * @param object
     * @return 结果视图对象
     */
    @SuppressWarnings("rawtypes")
    public static ResultVO success(Object object) {
        ResultVO<Object> resultVO = new ResultVO<Object>();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    /**
     * 没有返回值的
     *
     * @return 结果视图对象
     */
    @SuppressWarnings("unchecked")
    public static ResultVO<Object> success() {
        return success(null);
    }

    /**
     * 错误时返回
     *
     * @param code
     * @param msg
     * @return 结果视图对象
     */
    public static ResultVO<?> error(Integer code, String msg) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
