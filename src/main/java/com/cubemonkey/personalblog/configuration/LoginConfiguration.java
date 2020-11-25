package com.cubemonkey.personalblog.configuration;

import com.cubemonkey.personalblog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author CubeMonkey
 * @create 2020-11-23 16:46
 */
@Configuration
public class LoginConfiguration extends WebMvcConfigurationSupport {
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(getMyInterceptor());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/user/**");
        super.addInterceptors(registry);
    }

}


