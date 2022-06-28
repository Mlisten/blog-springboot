package cn.tenyear.blog.utils;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @author 李胜旺
 * @date 2022/3/30
 * @email 804464376@qq.com
 */
public class SpringBootUtil {

    public static HttpServletRequest getHttpServletRequest() {
       return getHttpServletRequest("UTF-8");
    }
    public static HttpServletRequest getHttpServletRequest(String charsetEncoding){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        try {
            request.setCharacterEncoding(charsetEncoding);
            return request;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return request;
        }
    }

    public static HttpServletResponse getHttpServletResponse() {
        return getHttpServletResponse("application/json;charset=UTF-8");
    }
    public static HttpServletResponse getHttpServletResponse(String contentType) {
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        assert response != null;
        response.setContentType(contentType);
        return response;
    }

    /**
     * 默认无session时，自动创建
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getHttpServletRequest().getSession();
    }

    /**
     * 当b为false时，无session时，不会自动创建；
     * 当b为true时， 无session时，会自动创建
     * @param b
     * @return HttpSession
     */
    public static HttpSession getSession(boolean b) {
        return getHttpServletRequest().getSession(b);
    }

}
