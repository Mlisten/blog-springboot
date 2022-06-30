package cn.tenyear.blog.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
    private RedisTask redisTask;

    @Override
    public void run(ApplicationArguments args) {
        redisTask.roleEntityJob();
        redisTask.userEntityJob();
    }
}
