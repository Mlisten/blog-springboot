package cn.tenyear.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@ConfigurationPropertiesScan(basePackages = "cn.tenyear.blog")
@MapperScan(basePackages = "cn/tenyear/blog/mapper")
@SpringBootApplication(scanBasePackages = "cn.tenyear.blog")
@EnableOpenApi
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
