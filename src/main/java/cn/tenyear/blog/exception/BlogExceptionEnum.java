package cn.tenyear.blog.exception;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
public enum BlogExceptionEnum {

    /**
     * code:1002,msg:只有文章管理权限
     */
    ONLY_ARTICLE(1002,"只有文章管理权限"),
    /**
     * code:1003,msg:只有评论管理权限
     */
    ONLY_COMMENT(1003,"只有评论管理权限"),
    /**
     * code:1099,msg:权限不足
     */
    ROLE_TO_LOW(1099,"权限不足"),

    /**
     * code:2000,msg:成功!
     */
    SUCCESS(2000, "成功!"),
    /**
     * code:-2000,msg:失败!
     */
    FAIL(-2000,"失败!"),

    /**
     * code:3001,msg:账号未启用
     */
    ACCOUNT_NO_USE(3001,"账号未启用"),
    /**
     * code:4000,msg:请求的数据格式不符!
     */
    BODY_NOT_MATCH(4000,"请求的数据格式不符!"),
    /**
     * code:4001,msg:请求的数字签名不匹配!
     */
    SIGNATURE_NOT_MATCH(4001,"请求的数字签名不匹配!"),
    /**
     * code:4004,msg:未找到该资源!
     */
    NOT_FOUND(4004, "未找到该资源!"),
    /**
     * code:5000,msg:服务器内部错误!
     */
    INTERNAL_SERVER_ERROR(5000, "服务器内部错误!"),
    /**
     * code:5003,msg:服务器正忙，请稍后再试!
     */
    SERVER_BUSY(5003,"服务器正忙，请稍后再试!"),
    /**
     * code:5010,msg:未知异常!
     */
    UNKNOWN(5010,"未知异常!"),

    ;
    private final Integer code;
    private final String msg;

    BlogExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    public Integer getCode() {
        return code;
    }
}
