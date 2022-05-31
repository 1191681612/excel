package quanta.mis.screenshot.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;

@Configurable
@Import({HtmlConfig.class,QuartzConfig.class})
public class SpringConfiguration {

}
