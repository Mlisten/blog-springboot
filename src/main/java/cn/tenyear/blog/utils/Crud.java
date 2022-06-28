package cn.tenyear.blog.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public class Crud implements InvocationHandler {

    private static Crud getCrud() {
        return new Crud();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.setAccessible(true);
        if (method.getName().contains("setCreateTime") || method.getName().contains("setUpdateTime")) {
            method.invoke(proxy, DateUtil.format());
        }
        return proxy;
    }

    /**
     * 遍历传进来的对象的每个方法，再调用invoke(...)
     * @param obj  传进来的对象
     * @param args 空参数
     */
    private void insertDateTime(Object obj, Object... args) {
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            try {
                invoke(obj, method, args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * post请求新增  自动调用 setCreateTime() setUpdateTime(), 日期格式 2020-12-12 12:12:12
     *
     * @param obj 一个对象
     */

    public static void addDate(Object obj) {
        Crud.getCrud().insertDateTime(obj);
    }
}
