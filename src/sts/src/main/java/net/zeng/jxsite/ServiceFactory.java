/**
 * 
 */
package net.zeng.jxsite;

import org.springframework.context.ApplicationContext;

import net.zeng.cache.CacheService;
import net.zeng.mvc.MVCContext;

/**
 * @author zeng.xiaoning
 *
 */
public abstract class ServiceFactory {
	
	public static ApplicationContext getContext() {
		return MVCContext.getInstance().getApplicationContext();
	}
	
	public static CacheService getCacheService() {
		return (CacheService) getContext().getBean("cacheService");
	}

}
