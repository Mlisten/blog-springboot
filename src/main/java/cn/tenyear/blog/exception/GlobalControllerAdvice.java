package cn.tenyear.blog.exception;

import cn.tenyear.blog.config.JwtProperties;
import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.utils.*;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
@Log4j2
@RestControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private JwtUtil jwtUtil;

    @ExceptionHandler({NumberFormatException.class, IllegalArgumentException.class})
    public R errorParameters(Exception e) {
        return R.error(BlogExceptionEnum.BODY_NOT_MATCH);
    }

    /**
     * token自带的令牌过期异常类
     */
    @ExceptionHandler(TokenExpiredException.class)
    public R tokenExpiredException(TokenExpiredException e) {
        log.warn("令牌已过期" + e);
        HttpServletRequest request = SpringBootUtil.getHttpServletRequest();
        HttpServletResponse response = SpringBootUtil.getHttpServletResponse();
        String token = request.getHeader("token");

        String userEntityID = redisUtils.get("token:" + token);
        UserEntity userEntity;
        try {
            userEntity = JSON.parseObject(redisUtils.get(userEntityID), UserEntity.class);
            // redis 中不存在该用户
            if (userEntity == null) {
                //告诉前端要回到登录页面
                return R.error("login");
            }
            //原token过期日期
            Date expiresAt = jwtUtil.decode(token).getExpiresAt();
            //最长过期时间数（以当前时间计算）
            long l0 = DateUtil.reduceMinutes(jwtProperties.getMaxExpiresAt()).getTime();
            //token中的过期时间数
            long l1 = expiresAt.getTime();

            if (l0 <= l1) {
                // 超过20分钟之内，重新给令牌
                String sub = JWT.decode(token).getSubject();
                String newToken = jwtUtil.create(sub);
                response.setHeader("token", jwtUtil.create(sub));
                //redis 删除旧token
                redisUtils.delete("token:" + token);
                //redis 添加新token
                redisUtils.set("token:" + newToken, userEntity);
                log.info("令牌已更新: {}", jwtUtil.create(sub));
                response.setHeader("token", newToken);
            } else {
                // 需要重新登录，重定向方式
                log.warn("令牌完全失效");
                //redis 删除旧token
                redisUtils.delete("token:" + token);
                return R.error("login");
            }
            return R.success();
        } catch (Exception exception) {
            return R.error("login");
        }

    }

    @ExceptionHandler({JWTVerificationException.class})
    public R jwtVerificationException(Exception e) {
        return R.error("非法token");
    }

}
