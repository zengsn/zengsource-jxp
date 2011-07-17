/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.dao.AclEntryDao;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclEntryServiceImpl implements AclEntryService {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private AclEntryDao aclEntryDao;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclEntryServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public int searchCount(String q) {
		if (StringUtil.isBlank(q)) {
			return 0;
		} else {
			return this.aclEntryDao.queryCount(q);
		}
	}

	public List<?> search(String q, Integer start, Integer limit) {
		return this.aclEntryDao.query(q, start, limit);
	};

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclEntryDao getAclEntryDao() {
		return aclEntryDao;
	}

	public void setAclEntryDao(AclEntryDao aclEntryDao) {
		this.aclEntryDao = aclEntryDao;
	}

}
