/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.dao.AclClassDao;
import net.zeng.jxsite.module.system.model.security.AclClass;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclClassServiceImpl implements AclClassService {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private AclClassDao aclClassDao;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclClassServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public int searchCount(String q) {
		return this.aclClassDao.queryCount(q);
	};

	public List<?> search(String q, Integer start, Integer limit) {
		return this.aclClassDao.query(q, start, limit);
	}

	public AclClass getById(String id) {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return this.aclClassDao.queryById(id);
	}

	public AclClass getByClassName(String name) {
		if (StringUtil.isBlank(name)) {
			return null;
		}
		return this.aclClassDao.queryByName(name);
	}

	public void save(AclClass aclClass) {
		if (aclClass != null) {
			this.aclClassDao.save(aclClass);
		}
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclClassDao getAclClassDao() {
		return aclClassDao;
	}

	public void setAclClassDao(AclClassDao aclClassDao) {
		this.aclClassDao = aclClassDao;
	}

}
