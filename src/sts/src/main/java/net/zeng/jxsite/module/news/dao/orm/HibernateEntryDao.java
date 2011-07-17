/**
 * 
 */
package net.zeng.jxsite.module.news.dao.orm;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.dao.EntryDao;
import net.zeng.jxsite.module.news.model.Entry;
import net.zeng.jxsite.module.product.ProductException;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateEntryDao extends HibernateDaoTemplate implements EntryDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateEntryDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return Entry.class;
	}

	public Integer queryCount(final String q, final String categoryId) throws NewsException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Entry.class);
				buildSearchCriteria(criteria, q, categoryId, null, null);
				criteria.setProjection(Projections.count("id"));
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	private void buildSearchCriteria(Criteria criteria, String q, String categoryId,
			Integer start, Integer limit) throws ProductException {
		if (StringUtil.notBlank(q)) {
			criteria.add(Expression.like("title", q));
		}
		if (StringUtil.notBlank(categoryId)) {
			criteria.add(Expression.sqlRestriction("nen_categoryid=?", categoryId,
					new StringType()));
		}
		if (start != null && start > 0) {
			criteria.setFirstResult(start);
		}
		if (limit != null && limit > 0) {
			criteria.setMaxResults(limit);
		}
	}

	public List<?> query(final String q, final String categoryId, Integer start, Integer limit)
			throws NewsException {
		// Map<String, Object> criteriaMap = new HashMap<String, Object>();
		// criteriaMap.put("title", new LikeMatcher(q, MatchMode.ANYWHERE));
		// criteriaMap.put("nen_categoryid=?", new SQLRestriction(categoryId,
		// new StringType()));
		// return query(criteriaMap, start, limit);
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Entry.class);
				buildSearchCriteria(criteria, q, categoryId, null, null);
				return criteria.list();
			}
		});
	}

	public Entry queryById(String id) throws NewsException {
		return (Entry) queryById((Object) id);
	}

	public void save(Entry entry) throws NewsException {
		if (entry != null) {
			save((Object) entry);
		}
	}

	public List<?> queryTop(final int limit) throws NewsException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Entry.class);
				criteria.addOrder(Order.desc("createdTime"));
				criteria.setMaxResults(limit);
				return criteria.list();
			}
		});
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
}
