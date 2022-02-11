package edu.xj.medicalcheckupweb.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.xj.medicalcheckupweb.util.CustomHttpSession;
import edu.xj.medicalcheckupweb.util.JSONData;
import io.netty.util.internal.StringUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.http.HttpResponse;

/**
 * @author zyhsna
 * <p>登录拦截器，对用户进行登录验证</p>
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getParameter("userId");
        if (StringUtil.isNullOrEmpty(userId)){
            //参数丢失，未登录
            JSONData jsonData = new JSONData(10, "请求参数丢失");
            String jsonStr = objectMapper.writeValueAsString(jsonData);
            renderJson(response, jsonStr);
            return false;
        }
        HttpSession session = request.getSession();
        Object sessionAttribute = session.getAttribute(CustomHttpSession.USER_TOKEN + userId);
        if (sessionAttribute == null) {
            //session中无登录用户，用户未登录
            JSONData jsonData = new JSONData(8, "用户未登录");
            String jsonStr = objectMapper.writeValueAsString(jsonData);
            renderJson(response, jsonStr);
            return false;
        }
        return true;
    }

    /**
     * 向前端返回相关登录信息
     * @param response response
     * @param jsonStr 登录失败的结果信息
     */
    private void renderJson(HttpServletResponse response,String jsonStr){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try(PrintWriter writer = response.getWriter()){
            writer.print(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
