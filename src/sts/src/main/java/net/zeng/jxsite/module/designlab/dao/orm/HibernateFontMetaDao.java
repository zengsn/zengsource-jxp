/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao.orm;

import java.util.List;

import org.hibernate.criterion.Criterion;

import net.zeng.jxsite.module.designlab.dao.FontMetaDao;
import net.zeng.jxsite.module.designlab.model.FontMeta;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;

/**
 * @author snzeng
 * @since 6.0
 */
public class HibernateFontMetaDao extends HibernateDaoTemplate implements FontMetaDao {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateFontMetaDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return FontMeta.class;
	}

	public List<?> queryAll() {
		return this.query(new Criterion[0], null, null);
	}
	
	public FontMeta queryById(String id) {
		return (FontMeta) super.queryById(id);
	}
	public void save(FontMeta font) {
		// TODO
		super.save(font);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
