/**
 * 
 */
package net.zeng.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class CacheServiceByEhcache implements CacheService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Cache cache;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CacheServiceByEhcache() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void cache(String key, Object obj) {
		Element element = new Element(key, obj);
		this.cache.put(element);
	}

	public Object get(String key) {
		Element element = this.cache.get(key);
		return element == null ? null : element.getValue();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

}
