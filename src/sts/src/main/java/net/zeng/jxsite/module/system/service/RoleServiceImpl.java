/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.dao.RoleDao;
import net.zeng.jxsite.module.system.dao.RoleLinkUserDao;
import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.Role;
import net.zeng.jxsite.module.system.model.security.RoleLinkUser;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class RoleServiceImpl implements RoleService {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private RoleDao roleDao;
	private RoleLinkUserDao roleLinkUserDao;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public RoleServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void save(Role role) {
		if (role != null) {
			this.roleDao.save(role);
		}
	}

	public Role getById(String id) {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return this.roleDao.queryById(id);
	}

	public Role findByName(String name) {
		if (StringUtil.isBlank(name)) {
			return null;
		}
		return this.roleDao.queryByName(name);
	}

	public int searchCount(String q) {
		return this.roleDao.queryCount(q);
	}

	public List<?> search(String q, Integer start, Integer limit) {
		return this.roleDao.query(q, start, limit);
	}

	public RoleLinkUser addUser(Role role, UserInfo user) {
		if (role == null || user == null) {
			return null;
		}
		// save link
		RoleLinkUser link = new RoleLinkUser();
		link.setRoleId(role.getId());
		link.setUserId(user.getId());
		this.roleLinkUserDao.save(link);
		// update role
		role.increaseUserCount();
		this.save(role);
		return link;
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleInfoDao) {
		this.roleDao = roleInfoDao;
	}

	public RoleLinkUserDao getRoleLinkUserDao() {
		return roleLinkUserDao;
	}

	public void setRoleLinkUserDao(RoleLinkUserDao roleLinkUserDao) {
		this.roleLinkUserDao = roleLinkUserDao;
	}

}
