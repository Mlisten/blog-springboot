package cn.tenyear.blog.controller.show;

import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.service.impl.UserServiceImpl;
import cn.tenyear.blog.utils.JwtUtil;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.RedisUtils;
import cn.tenyear.blog.utils.SpringBootUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */

@RestController
@RequestMapping()
public class LoginController {
    @Autowired
    private UserServiceImpl service;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(@RequestBody UserEntity user) {

        UserEntity userEntity = service.getOne(new LambdaQueryWrapper<UserEntity>().select().eq(UserEntity::getUsername, user.getUsername()).eq(UserEntity::getPassword, user.getPassword()));

        if (userEntity != null && userEntity.getStatus() == 1) {
            //给浏览器的token中的负荷只有唯一的用户ID
            String token = jwtUtil.create("user_id:" + userEntity.getId());

            //redis存用户id，因为程序启动时已添加至redis中
            redisUtils.set("token:" + token, "userEntity:" + userEntity.getId(), RedisUtils.HOUR_EXPIRE);
            SpringBootUtil.getHttpServletResponse().setHeader("token", token);
            user.setPassword(null);
            return R.success(user);
        }
        return R.error("用户名或密码错误");
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public void logout() {
        String token = SpringBootUtil.getHttpServletRequest().getHeader("token");
        redisUtils.get("token:" + token, RedisUtils.EXPIRE);
    }

    /**
     * 注册
     */
    @PostMapping("/logon")
    public R logon(@RequestBody UserEntity user) {
        UserEntity username = service.getOne(new LambdaQueryWrapper<UserEntity>().select().eq(UserEntity::getUsername, user.getUsername()));
        if (username != null) {
            return R.error("用户名已存在!");
        }
        UserEntity phone = service.getOne(new LambdaQueryWrapper<UserEntity>().select().eq(UserEntity::getPhone, user.getPhone()));
        if (phone != null) {
            return R.error("手机号已存在!");
        }
        if (service.save(user)) {
            return R.success("注册成功,请继续登录!");
        }
        return R.error("注册失败,请联系管理员!");
    }
}
