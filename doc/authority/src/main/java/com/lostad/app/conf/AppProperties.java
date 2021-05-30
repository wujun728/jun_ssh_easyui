package com.lostad.app.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/**
 * server.context-path=/app
 * server.port=9002
 * @author sszvip
 *
 */
@Component
public class AppProperties {
    @Autowired
    public Environment env;//当前环境的application.properties的 配置
    @Value("server.context-path")//注入普通字符串
    public String contextPath;
    @Value("#{systemProperties['server.port']}")//系统属性配置
    public String port;
    @Value("#{systemProperties['uploadPath']}")//系统属性配置
    public String uploadPath;
//    @Value("#{ T(java.lang.String).valueOf(111)}")//执行某个类的方法
//    public String test3;
//    @Value("#{valueTest.name}")//某个类的公有属性
//    public String test4;
//    @Value("${name}")//读取配置在PropertySourcesPlaceholderConfigurer Bean里的properties文件的值
//    public String test5;

}