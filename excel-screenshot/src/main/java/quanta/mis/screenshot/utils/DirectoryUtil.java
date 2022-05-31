package quanta.mis.screenshot.utils;


import java.io.File;

public class DirectoryUtil {
    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (!file.exists()) {
            return true;
        }

        // 如果file不是一个文件
        if (!file.isFile()) {
            return false;
        }

        boolean flag = file.delete();
        if (!flag) {
            System.gc();
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 只删除目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件目录不存在
        if ((!dirFile.exists())) {
            //logger.info("目录：" + dir + "不存在！");
            return true;
        }
        // 如果dir不是一个目录
        if (!dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        return flag;
    }

    /**
     * 递归删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectoryRecursion(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件目录不存在
        if ((!dirFile.exists())) {
            //logger.info("目录：" + dir + "不存在！");
            return true;
        }

        // 如果dir不是一个目录
        if (!dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectoryRecursion(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            //logger.error("删除目录" + dir +"失败！");
            return false;
        }
        // 删除当前目录
        flag = dirFile.delete();
        //logger.info("删除目录" + dir + " "+flag);
        return flag;
    }

    /**
     * 判断文件是否存在
     * @param dir 文件路径
     * @return 是否存在
     */
    public static boolean existFile(String dir){
        File file = new File(dir);
        return file.exists() && !file.isDirectory();
    }

    /**
     * 判断文件夹是否存在
     * @param dir 文件路径
     * @return 是否存在
     */
    public static boolean existDirectory(String dir){
        File file = new File(dir);
        return file.exists() && file.isDirectory();
    }

    /**
     * 创建文件夹
     */
    public static void createDirectory(String dir) {
        File file = new File(dir);
        boolean mkdirs = file.mkdirs();
    }
}

