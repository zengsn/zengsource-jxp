/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.system.dao.UserInfoDao;
import net.zeng.jxsite.module.system.model.UserInfo;
import net.zeng.jxsite.module.system.model.security.Role;
import net.zeng.util.StringUtil;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateUserInfoDao extends HibernateDaoTemplate implements UserInfoDao {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateUserInfoDao() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return UserInfo.class;
	}

	public void save(UserInfo userInfo) {
		Date now = new Date();
		if (userInfo.getCreatedTime() == null) {
			userInfo.setCreatedTime(now);
		}
		userInfo.setUpdatedTime(now);
		super.save(userInfo);
	}

	public UserInfo queryById(String id) {
		return (UserInfo) super.queryById(id);
	}

	public List<?> query(String q, Integer start, Integer limit) {
		if (StringUtil.isBlank(q)) {
			return super.query(new Criterion[0], start, limit);
		}
		// return this.query(new Criterion[] { Restrictions.disjunction().add(
		// Expression.like("firstName", q, MatchMode.ANYWHERE)).add(
		// Expression.like("lastName", q, MatchMode.ANYWHERE)).add(
		// Expression.like("email", q, MatchMode.ANYWHERE)).add(
		// Expression.sqlRestriction("username like '%?%'", q,
		// Hibernate.STRING)) }, start,
		// limit);
		return super.query(new Criterion[] { Expression.sqlRestriction("username_ like ? ", "%"
				+ q + "%", Hibernate.STRING) }, start, limit);
	}

	public Integer queryCount(String q) {
		if (StringUtil.isBlank(q)) {
			return super.queryCount(new Criterion[0]);
		}
		// return this.queryCount(new Criterion[] {
		// Restrictions.disjunction().add(
		// Expression.like("firstName", q, MatchMode.ANYWHERE)).add(
		// Expression.like("lastName", q, MatchMode.ANYWHERE)).add(
		// Expression.like("email", q, MatchMode.ANYWHERE)).add(
		// Expression.sqlRestriction("username like '%?%'", q,
		// Hibernate.STRING)) });
		return super.queryCount(new Criterion[] { Expression.sqlRestriction("username_ like ? ",
				"%" + q + "%", Hibernate.STRING) });
	}

	public List<?> query(final Role role, final Integer start, final Integer limit) {
		if (role == null) {
			return null;
		}
		return this.hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria userCriteria = session.createCriteria(getPrototypeClass());

				buildUserCriteria(userCriteria, role);

				if (start != null && start > 0) {
					userCriteria.setFirstResult(start);
				}
				if (limit != null && limit > 0) {
					userCriteria.setMaxResults(limit);
				}
				return userCriteria.list();
			}
		});
	}

	private void buildUserCriteria(Criteria userCriteria, Role role) {

		Criteria roleCriteria = userCriteria.createCriteria("user").createCriteria("roles");
		roleCriteria.add(Expression.eq("id", role.getId()));

	}

	public Integer queryCount(final Role role) {
		if (role == null) {
			return 0;
		}
		Object result = this.hibernateTemplate.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria userCriteria = session.createCriteria(getPrototypeClass());

				buildUserCriteria(userCriteria, role);

				userCriteria.setProjection(Projections.count("id"));
				return userCriteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
