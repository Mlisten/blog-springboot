package cn.tenyear.blog.utils;

import cn.tenyear.blog.config.JwtProperties;
import cn.tenyear.blog.modle.entity.UserEntity;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
@Log4j2
@Component
public class JwtUtil {
    @Autowired
    private JwtProperties properties;

    /**
     * 创建token
     *
     * @param user 用户信息
     * @return token字符串
     */
    public String create(UserEntity user) {
        user.setPassword(null);
        log.info("用户 {} 成功创建token,创建时间 {}", user.getUsername(), DateUtil.format());
        return JWT.create()
                //代表这个JWT的签发主体
                .withIssuer(properties.getIssuer())
                //代表这个JWT的主体，即它的所有人；
                .withSubject(JSON.toJSONString(user))
                //代表这个JWT的过期时间；
                .withExpiresAt(DateUtil.addMinutes(properties.getExpiresAt()))
                .withIssuedAt(new Date())
                //是JWT的唯一标识。
                .withJWTId(properties.getJwtId())
                .sign(Algorithm.HMAC256(properties.getKey().getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 创建token
     *
     * @param map 自定义的 Subject
     * @return token字符串
     */
    public String create(Map<String, Object> map) {
        return JWT.create()
                //代表这个JWT的签发主体
                .withIssuer(properties.getIssuer())
                //代表这个JWT的主体，即它的所有人；
                .withSubject(JSON.toJSONString(map))
                //代表这个JWT的过期时间；
                .withExpiresAt(DateUtil.addMinutes(properties.getExpiresAt()))
                .withIssuedAt(new Date())
                //是JWT的唯一标识。
                .withJWTId(properties.getJwtId())
                .sign(Algorithm.HMAC256(properties.getKey().getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 创建token
     *
     * @param subject 自定义的 Subject
     * @return token字符串
     */
    public String create(String subject) {
        return JWT.create()
                //代表这个JWT的签发主体
                .withIssuer(properties.getIssuer())
                //代表这个JWT的主体，即它的所有人；
                .withSubject(subject)
                //代表这个JWT的过期时间；
                .withExpiresAt(DateUtil.addMinutes(properties.getExpiresAt()))
                .withIssuedAt(new Date())
                //是JWT的唯一标识。
                .withJWTId(properties.getJwtId())
                .sign(Algorithm.HMAC256(properties.getKey().getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 验证token是否合法
     *
     * @param token
     * @return
     */
    public DecodedJWT verify(String token) throws JWTVerificationException {
        JWTVerifier build = JWT.require(Algorithm.HMAC256(properties.getKey().getBytes(StandardCharsets.UTF_8)))
                .withIssuer(properties.getIssuer())
                .withJWTId(properties.getJwtId())
                .build();
        return build.verify(token);
    }

    /**
     * 直接解析token
     *
     * @param token
     * @return
     */
    public DecodedJWT decode(String token) {
        return JWT.decode(token);
    }
}

