/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.system.dao.ConfigurationDao;
import net.zeng.jxsite.module.system.model.Configuration;
import net.zeng.jxsite.module.system.service.ConfigurationException;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.StringUtil;
import net.zeng.util.TimeZoneUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateConfigurationDao extends HibernateDaoTemplate implements ConfigurationDao {

	public HibernateConfigurationDao() {
	}

	@Override
	public Class<?> getPrototypeClass() {
		return Configuration.class;
	}

	public List<?> query(final String query, final String group, final int start, final int limit)
			throws ConfigurationException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Configuration.class);
				if (StringUtil.notBlank(query)) {
					criteria.add(Expression.like("key", query, MatchMode.ANYWHERE));
				}
				if (StringUtil.notBlank(group)) {
					criteria.add(Expression.eq("group", group));
				}
				if (start > 0) {
					criteria.setFirstResult(start);
				}
				if (limit > 0) {
					criteria.setMaxResults(limit);
				}
				return criteria.list();
			}
		});
	}

	public List<?> queryByGroup(String group, Integer start, Integer limit)
			throws ConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Configuration configuration) throws ConfigurationException {
		if (StringUtil.isBlank(configuration.getId())) {
			configuration.setId(uniqueId());
		}
		Date now = TimeZoneUtil.getNow();
		if (configuration.getCreatedTime() == null) {
			configuration.setCreatedTime(now);
		}
		try {
			this.hibernateTemplate.saveOrUpdate(configuration);
		} catch (DataIntegrityViolationException e) {
			// Do not save
			logger.warn("Configuration already existed! 1");
		} catch (ConstraintViolationException e) {
			// Do not save
			logger.warn("Configuration already existed! 2");
		}
	}

	public Configuration queryByKey(final String key) throws ConfigurationException {
		return (Configuration) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Configuration.class);
				criteria.add(Expression.eq("key", key));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
	}

	public int queryCount(final String query) throws ConfigurationException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Configuration.class);
				if (StringUtil.notBlank(query)) {
					criteria.add(Expression.like("key", query, MatchMode.ANYWHERE));
				}
				criteria.setProjection(Projections.count("id"));
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : ((Integer) result).intValue();
	}

	public Configuration queryById(final String id) throws ConfigurationException {
		return (Configuration) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Configuration.class);
				criteria.add(Expression.eq("id", id));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
	}

}
