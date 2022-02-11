package edu.xj.medicalcheckupweb.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zyhsna
 * <p>自定义全局获取session</p>
 *
 */
public class CustomHttpSession {
    public static final String USER_TOKEN = "USER_TOKEN_";

    public static HttpSession getHttpSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getSession();
    }
}
