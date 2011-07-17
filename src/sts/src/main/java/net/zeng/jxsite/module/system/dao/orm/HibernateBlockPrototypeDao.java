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

import net.zeng.jxsite.module.system.dao.BlockPrototypeDao;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.service.BlockException;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateBlockPrototypeDao extends HibernateDaoTemplate implements BlockPrototypeDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateBlockPrototypeDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return BlockPrototype.class;
	}

	public void save(BlockPrototype prototype) {
		Date now = new Date();
		if (prototype.getCreatedTime() == null) {
			prototype.setCreatedTime(now);
		}
		prototype.setUpdatedTime(now);
		if (StringUtil.isBlank(prototype.getId())) {
			prototype.setId(IDUtil.generate());
			this.hibernateTemplate.save(prototype);
		} else {
			this.hibernateTemplate.saveOrUpdate(prototype);
		}
	}

	public BlockPrototype queryById(final String id) throws BlockException {
		return (BlockPrototype) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockPrototype.class);
				criteria.add(Expression.eq("id", id));
				return criteria.uniqueResult();
			}
		});
	}
	
	public BlockPrototype queryByPageUrl(String pageUrl) throws BlockException {
		return (BlockPrototype) queryUnique("pageUrl", pageUrl);
	}

	public Integer queryCount(final String q) throws BlockException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockPrototype.class);
				if (StringUtil.notBlank(q)) {
					criteria.add(Expression.like("name", q));
				}
				criteria.setProjection(Projections.count("id"));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	public Integer queryCount(final Module module) throws BlockException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockPrototype.class);
				if (module != null) {
					criteria.add(Expression.eq("module", module));
				}
				criteria.setProjection(Projections.count("id"));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	public List<?> query(final String q, final Integer start, final Integer limit)
			throws BlockException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockPrototype.class);
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

	public List<?> query(final Module module, final Integer start, final Integer limit)
			throws BlockException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockPrototype.class);
				if (module != null) {
					criteria.add(Expression.eq("module", module));
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
