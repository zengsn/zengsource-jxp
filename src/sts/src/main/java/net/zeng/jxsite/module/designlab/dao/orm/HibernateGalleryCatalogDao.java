/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao.orm;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.dao.GalleryCatalogDao;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class HibernateGalleryCatalogDao extends HibernateDaoTemplate implements GalleryCatalogDao {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateGalleryCatalogDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return GalleryCatalog.class;
	}

	public void save(GalleryCatalog catalog) throws DesignLabException {
		Date now = new Date();
		if (catalog.getCreatedTime() == null) {
			catalog.setCreatedTime(now);
		}
		catalog.setUpdatedTime(now);
		if (StringUtil.isBlank(catalog.getId())) {
			catalog.setId(IDUtil.generate());
		}
		this.hibernateTemplate.saveOrUpdate(catalog);
	}

	public GalleryCatalog queryById(final String id) throws DesignLabException {
		return (GalleryCatalog) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(GalleryCatalog.class);
				criteria.add(Expression.eq("id", id));
				return criteria.uniqueResult();
			}
		});
	}

	public List<?> queryByParent(final GalleryCatalog parent) throws DesignLabException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(GalleryCatalog.class);
				if (parent == null) {
					criteria.add(Expression.isNull("parent"));
				} else {
					criteria.add(Expression.eq("parent", parent));
				}
				return criteria.list();
			}
		});
	}

	public GalleryCatalog queryInParent(final String name, final GalleryCatalog parent)
			throws DesignLabException {
		return (GalleryCatalog) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(GalleryCatalog.class);
				criteria.add(Expression.eq("name", name));
				criteria.add(Expression.eq("parent", parent));
				return criteria.uniqueResult();
			}
		});
	}

	public GalleryCatalog queryByDirectory(String dirName, GalleryCatalog parent)
			throws DesignLabException {
		// Map<String, Object> criteriaMap = new HashMap<String, Object>();
		// criteriaMap.put("dirName", dirName);
		// criteriaMap.put("parent", parent);
		// return (GalleryCatalog) queryUnique(criteriaMap);

		// return (GalleryCatalog) this.hibernateTemplate.execute(new
		// HibernateCallback() {
		// @Override
		// public Object doInHibernate(Session session) throws
		// HibernateException, SQLException {
		// Criteria criteria = session.createCriteria(getPrototypeClass());
		// criteria.add(Expression.eq("dirName", dirName));
		// if (parent == null) {
		// criteria.add(Expression.isNull("parent"));
		// } else {
		// criteria.add(Expression.sqlRestriction("this_.lgc_parentid = ?",
		// parent.getId(), new StringType()));
		// }
		// return criteria.uniqueResult();
		// }
		// });

		if (parent == null) {
			return (GalleryCatalog) queryUnique(new Criterion[] {
					Expression.eq("dirName", dirName), Expression.isNull("parent") });
		} else {
			return (GalleryCatalog) queryUnique(new Criterion[] {
					Expression.eq("dirName", dirName),
					Expression.sqlRestriction("this_.lgc_parentid = ?", parent.getId(),
							new StringType()) });
		}
	}
	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
