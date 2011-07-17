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
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.dao.CatalogDao;
import net.zeng.jxsite.module.product.model.Catalog;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateCatalogDao extends HibernateDaoTemplate implements CatalogDao {

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateCatalogDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public Class<?> getPrototypeClass() {
		return Catalog.class;
	}

	public Catalog queryById(final String id) throws ProductException {
		return (Catalog) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Catalog.class);
				criteria.add(Expression.eq("id", id));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
	}

	public void save(Catalog catalog) throws ProductException {
		logger.info("Save Catalog: " + catalog);
		Date now = new Date();
		if (catalog.getCreatedTime() == null) {
			catalog.setCreatedTime(now);
		}
		catalog.setUpdatedTime(now);
		if (StringUtil.isBlank(catalog.getId())) {
			catalog.setId(IDUtil.generate());
			this.hibernateTemplate.save(catalog);
		} else {
			this.hibernateTemplate.saveOrUpdate(catalog);
		}
	}

	public Catalog query(final String name, final String parentId) throws ProductException {
		return (Catalog) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Catalog.class);
				criteria.add(Expression.eq("name", name));
				criteria.add(Expression.eq("parent.id", parentId));
				// criteria.add(Expression.sqlRestriction(""));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
	}

	public List<?> queryByParent(final Catalog parent) throws ProductException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Catalog.class);
				if (parent == null) {
					criteria.add(Expression.isNull("parent"));
				} else {
					criteria.add(Expression.eq("parent", parent));
				}
				return criteria.list();
			}
		});
	}

}
