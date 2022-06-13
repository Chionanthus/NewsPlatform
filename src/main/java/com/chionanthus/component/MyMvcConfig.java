package com.chionanthus.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/signup.html").setViewName("signup");
        registry.addViewController("/main.html").setViewName("main");
        registry.addViewController("/test.html").setViewName("test");
        registry.addViewController("/index.html").setViewName("index");
    }



    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**").excludePathPatterns(
                        Arrays.asList("/login.html", "/signup.html","/","/login","/signup",
                                "/asserts/**","/webjars/**","/index.html")
        );
    }
}

