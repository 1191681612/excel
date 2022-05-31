package quanta.mis.screenshot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import quanta.mis.screenshot.config.SpringConfiguration;

@SpringBootApplication
@MapperScan("quanta.mis.screenshot.mapper")
@Import(SpringConfiguration.class)
public class ExcelScreenshotApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExcelScreenshotApplication.class, args);
	}
}
