package edu.xj.medicalcheckupweb.config;

import edu.xj.medicalcheckupweb.interceptor.CorsInterceptor;
import edu.xj.medicalcheckupweb.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>自定义拦截器</p>
 */
@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器并且添加拦截路径
        /*TODO 之后项目上线将其放开
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/user/pri/**");*/
        registry.addInterceptor(getCorsInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public CorsInterceptor getCorsInterceptor(){return new CorsInterceptor();}
}
