/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao.orm;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.dao.GalleryImageDao;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.model.GalleryImage;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateGalleryImageDao extends HibernateDaoTemplate implements GalleryImageDao {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateGalleryImageDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public Class<?> getPrototypeClass() {
		return GalleryImage.class;
	}

	public GalleryImage queryById(final String id) throws DesignLabException {
		return (GalleryImage) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(GalleryImage.class);
				criteria.add(Expression.eq("id", id));
				return criteria.uniqueResult();
			}
		});
	}

	public void save(GalleryImage galleryImage) throws DesignLabException {
		Date now = new Date();
		if (galleryImage.getCreatedTime() == null) {
			galleryImage.setCreatedTime(now);
		}
		galleryImage.setUpdatedTime(now);
		if (StringUtil.isBlank(galleryImage.getId())) {
			galleryImage.setId(IDUtil.generate());
		}
		this.hibernateTemplate.saveOrUpdate(galleryImage);
	}

	public int queryCount(final String q, final GalleryCatalog catalog) throws DesignLabException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(GalleryImage.class);
				prepareSearchCondition(criteria, q, catalog);
				criteria.setProjection(Projections.count("id"));
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	public List<?> query(final String q, final GalleryCatalog catalog, final int start, final int limit)
			throws DesignLabException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(GalleryImage.class);
				prepareSearchCondition(criteria, q, catalog);
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

	private void prepareSearchCondition(Criteria criteria, final String q,
			final GalleryCatalog catalog) {
		if (catalog != null) {
			criteria.add(Expression.eq("catalog", catalog));
		}
		if (StringUtil.notBlank(q)) {
			criteria.add(Expression.like("name", q, MatchMode.ANYWHERE));
			criteria.add(Expression.like("creator", q, MatchMode.ANYWHERE));
			criteria.add(Expression.like("description", q, MatchMode.ANYWHERE));
		}
	}

	public GalleryImage queryByFilename(String filename, GalleryCatalog catalog)
			throws DesignLabException {
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("filename", filename);
		criteriaMap.put("catalog", catalog);
		return (GalleryImage) queryUnique(criteriaMap);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
