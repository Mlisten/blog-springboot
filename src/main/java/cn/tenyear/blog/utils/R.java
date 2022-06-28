package cn.tenyear.blog.utils;

import cn.tenyear.blog.exception.BlogExceptionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
@ToString
@Getter
@Setter
public class R extends HashMap<String, Object> {
    @Serial
    private static final long serialVersionUID = 1L;
    private R () {
        put("code", 2000);
        put("msg", "成功");
    }
    public static R error() {
        return error(-2000, "失败");
    }

    public static R error(String msg) {
        return error(-2000, msg);
    }
    public static R error(BlogExceptionEnum exceptionEnum) {
        return error(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static R success(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }
//    public static R success(Object data){
//        R r = new R();
//        r.put("data",data);
//        return r;
//    }

    public static R success(Object data){
        R r = new R();
        r.put("data",data);
        return r;
    }
    public static R success(Object data,long count){
        R r = new R();
        r.put("data",data);
        r.put("count",count);
        return r;
    }

    public static R success(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R success() {
        return new R();
    }
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
