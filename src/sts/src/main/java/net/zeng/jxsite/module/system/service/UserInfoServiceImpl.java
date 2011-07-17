/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;
import java.util.Random;

import net.zeng.jxsite.module.system.dao.RoleLinkUserDao;
import net.zeng.jxsite.module.system.dao.UserInfoDao;
import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.Role;
import net.zeng.jxsite.module.system.model.security.User;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class UserInfoServiceImpl implements UserInfoService {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public static final String KEY_SUPER_USERNAME = "system.user.super.username";
	public static final String KEY_SUPER_PASSWORD = "system.user.super.password";
	public static final String KEY_SUPER_NICKNAME = "system.user.super.nickname";
	public static final String KEY_SUPER_EMAIL = "system.user.super.email";

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private UserInfoDao userInfoDao;
	private RoleLinkUserDao roleLinkUserDao;

	private ConfigurationService configurationService;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserInfoServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserInfo create(UserInfo userInfo) {
		if (userInfo != null) {
			this.userInfoDao.save(userInfo);
			return userInfo;
		}
		return null;
	}

	public UserInfo createSuper() {
		UserInfo superAccount = new UserInfo();
		superAccount.setId("00000001");
		superAccount.setUser(new User( //
				this.configurationService.getString(KEY_SUPER_USERNAME), //
				this.configurationService.getString(KEY_SUPER_PASSWORD), 1));
		superAccount.setNickname(this.configurationService.getString(KEY_SUPER_NICKNAME));
		superAccount.setEmail(this.configurationService.getString(KEY_SUPER_EMAIL));
		return create(superAccount);
	}

	public UserInfo getById(String id) {
		if (StringUtil.notBlank(id)) {
			return this.userInfoDao.queryById(id);
		}
		return null;
	}

	public List<?> search(String q, Integer start, Integer limit) {
		return this.userInfoDao.query(q, start, limit);
	}

	public Integer searchCount(String q) {
		return this.userInfoDao.queryCount(q);
	}

	public String generateDefaultPassword() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 8; i++) {
			int n = random.nextInt(52);
			if (n < 26) {
				sb.append((char) ('a' + n));
			} else {
				sb.append((char) ('A' + n - 26));
			}
		}
		return sb.toString();
	}

	public void save(UserInfo account) {
		this.userInfoDao.save(account);
	}

	public void mailPassword(UserInfo account) {
		// TODO Auto-generated method stub

	}

	public void disable(UserInfo account) {
		// TODO More configuration
		if (account != null) {
			account.getUser().disable();
			save(account);
		}
	}

	public List<?> search(Role role, Integer start, Integer limit) {
		if (role == null) {
			return null;
		}
		return this.userInfoDao.query(role, start, limit);
	}

	public Integer searchCount(Role role) {
		if (role == null) {
			return 0;
		}
		return this.userInfoDao.queryCount(role);
	};

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public RoleLinkUserDao getRoleLinkUserDao() {
		return roleLinkUserDao;
	}

	public void setRoleLinkUserDao(RoleLinkUserDao roleLinkUserDao) {
		this.roleLinkUserDao = roleLinkUserDao;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

}
