/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.Role;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface UserInfoService { 
	
	public UserInfo create(UserInfo userInfo);
	
	public UserInfo createSuper();
	
	public UserInfo getById(String id);
	
	public List<?> search(String q, Integer start, Integer limit);
	
	public List<?> search(Role role, Integer start, Integer limit);

	public Integer searchCount(String q);

	public Integer searchCount(Role role);

	public String generateDefaultPassword();

	public void save(UserInfo userInfo);

	public void mailPassword(UserInfo userInfo);

	public void disable(UserInfo userInfo);

}
