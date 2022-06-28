package cn.tenyear.blog.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 李胜旺
 * @date 2022/3/31
 * @email 804464376@qq.com
 */
@Getter
@Setter
public class BlogException extends RuntimeException {
    private Integer code;
    private String msg;

    public BlogException() {
        super();
    }

    public BlogException(BlogExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public BlogException(BlogExceptionEnum exceptionEnum, Throwable cause) {
        super(exceptionEnum.getMsg(), cause);
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public BlogException(String msg) {
        super(msg);
        this.code = -1000;
        this.msg = msg;
    }

    public BlogException(String msg, Integer code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BlogException(String msg, Integer code, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
