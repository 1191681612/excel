package quanta.mis.screenshot.utils;


public class CommonUtils {
    private static String[] strArray = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    private static Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    /**
     * 根据输入的日期输出月份
     *
     * @param str "2022年四月各PU报废率"
     * @return
     */
    public static Integer[] getMonth(String str) {
        int yearIndex = str.indexOf("年");
        int year = Integer.parseInt(str.substring(0, yearIndex));
        int monthIndex = str.indexOf("月");
        String monthStr = str.substring(yearIndex + 1, monthIndex);
        int month = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i].equals(monthStr)) {
                month = intArray[i];
            }
        }
        return new Integer[]{year,month};
    }
}