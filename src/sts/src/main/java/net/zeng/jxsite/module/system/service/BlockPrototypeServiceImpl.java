/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;
import java.util.Set;

import net.zeng.jxsite.module.system.dao.BlockPrototypeDao;
import net.zeng.jxsite.module.system.dao.BlockSettingDao;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.BlockSetting;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class BlockPrototypeServiceImpl implements BlockPrototypeService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private BlockSettingDao blockSettingDao;
	private BlockPrototypeDao blockPrototypeDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockPrototypeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void save(BlockPrototype prototype) throws BlockException {
		if (prototype == null) {
			return;
		}
		// Save  prototype
		this.blockPrototypeDao.save(prototype);
		
		Set<BlockSetting> settings = prototype.getSettings();
		// Save settings
		if (settings != null) {
			for (BlockSetting setting : settings) {
				 setting.setPrototype(prototype);
				this.blockSettingDao.save(setting);
			}
		}
	}

	public Integer searchCount(String q) throws BlockException {
		return this.blockPrototypeDao.queryCount(q);
	}

	public Integer searchCount(Module module) throws BlockException {
		return this.blockPrototypeDao.queryCount(module);
	}

	public List<?> search(String q, Integer start, Integer limit) throws BlockException {
		return this.blockPrototypeDao.query(q, start, limit);
	}

	public List<?> search(Module module, Integer start, Integer limit) throws BlockException {
		return this.blockPrototypeDao.query(module, start, limit);
	}

	public BlockPrototype getById(String id) throws BlockException {
		if (StringUtil.notBlank(id)) {
			return this.blockPrototypeDao.queryById(id);
		}
		return null;
	}
	
	public BlockPrototype getByPageUrl(String pageUrl) throws BlockException {
		if (StringUtil.notBlank(pageUrl)) {
			return this.blockPrototypeDao.queryByPageUrl(pageUrl);
		}
		return null;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockSettingDao getBlockSettingDao() {
		return blockSettingDao;
	}

	public void setBlockSettingDao(BlockSettingDao blockSettingDao) {
		this.blockSettingDao = blockSettingDao;
	}

	public BlockPrototypeDao getBlockPrototypeDao() {
		return blockPrototypeDao;
	}

	public void setBlockPrototypeDao(BlockPrototypeDao blockPrototypeDao) {
		this.blockPrototypeDao = blockPrototypeDao;
	}

}
