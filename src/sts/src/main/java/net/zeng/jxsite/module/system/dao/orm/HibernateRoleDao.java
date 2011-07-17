/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import net.zeng.jxsite.module.system.dao.RoleDao;
import net.zeng.jxsite.module.system.model.security.Role;
import net.zeng.util.StringUtil;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateRoleDao extends HibernateDaoTemplate implements RoleDao {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateRoleDao() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return Role.class;
	}

	public void save(Role role) {
		if (role != null) {
			Date now = new Date();
			if (role.getCreatedTime() == null) {
				role.setCreatedTime(now);
			}
			role.setUpdatedTime(now);
			super.save(role);
		}
	}

	public Role queryById(String id) {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return (Role) super.queryById(id);
	}

	public Role queryByName(String name) {
		if (StringUtil.isBlank(name)) {
			return null;
		}
		return (Role) this.queryUnique(new Criterion[] { //
				Expression.sqlRestriction("name_=?", name, Hibernate.STRING) });
	}

	public int queryCount(String q) {
		return super.queryCount(buildSearchCriterions(q));
	}

	private Criterion[] buildSearchCriterions(String q) {
		if (StringUtil.isBlank(q)) {
			return new Criterion[0];
		} else {
			return new Criterion[] { //
			Restrictions.disjunction().add( //
					Expression.sqlRestriction("name_ like ?", "%" + q + "%", Hibernate.STRING)//
					).add(Expression.like("description", q, MatchMode.ANYWHERE)) };
		}
	}
	
	public List<?> query(String q, Integer start, Integer limit) {
		return super.query(buildSearchCriterions(q), start, limit);
	}
	
	public Integer queryUsersCount(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
