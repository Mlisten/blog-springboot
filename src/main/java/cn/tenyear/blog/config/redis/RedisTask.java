package cn.tenyear.blog.config.redis;

import cn.tenyear.blog.modle.entity.RoleEntity;
import cn.tenyear.blog.modle.entity.UserEntity;
import cn.tenyear.blog.service.impl.RoleServiceImpl;
import cn.tenyear.blog.service.impl.UserServiceImpl;
import cn.tenyear.blog.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * 应用程序起动时,redis需从MySQL中加载的数据,之后每一天执行一次
 *
 * @author 李胜旺
 * @date 2022/6/30
 * @email 804464376@qq.com
 */
@Slf4j
@Configuration
@EnableScheduling
public class RedisTask {
    private static final String EVERY_DAY_CRON = "0 0 0 * * ? *";
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private UserServiceImpl userService;

    public RedisTask() {
        redisUtils.flushAll();
    }

    @Scheduled(cron = EVERY_DAY_CRON)
    public void userEntityJob() {
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

    @Scheduled(cron = EVERY_DAY_CRON)
    public void roleEntityJob() {
        for (RoleEntity roleEntity : roleService.list()) {
            redisUtils.set("roleEntity:" + roleEntity.getId(), roleEntity);
            log.info("redisUtils 添加 id={} 的roleEntity对象", roleEntity.getId());
        }
    }
}
