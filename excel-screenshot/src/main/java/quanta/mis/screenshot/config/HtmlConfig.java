package quanta.mis.screenshot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import quanta.mis.screenshot.service.impl.CreatePicServiceImpl;

/**
 * @author by.陈
 * @since 2022/5/30 22:06
 */
@Configuration
@PropertySource("classpath:application.yml")
public class HtmlConfig {
    private static String outPath = "";

    static {
        outPath = CreatePicServiceImpl.class.getResource("/").getPath();
    }

    @Value("${html.out.path}")
    private String outPutDirectory;
    @Value("${selenium.driverClassPath}")
    private String driverClassPath;
    @Value("${selenium.chromePath}")
    private String chromePath;

    public String getDetailOutPath() {
        return outPath + outPutDirectory;
    }

    public String getOutPutDirectory() {
        return outPutDirectory;
    }

    public String getDriverClassPath() {
        return driverClassPath;
    }

    public String getChromePath() {
        return chromePath;
    }
}
