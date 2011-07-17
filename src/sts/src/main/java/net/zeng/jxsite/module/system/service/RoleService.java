/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.Role;
import net.zeng.jxsite.module.system.model.security.RoleLinkUser;


/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface RoleService {

	public Role getById(String id);

	public void save(Role role);

	public Role findByName(String name);

	public int searchCount(String q);

	public List<?> search(String q, Integer start, Integer limit);

	public RoleLinkUser addUser(Role role, UserInfo user);

}
