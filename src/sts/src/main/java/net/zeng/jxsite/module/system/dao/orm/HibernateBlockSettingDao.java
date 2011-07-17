/**
 * 
 */
package net.zeng.jxsite.module.system.dao.orm;

import java.util.Date;

import net.zeng.jxsite.module.system.dao.BlockSettingDao;
import net.zeng.jxsite.module.system.model.BlockSetting;
import net.zeng.util.spring.dao.orm.HibernateDaoTemplate;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class HibernateBlockSettingDao extends HibernateDaoTemplate implements BlockSettingDao {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HibernateBlockSettingDao() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public Class<?> getPrototypeClass() {
		return BlockSetting.class;
	}

	public void save(BlockSetting setting) {
		Date now = new Date();
		if (setting.getCreatedTime() == null) {
			setting.setCreatedTime(now);
		}
		setting.setUpdatedTime(now);

		if (StringUtil.isBlank(setting.getId())) {
			setting.setId(IDUtil.generate());
			this.hibernateTemplate.save(setting);
		} else {
			this.hibernateTemplate.update(setting);
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
