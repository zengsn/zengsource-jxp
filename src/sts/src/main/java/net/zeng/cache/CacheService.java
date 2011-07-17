/**
 * 
 */
package net.zeng.cache;

/**
 * @author zeng.xiaoning
 *
 */
public interface CacheService {

	public Object get(String key);
	
	public void cache(String key, Object obj);
	
}
