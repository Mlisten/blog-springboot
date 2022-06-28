package cn.tenyear.blog.utils;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
@Component
@Getter
public class RedisUtils {

    @Value("#{redisConfig.redisTemplate()}")
    private RedisTemplate<String, Object> redisTemplate;

    @Value("#{redisConfig.redisTemplate().opsForValue()}")
    private ValueOperations<String, String> valueOperations;

    @Value("#{redisConfig.redisTemplate().opsForHash()}")
    private HashOperations<String, String, Object> hashOperations;

    @Value("#{redisConfig.redisTemplate().opsForList()}")
    private ListOperations<String, Object> listOperations;

    @Value("#{redisConfig.redisTemplate().opsForSet()}")
    private SetOperations<String, Object> setOperations;

    @Value("#{redisConfig.redisTemplate().opsForZSet()}")
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 过期时长，单位：秒
     */
    public final static long SECOND_EXPIRE = 1;
    /**
     * 过期时长，单位：分钟
     */
    public final static long MINUTES_EXPIRE = 60;
    /**
     * 过期时长，单位：时
     */
    public final static long HOUR_EXPIRE = 60 * MINUTES_EXPIRE;
    /**
     * 过期时长，单位：天
     */
    public final static long DAY_EXPIRE = 24 * HOUR_EXPIRE;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;
    /**
     * 设置立即过期
     */
    public final static long EXPIRE = -2;

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DAY_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void select(int dbIndex) {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().select(dbIndex);
    }

    public void flushDb() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    public void flushAll() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
