/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.util.List;

import org.hibernate.criterion.Criterion;

import net.zeng.jxsite.module.system.dao.AclEntryDao;
import net.zeng.jxsite.module.system.model.security.AclEntry;
import net.zeng.util.StringUtil;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateAclEntryDao extends HibernateDaoTemplate implements AclEntryDao {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateAclEntryDao() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return AclEntry.class;
	}

	public int queryCount(String q) {
		return super.queryCount(buildSearchCriterions(q));
	}

	private Criterion[] buildSearchCriterions(String q) {
		if (StringUtil.isBlank(q)) {
			return new Criterion[0];
		} else {
			return new Criterion[0]; // TODO
		}
	}

	public List<?> query(String q, Integer start, Integer limit) {
		return super.query(buildSearchCriterions(q), start, limit);
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
