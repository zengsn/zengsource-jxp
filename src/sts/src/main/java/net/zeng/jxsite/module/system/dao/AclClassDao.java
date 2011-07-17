/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.security.AclClass;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface AclClassDao {

	public int queryCount(String q);

	public List<?> query(String q, Integer start, Integer limit);

	public AclClass queryById(String id);

	public AclClass queryByName(String name);

	public void save(AclClass aclClass);

}
