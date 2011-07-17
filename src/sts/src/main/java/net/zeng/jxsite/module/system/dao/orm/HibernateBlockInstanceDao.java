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
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.system.dao.BlockInstanceDao;
import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.BlockException;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class HibernateBlockInstanceDao extends HibernateDaoTemplate implements BlockInstanceDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateBlockInstanceDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public Class<?> getPrototypeClass() {
		return BlockInstance.class;
	}

	public void save(BlockInstance instance) throws BlockException {
		Date now = new Date();
		if (instance.getCreatedTime() == null) {
			instance.setCreatedTime(now);
		}
		instance.setUpdatedTime(now);
		if (StringUtil.isBlank(instance.getId())) {
			instance.setId(IDUtil.generate());
			this.hibernateTemplate.save(instance);
		} else {
			this.hibernateTemplate.saveOrUpdate(instance);
		}

	}

	public Integer queryCount(final Page page, final String q) throws BlockException {
		Object result = this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockInstance.class);
				if (page != null) {
					criteria.add(Expression.eq("page", page));
				}
				if (StringUtil.notBlank(q)) {
					criteria.add(Expression.like("name", q, MatchMode.ANYWHERE));
				}
				criteria.setProjection(Projections.count("id"));
				return criteria.uniqueResult();
			}
		});
		return result == null ? 0 : (Integer) result;
	}

	public List<?> query(final Page page, final String q, final Integer start, final Integer limit)
			throws BlockException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockInstance.class);
				if (page != null) {
					criteria.add(Expression.eq("page", page));
				}
				if (StringUtil.notBlank(q)) {
					criteria.add(Expression.like("name", q, MatchMode.ANYWHERE));
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

	public BlockInstance queryById(final String id) throws BlockException {
		return (BlockInstance) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockInstance.class);
				criteria.add(Expression.eq("id", id));
				return criteria.uniqueResult();
			}
		});
	}

	public List<?> query(final BlockPrototype prototype) throws BlockException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockInstance.class);
				criteria.add(Expression.eq("prototype", prototype));
				return criteria.list();
			}
		});
	}

	public BlockInstance query(final Page page, final BlockPrototype prototype)
			throws BlockException {
		return (BlockInstance) this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(BlockInstance.class);
				criteria.add(Expression.eq("page", page));
				criteria.add(Expression.eq("prototype", prototype));
				return criteria.uniqueResult();
			}
		});
	}

	public void delete(BlockInstance instance) throws BlockException {
		this.hibernateTemplate.delete(instance);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
