/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.Role;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface UserInfoDao {

	public void save(UserInfo account);

	public UserInfo queryById(String id);

	public List<?> query(String q, Integer start, Integer limit);
	
	public Integer queryCount(String q);

	public List<?> query(Role role, Integer start, Integer limit);

	public Integer queryCount(Role role);

}
