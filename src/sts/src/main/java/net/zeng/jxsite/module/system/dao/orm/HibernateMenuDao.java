/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.StringUtil;
import net.zeng.jxsite.module.system.dao.MenuDao;
import net.zeng.jxsite.module.system.model.Menu;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateMenuDao extends HibernateDaoTemplate implements MenuDao {
	
	public HibernateMenuDao() {
	}
	
	@Override
	public Class<?> getPrototypeClass() {
		return Menu.class;
	}

	public void save(Menu menu) {
		if (StringUtil.isBlank(menu.getId())) {
			menu.setId(uniqueId());
		}
		Date now = new Date();
		if (menu.getCreatedTime() == null) {
			menu.setCreatedTime(now);
		}
		menu.setUpdatedTime(now);
		logger.info("Save menu: " + menu.getName());

		Set<Menu> children = menu.getChildren();
		if (children != null && children.size() > 0) {
			for (Menu item : children) {
				item.setModule(menu.getModule());
				this.save(item);
			}
		}

		this.hibernateTemplate.saveOrUpdate(menu);
	}

	public Menu queryById(final String id) {
		return (Menu) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Menu.class);
				criteria.add(Expression.eq("id", id));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
	}

	public void delete(Menu menu) {
		this.hibernateTemplate.delete(menu);
	}

}
