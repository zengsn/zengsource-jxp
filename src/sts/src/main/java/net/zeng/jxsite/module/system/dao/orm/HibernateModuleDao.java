/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.sql.SQLException;
import java.util.Date;

import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.StringUtil;
import net.zeng.jxsite.module.system.dao.ModuleDao;
import net.zeng.jxsite.module.system.model.Module;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateModuleDao extends HibernateDaoTemplate implements ModuleDao {
	
	public HibernateModuleDao() {
	}
	
	@Override
	public Class<?> getPrototypeClass() {
		return Module.class;
	}

	public void insert(Module module) {
		if (StringUtil.isBlank(module.getId())) {
			module.setId(uniqueId());
		}
		Date now = new Date();
		if (module.getCreatedTime() == null) {
			module.setCreatedTime(now);
		}
		module.setUpdatedTime(now);
		// Module dbModule = new Module(module);
		// this.hibernateTemplate.save(dbModule);
		this.hibernateTemplate.save(module);
	}

	public void save(Module module) {
		Date now = new Date();
		if (module.getCreatedTime() == null) {
			module.setCreatedTime(now);
		}
		module.setUpdatedTime(now);
		// Module dbModule = new Module(module);
		this.hibernateTemplate.saveOrUpdate(module);
		// this.hibernateTemplate.save(module);
	}

	public Module queryById(final String id) {
		Module module = (Module) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Module.class);
				criteria.add(Expression.eq("id", id));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
		return module;
	}

}
