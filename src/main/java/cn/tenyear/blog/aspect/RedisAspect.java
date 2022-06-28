package cn.tenyear.blog.aspect;

import cn.tenyear.blog.exception.BlogExceptionEnum;
import cn.tenyear.blog.modle.entity.*;
import cn.tenyear.blog.utils.R;
import cn.tenyear.blog.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static cn.tenyear.blog.utils.RedisUtils.EXPIRE;

/**
 * controller包下的admin包,对单个数据进行缓存，处理方法:get,delete,update
 * <p>格式: 简单类名:id，json格式的对象，默认1天；</p>
 *
 * @author 李胜旺
 * @date 2022/4/4
 * @email 804464376@qq.com
 */
@Aspect
@Slf4j
@Component
public class RedisAspect {
    @Autowired
    private RedisUtils redisUtils;

    @Around(value = "execution(public cn.tenyear.blog.utils.R cn.tenyear.blog.controller.admin.*Controller.get*(..)) && args(id)", argNames = "joinPoint,id")
    public Object getOne(ProceedingJoinPoint joinPoint, Integer id) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (id != null) {
            // 如果redis中有数据，则返回
            String value = redisUtils.get(className + ":" + id);
            if (value != null) {
                ConcurrentMap<String, Object> map = new ConcurrentHashMap<>(3);
                map.put("data", value);
                return R.success(map);
            }
            // 如果redis中没有数据，则执行controller
            try {
                R r = (R) joinPoint.proceed();
                // 把执行获得的结果取出，放到redis中
                Object data = r.get("data");
                if (data != null) {
                    redisUtils.set(className + ":" + id, data);
                }
                return r;
            } catch (Throwable e) {
                log.error(e.getMessage());
                return R.error(BlogExceptionEnum.UNKNOWN);
            }
        } else {
            log.error("RedisAspect.get方法的id参数传入了null");
            return null;
        }
    }

    /**
     * 所有的admin包下的删除方法删除前，根据参数中的id先删除redis中的数据
     *
     * @param joinPoint 连接点
     * @param id        参数id
     */
    @Before(value = "execution(public cn.tenyear.blog.utils.R cn.tenyear.blog.controller.admin.*Controller.delete(..)) && args(id)", argNames = "joinPoint,id")
    public void delete(JoinPoint joinPoint, Integer id) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取方法名称
        String methodName = method.getName();
        //获取简短的类名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        //立即删除redis中对应的数据
        redisUtils.set(className + ":" + id, "", EXPIRE);
    }

    /**
     * 所有的admin包下的更新方法更新前，根据参数中的id先删除redis中的数据
     *
     * @param joinPoint 连接点
     * @param entity    参数id
     */
    @Before(value = "execution(public cn.tenyear.blog.utils.R cn.tenyear.blog.controller.admin.*Controller.update(..)) && args(entity)", argNames = "joinPoint,entity")
    public void update(JoinPoint joinPoint, Object entity) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取方法名称
        String methodName = method.getName();
        //获取简短的类名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Integer id = -1000;
        if (entity instanceof ArticleEntity article) {
            id = article.getId();
        } else if (entity instanceof CommentEntity comment) {
            id = comment.getId();
        } else if (entity instanceof RoleEntity role) {
            id = role.getId();
        } else if (entity instanceof TagEntity tag) {
            id = tag.getId();
        } else if (entity instanceof TypeEntity type) {
            id = type.getId();
        } else if (entity instanceof UserEntity user) {
            id = user.getId();
        } else {
            log.error(className + "的update中对象id不存在");
        }
        redisUtils.set(className + ":" + id, "", EXPIRE);
    }
}
