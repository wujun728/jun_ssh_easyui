//package com.erp.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class WebAppConfigurer extends WebMvcConfigurerAdapter {
//
//    /**
//     * 配置拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // TODO Auto-generated method stub
//        // 多个拦截器组成一个拦截器链
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用户排除拦截
////        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**");
//
//
//        super.addInterceptors(registry);
//    }
//
//    /**
//     * 添加自定义的静态资源映射
//      这里使用代码的方式自定义目录映射，并不影响Spring Boot的默认映射，可以同时使用。
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
////      registry.addResourceHandler("/new/**").addResourceLocations("classpath:/new/");
////      registry.addResourceHandler("/**").addResourceLocations("/");
//        super.addResourceHandlers(registry);
//    }
//
//}