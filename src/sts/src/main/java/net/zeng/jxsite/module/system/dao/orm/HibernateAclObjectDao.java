/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

import net.zeng.jxsite.module.system.dao.AclObjectDao;
import net.zeng.jxsite.module.system.model.security.AclObject;
import net.zeng.util.StringUtil;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateAclObjectDao extends HibernateDaoTemplate implements AclObjectDao {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateAclObjectDao() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return AclObject.class;
	}

	public List<?> query(String q, Integer start, Integer limit) {
		return super.query(buildSearchCriterions(q), start, limit);
	}

	private Criterion[] buildSearchCriterions(String q) {
		if (StringUtil.isBlank(q)) {
			return new Criterion[0];
		} else {
			return new Criterion[] { Expression.sqlRestriction("objectname_ like ?",
					"%" + q + "%", Hibernate.STRING) };
		}
	}

	public int queryCount(String q) {
		return super.queryCount(buildSearchCriterions(q));
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
