package com.recycle.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Autowired
    private MySiteSetting mySiteSetting;

    /**
     * 静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
                .setCachePeriod(600)
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addVersionStrategy(new MyVersionPathStrategy(), "/**"));
    }

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }
}