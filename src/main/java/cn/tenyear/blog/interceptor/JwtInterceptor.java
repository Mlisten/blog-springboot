package cn.tenyear.blog.interceptor;

import cn.tenyear.blog.exception.BlogException;
import cn.tenyear.blog.exception.BlogExceptionEnum;
import cn.tenyear.blog.modle.entity.RoleEntity;
import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.service.RoleService;
import cn.tenyear.blog.service.UserService;
import cn.tenyear.blog.utils.IPUtil;
import cn.tenyear.blog.utils.JwtUtil;
import cn.tenyear.blog.utils.RedisUtils;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) throws Exception, JWTVerificationException {
        //测试IPUtil
        String ipAddr = IPUtil.getIpAddr(request);
        log.info("ip地址:{}", ipAddr);

        log.info("拦截器开始验证 token");
        String token = request.getHeader("token");
        log.info(token);

        jwtUtil.verify(token);

        String userEntityID = redisUtils.get("token:" + token);
        if (userEntityID == null) {
            log.info("redis中，token不存在");
            throw new TokenExpiredException("redis中，token不存在");
        }
        UserEntity userEntity = JSON.parseObject(redisUtils.get(userEntityID), UserEntity.class);
        if (userEntity == null) {
            int id = Integer.parseInt((userEntityID.substring(userEntityID.indexOf(":") + 1)));
            userEntity = userService.getById(id);
            redisUtils.set(userEntityID, userEntity);
        }

        log.info("JwtInterceptor，用户信息==>  {}", userEntity.toString());
        Integer roleId = userEntity.getRoleId();
        Integer status = userEntity.getStatus();
        //账号是启用状态
        if (status == 1) {
            // 判断账号的访问权限
            String uri = request.getRequestURI();
            RoleEntity role = roleService.getById(roleId);
            ArrayList<String> apis = role.getApi();
            //比较该用户的所拥有的权限中是否有这个请求路径
            for (String api : apis) {
                if (uri.contains(api)) {
                    return true;
                }
            }
            throw new BlogException(BlogExceptionEnum.ROLE_TO_LOW);
        }
        throw new BlogException(BlogExceptionEnum.ACCOUNT_NO_USE);
    }
}
