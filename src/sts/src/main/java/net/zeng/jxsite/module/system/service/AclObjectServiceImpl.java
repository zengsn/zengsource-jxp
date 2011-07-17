/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.dao.AclObjectDao;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclObjectServiceImpl implements AclObjectService {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private AclObjectDao aclObjectDao;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclObjectServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public int searchCount(String q) {
		if (StringUtil.isBlank(q)) {
			return 0;
		} else {
			return this.aclObjectDao.queryCount(q);
		}
	};
	
	public List<?> search(String q, Integer start, Integer limit) {
		return this.aclObjectDao.query(q, start, limit);
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclObjectDao getAclObjectDao() {
		return aclObjectDao;
	}

	public void setAclObjectDao(AclObjectDao aclObjectDao) {
		this.aclObjectDao = aclObjectDao;
	}

}
