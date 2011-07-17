/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao.orm;

import java.util.Date;

import net.zeng.jxsite.module.designlab.dao.ImageItemDao;
import net.zeng.jxsite.module.designlab.model.ImageItem;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author snzeng
 *
 */
public class HibernateImageItemDao extends HibernateDaoTemplate implements ImageItemDao {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public HibernateImageItemDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return ImageItem.class;
	}

	public void save(ImageItem image) {
		Date now = new Date();
		if(image.getCreatedTime() == null) {
			image.setCreatedTime(now);
		}
		image.setUpdatedTime(now);
		super.save(image);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
