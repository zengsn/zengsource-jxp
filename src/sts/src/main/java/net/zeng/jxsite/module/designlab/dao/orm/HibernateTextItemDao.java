/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao.orm;

import net.zeng.jxsite.module.designlab.dao.TextItemDao;
import net.zeng.jxsite.module.designlab.model.TextItem;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateTextItemDao extends HibernateDaoTemplate implements TextItemDao {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public HibernateTextItemDao() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return TextItem.class;
	}
	
	public TextItem queryById(String id) {
		return (TextItem) super.queryById(id);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
