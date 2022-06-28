package cn.tenyear.blog.utils;


import java.util.UUID;
/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public class IdUtils {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static String creatNewName( String withSuffixFileName){
       return uuid() + withSuffixFileName.substring(withSuffixFileName.lastIndexOf(".")-1);
    }
}
