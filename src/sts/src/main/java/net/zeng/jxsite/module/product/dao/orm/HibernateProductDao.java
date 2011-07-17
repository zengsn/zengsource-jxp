/**
 * 
 */
package net.zeng.jxsite.module.product.dao.orm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.dao.ProductDao;
import net.zeng.jxsite.module.product.model.Product;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateProductDao extends HibernateDaoTemplate implements ProductDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateProductDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public Class<?> getPrototypeClass() {
		return Product.class;
	}

	public void save(Product product) throws ProductException {
		Date now = new Date();
		if (product != null) {
			if (product.getCreatedTime() == null) {
				product.setCreatedTime(now);
			}
			product.setUpdatedTime(now);
			if (StringUtil.isBlank(product.getId())) {
				product.setId(IDUtil.generate());
				this.hibernateTemplate.save(product);
			} else {
				this.hibernateTemplate.saveOrUpdate(product);
			}
		}
	}

	public List<?> query(final String q, final String catalogId, final Integer start,
			final Integer limit) {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Product.class);
				buildSearchCriteria(criteria, q, catalogId, start, limit);
				return criteria.list();
			}
		});
	}

	public Integer queryCount(final String q, final String catalogId) throws ProductException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Product.class);
				buildSearchCriteria(criteria, q, catalogId, null, null);
				criteria.setProjection(Projections.count("id"));
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	private void buildSearchCriteria(Criteria criteria, String q, String catalogId, Integer start,
			Integer limit) throws ProductException {
		if (StringUtil.notBlank(q)) {
			criteria.add(Expression.like("name", q));
		}
		if (StringUtil.notBlank(catalogId)) {
			criteria.add(Expression.sqlRestriction("pd_catalogid=?", catalogId, new StringType()));
		}
		if (start != null && start > 0) {
			criteria.setFirstResult(start);
		}
		if (limit != null && limit > 0) {
			criteria.setMaxResults(limit);
		}
	}

	public Product queryById(final String id) throws ProductException {
		return (Product) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Product.class);
				criteria.add(Expression.eq("id", id));
				return criteria.uniqueResult();
			}
		});
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
