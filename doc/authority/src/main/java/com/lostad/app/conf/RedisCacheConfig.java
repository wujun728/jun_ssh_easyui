package com.lostad.app.conf;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 数据缓存和session共享全部使用redis
 * @author songsz
 */
@Configuration    
@EnableCaching  
@EnableRedisHttpSession  
@SuppressWarnings("rawtypes")  
public class RedisCacheConfig extends CachingConfigurerSupport{    
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
    @Bean    
    @Override
    public KeyGenerator keyGenerator(){    
        return new KeyGenerator() {    
			@Override
			public Object generate(Object target, Method method,Object... params) {
				  StringBuilder sb = new StringBuilder();    
	                sb.append(target.getClass().getName());    
	                sb.append(method.getName());    
	                for (Object obj : params) {    
	                    sb.append(obj.toString());    
	                }    
	              return sb.toString();    
			}    
        };    
    }    
    @Bean
    @Override
    public CacheManager cacheManager() {
        String[] cacheNames = {"basicCache", "user", "dict","resourceCache"};
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate(), Arrays.asList(cacheNames));
        redisCacheManager.setDefaultExpiration(1800);//半个小时
        return redisCacheManager;
    }

    
    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(jedisConnectionFactory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

  
    @Bean
    public Cache cache() {
        return cacheManager().getCache("app_default");
    }

   
}  