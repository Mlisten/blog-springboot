package cn.tenyear.blog.config;

import cn.tenyear.blog.modle.entity.RoleEntity;
import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.service.impl.RoleServiceImpl;
import cn.tenyear.blog.service.impl.UserServiceImpl;
import cn.tenyear.blog.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 应用程序起动时,redis需从MySQL中加载的数据
 *
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
@Component
@Slf4j
public class RedisRunner implements ApplicationRunner {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void run(ApplicationArguments args) {
        redisUtils.flushAll();
        for (RoleEntity roleEntity : roleService.list()) {
            redisUtils.set("roleEntity:" + roleEntity.getId(), roleEntity);
            log.info("redisUtils 添加 id={} 的roleEntity对象", roleEntity.getId());
        }
        List<UserEntity> userEntityList = userService.list();
        if (userEntityList == null) {
            log.info("用户信息全都不存在");
            return;
        }
        for (UserEntity userEntity : userEntityList) {
            redisUtils.set("userEntity:" + userEntity.getId(), userEntity);
        }
        log.info("用户信息已加入redis，总计{}个", userEntityList.size());

    }
}
