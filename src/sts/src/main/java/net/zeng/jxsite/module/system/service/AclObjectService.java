/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface AclObjectService {

	public int searchCount(String q);

	public List<?> search(String q, Integer start, Integer limit);

}
