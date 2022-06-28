package cn.tenyear.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
@ConfigurationProperties(prefix = "blog.site")
public class SiteProperties {
    private String url;
    private String index;
    private String login;
    private Integer port;
}
