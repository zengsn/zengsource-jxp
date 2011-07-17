/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao.orm;

import java.util.Date;

import net.zeng.jxsite.module.designlab.dao.DesignDao;
import net.zeng.jxsite.module.designlab.model.Design;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author snzeng
 * 
 */
public class HibernateDesignDao extends HibernateDaoTemplate implements DesignDao {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateDesignDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.zeng.spring.dao.orm.HibernateDaoTemplate#getPrototypeClass()
	 */
	@Override
	public Class<?> getPrototypeClass() {
		return Design.class;
	}
	
	public void save(Design design) {
		Date now = new Date();
		if(design.getCreatedTime() == null) {
			design.setCreatedTime(now);
		}
		design.setUpdatedTime(now);
		super.save(design);
	}
	
	public Design queryById(String id) {
		return (Design) super.queryById(id);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
