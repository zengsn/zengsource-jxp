/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.security.Role;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface RoleDao {

	public void save(Role role);

	public Role queryById(String id);

	public Role queryByName(String name);

	public int queryCount(String q);

	public List<?> query(String q, Integer start, Integer limit);

}
