/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.system.dao.RoleLinkUserDao;
import net.zeng.jxsite.module.system.model.security.RoleLinkUser;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateRoleLinkUserDao extends HibernateDaoTemplate implements RoleLinkUserDao {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Logger logger = Logger.getLogger(getClass());

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateRoleLinkUserDao() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return RoleLinkUser.class;
	}

	public void save(RoleLinkUser link) {
		if (link == null) {
			return;
		}
		if (StringUtil.isBlank(link.getId())) {
			link.setId(IDUtil.generate());
		}
		if (link.getCreatedTime() == null) {
			link.setCreatedTime(new Date());
		}
		// save
		final RoleLinkUser link2 = link;
		// update index
		this.hibernateTemplate.execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getPrototypeClass());
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					// count roles
					criteria.add(Expression.sqlRestriction("userid_=?", //
							link2.getUserId(), Hibernate.STRING));
					criteria.add(Expression.sqlRestriction("roleid_=?", // 
							link2.getRoleId(), Hibernate.STRING));
					criteria.setProjection(Projections.count("id"));
					int result = (Integer) criteria.uniqueResult();
					// update index
					link2.setIndex(result);
					// save link
					session.save(link2);
					logger.info("Save link: " + link2.getId() + ".");
					tx.commit();
					tx = null;
					return result;
				} catch (HibernateException e) {
					e.printStackTrace();
					if (tx != null)
						tx.rollback();
				} finally {
					session.close();
				}
				return null;
			}
		});
	}
	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
