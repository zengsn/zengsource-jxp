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
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.system.dao.PageDao;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.PageException;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernatePageDao extends HibernateDaoTemplate implements PageDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernatePageDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public Class<?> getPrototypeClass() {
		return Page.class;
	}

	public void save(Page page) throws PageException {
		if (StringUtil.isBlank(page.getId())) {
			throw new PageException("Null Page ID!");
		}
		Date now = new Date();
		if (page.getCreatedTime() == null) {
			page.setCreatedTime(now);
		}
		page.setUpdatedTime(now);

		this.hibernateTemplate.saveOrUpdate(page);
	}

	public Page queryById(final String pageId) {
		return (Page) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Page.class);
				criteria.add(Expression.eq("id", pageId));
				return criteria.uniqueResult();
			}
		});
	}

	public Page queryByUrl(final String url) throws PageException {
		return (Page) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Page.class);
				criteria.add(Expression.eq("url", url));
				//criteria.add(Expression.like("url", url, MatchMode.END));
				return criteria.uniqueResult();
			}
		});
	}

	public Integer queryCount(final String q) throws PageException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Page.class);
				if (StringUtil.notBlank(q)) {
					criteria.add(Expression.like("name", q));
				}
				criteria.setProjection(Projections.count("id"));
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	public List<?> query(final String q, final Integer start, final Integer limit)
			throws PageException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Page.class);
				if (StringUtil.notBlank(q)) {
					criteria.add(Expression.like("name", q));
				}
				if (start != null && start > 0) {
					criteria.setFirstResult(start);
				}
				if (limit != null && limit > 0) {
					criteria.setMaxResults(limit);
				}
				return criteria.list();
			}
		});
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
