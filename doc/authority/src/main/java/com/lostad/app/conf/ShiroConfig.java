package com.lostad.app.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.lostad.app.conf.shiro.MyRealm;
import com.lostad.app.conf.shiro.ShiroManager;

@Configuration
@Import(ShiroManager.class)
public class ShiroConfig {

	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public Realm realm() {
		return new MyRealm();
	}
	
	 /**
     * 用户授权信息Cache
     */
    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setCacheManager(cacheManager());
        return sm;
    }

	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager securityManager, Realm realm) {
		securityManager.setRealm(realm);

		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/login");//默认也是这个  
		shiroFilter.setSuccessUrl("/main/indexMain");
		shiroFilter.setUnauthorizedUrl("/common/403");
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/api/**", "anon");
		filterChainDefinitionMap.put("/anno/**", "anon");
		filterChainDefinitionMap.put("/logout", "anon");
		//动态授权的资源
		filterChainDefinitionMap.put("/sys/user/index", "perms[system:user:index]");
		filterChainDefinitionMap.put("/sys/user/add", "perms[system:user:add]");
		filterChainDefinitionMap.put("/sys/user/edit*", "perms[system:user:edit]");
		filterChainDefinitionMap.put("/sys/user/deleteBatch", "perms[system:user:deleteBatch]");
		filterChainDefinitionMap.put("/sys/user/grant/**", "perms[system:user:grant]");
		
		filterChainDefinitionMap.put("/sys/role/index", "perms[system:role:index]");
		filterChainDefinitionMap.put("/sys/role/add", "perms[system:role:add]");
		filterChainDefinitionMap.put("/sys/role/edit*", "perms[system:role:edit]");
		filterChainDefinitionMap.put("/sys/role/deleteBatch", "perms[system:role:deleteBatch]");
		filterChainDefinitionMap.put("/sys/role/grant/**", "perms[system:role:grant]");
		
		filterChainDefinitionMap.put("/sys/resource/index", "perms[system:resource:index]");
		filterChainDefinitionMap.put("/sys/resource/add", "perms[system:resource:add]");
		filterChainDefinitionMap.put("/sys/resource/edit*", "perms[system:resource:edit]");
		filterChainDefinitionMap.put("/sys/resource/deleteBatch", "perms[system:resource:deleteBatch]");
		
		filterChainDefinitionMap.put("/sys/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}
}
