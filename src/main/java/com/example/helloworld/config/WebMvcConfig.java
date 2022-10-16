package com.example.helloworld.config;

import com.example.helloworld.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    public WebMvcConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration sessionInterceptorRegistry = registry.addInterceptor(sessionInterceptor);
        // 排除不需要拦截的路径
//        sessionInterceptorRegistry.excludePathPatterns("/page/doLogin");
//        sessionInterceptorRegistry.excludePathPatterns("/error");

        // 需要拦截的路径
        sessionInterceptorRegistry.addPathPatterns("/**");
        sessionInterceptorRegistry.excludePathPatterns("/page/login");
        sessionInterceptorRegistry.excludePathPatterns("/page/please/login");

    }
}