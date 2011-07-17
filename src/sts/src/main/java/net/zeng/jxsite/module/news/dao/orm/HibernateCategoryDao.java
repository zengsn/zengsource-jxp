/**
 * 
 */
package net.zeng.jxsite.module.news.dao.orm;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.dao.CategoryDao;
import net.zeng.jxsite.module.news.model.Category;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.spring.dao.orm.SQLRestriction;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateCategoryDao extends HibernateDaoTemplate implements CategoryDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateCategoryDao() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.zeng.spring.dao.orm.HibernateDaoTemplate#getPrototypeClass()
	 */
	@Override
	public Class<?> getPrototypeClass() {
		return Category.class;
	}

	public List<?> queryByParent(final Category parent) throws NewsException {
		return this.hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Category.class);
				if (parent == null) {
					criteria.add(Expression.isNull("parent"));
				} else {
					criteria.add(Expression.eq("parent", parent));
				}
				return criteria.list();
			}
		});
	}

	public Category query(String name, String parentId) throws NewsException {
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		if (StringUtil.notBlank(name)) {
			criteriaMap.put("name", name);
		}
		if (StringUtil.notBlank(parentId)) {
			criteriaMap.put("nca_parentid=?", new SQLRestriction(parentId, new StringType()));
		} else {
			criteriaMap.put("parent", null);
		}
		return (Category) queryUnique(criteriaMap);
	}
	
	public Category queryById(String id) throws NewsException {
		return (Category) queryById((Object)id);
	}
	
	public void save(Category category) throws NewsException {
		save((Object) category);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
}
