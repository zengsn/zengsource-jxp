/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import net.zeng.jxsite.module.system.dao.AclClassDao;
import net.zeng.jxsite.module.system.model.security.AclClass;
import net.zeng.util.StringUtil;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateAclClassDao extends HibernateDaoTemplate implements AclClassDao {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateAclClassDao() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return AclClass.class;
	}

	public int queryCount(String q) {
		return super.queryCount(buildSearchCriterions(q));
	}

	private Criterion[] buildSearchCriterions(String q) {
		if (StringUtil.isBlank(q)) {
			return new Criterion[0];
		} else {
			return new Criterion[] { Expression.like("name", q, MatchMode.ANYWHERE) };
		}
	}
	
	public List<?> query(String q, Integer start, Integer limit) {
		return super.query(buildSearchCriterions(q), start, limit);
	}
	
	public AclClass queryById(String id) {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return (AclClass) super.queryById(id);
	}
	
	public AclClass queryByName(String name) {
		if (StringUtil.isBlank(name)) {
			return null;
		}
		return (AclClass) super.queryUnique(new Criterion[] { Expression.eq("name", name) });
	}
	
	public void save(AclClass aclClass) {
		if (aclClass != null) {
			Date now = new Date();
			if (aclClass.getCreatedTime() == null) {
				aclClass.setCreatedTime(now);
			}
			aclClass.setUpdatedTime(now);
			super.save(aclClass);			
		}
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
