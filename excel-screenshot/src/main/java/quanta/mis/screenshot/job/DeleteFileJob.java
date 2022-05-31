package quanta.mis.screenshot.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import quanta.mis.screenshot.config.HtmlConfig;
import quanta.mis.screenshot.utils.DirectoryUtil;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DeleteFileJob  {
    @Autowired
    private HtmlConfig htmlConfig;
    protected void executeInternal() throws JobExecutionException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String outPutDirectory = htmlConfig.getOutPutDirectory();
        //判断目录下是否存在该目录
        URL resource = this.getClass().getResource("/");
        if (resource != null) {
            String filePath = resource.getPath()  + outPutDirectory + File.separator + format;
            //递归删除该文件夹下的所有内容及该文件夹
            DirectoryUtil.deleteDirectoryRecursion(filePath);
        }
    }
}
