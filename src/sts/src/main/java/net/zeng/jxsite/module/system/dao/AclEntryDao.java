/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface AclEntryDao {

	public int queryCount(String q);

	public List<?> query(String q, Integer start, Integer limit);

}
