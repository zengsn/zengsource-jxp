/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;
import java.util.Set;

import net.zeng.jxsite.module.system.dao.BlockInstanceDao;
import net.zeng.jxsite.module.system.dao.BlockSettingDao;
import net.zeng.jxsite.module.system.dao.PageDao;
import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.BlockSetting;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class BlockInstanceServiceImpl implements BlockInstanceService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private PageDao pageDao;
	private BlockSettingDao blockSettingDao;
	private BlockInstanceDao blockInstanceDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockInstanceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void save(BlockInstance instance) throws BlockException {
		if (instance == null) {
			return;
		}
		Set<BlockSetting> settings = instance.getSettings();
		// Save instance
		instance.setSettings(null);
		this.blockInstanceDao.save(instance);
		// Save settings
		for (BlockSetting setting : settings) {
			// setting.setInstance(instance);
			this.blockSettingDao.save(setting);
		}
		// Save settings' instance
		instance.setSettings(settings);
		this.blockInstanceDao.save(instance);
	}

	public Integer searchCount(String pageId, String q) throws BlockException {
		Page page = this.pageDao.queryById(pageId);
		if (page == null) {
			return 0;
		}
		return this.blockInstanceDao.queryCount(page, q);
	}

	public List<?> search(String pageId, String q, Integer start, Integer limit)
			throws BlockException {
		Page page = this.pageDao.queryById(pageId);
		if (page == null) {
			return null;
		}
		return this.blockInstanceDao.query(page, q, start, limit);
	}

	public BlockInstance search(Page page, BlockPrototype prototype) throws BlockException {
		if (page == null || prototype == null) {
			return null;
		}
		return this.blockInstanceDao.query(page, prototype);
	}

	public BlockInstance getById(String id) throws BlockException {
		if (StringUtil.notBlank(id)) {
			return this.blockInstanceDao.queryById(id);
		}
		return null;
	}

	public List<?> search(BlockPrototype prototype) throws BlockException {
		if (prototype == null) {
			return null;
		}
		return this.blockInstanceDao.query(prototype);
	}

	public void delete(BlockInstance instance) throws BlockException {
		if (instance != null) {
			this.blockInstanceDao.delete(instance);
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	public BlockSettingDao getBlockSettingDao() {
		return blockSettingDao;
	}

	public void setBlockSettingDao(BlockSettingDao blockSettingDao) {
		this.blockSettingDao = blockSettingDao;
	}

	public BlockInstanceDao getBlockInstanceDao() {
		return blockInstanceDao;
	}

	public void setBlockInstanceDao(BlockInstanceDao blockInstanceDao) {
		this.blockInstanceDao = blockInstanceDao;
	}
}
