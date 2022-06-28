package cn.tenyear.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Data
@ConfigurationProperties(prefix = "blog.jwt")
public class JwtProperties {
    private String jwtId;
    private String issuer;
    private Integer expiresAt;
    private Integer maxExpiresAt;
    private String key;
}
