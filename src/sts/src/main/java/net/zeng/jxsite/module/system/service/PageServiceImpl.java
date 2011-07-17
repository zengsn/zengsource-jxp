/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import org.apache.log4j.Logger;

import net.zeng.jxsite.module.system.dao.PageDao;
import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class PageServiceImpl implements PageService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Logger logger = Logger.getLogger(getClass());

	private PageDao pageDao;
	private BlockInstanceService blockInstanceService;
	private BlockPrototypeService blockPrototypeService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public PageServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void save(Page page) throws PageException {
		if (page != null) {
			BlockPrototype defaultPrototype = page.getDefaultBlock();
			if (defaultPrototype == null) {
				logger.warn("No default block, use page!");
				defaultPrototype = this.blockPrototypeService.getByPageUrl(page.getUrl());
				if (defaultPrototype == null) {
					defaultPrototype = new BlockPrototype();
				}
				defaultPrototype.buildPageBlock(page);
				this.blockPrototypeService.save(defaultPrototype);
				page.setDefaultBlock(defaultPrototype);
			} else if (StringUtil.notBlank(defaultPrototype.getId())) {
				defaultPrototype = this.blockPrototypeService.getById(defaultPrototype.getId());
				page.setDefaultBlock(defaultPrototype);
			}

			this.pageDao.save(page);

			// Check instances
			// for (BlockInstance instance : page.getBlockInstances()) {
			// this.blockInstanceService.delete(instance);
			// }

			if (defaultPrototype != null && page.getNumOfBlocks() == 0) { // Add new default instance
				BlockInstance instance = new BlockInstance(defaultPrototype, page);
				logger.info("Save new block instance: " + instance.getName());
				this.blockInstanceService.save(instance);
			}
		}
	}

	public Integer searchCount(String q) throws PageException {
		return this.pageDao.queryCount(q);
	}

	public List<?> search(String q, Integer start, Integer limit) throws PageException {
		return this.pageDao.query(q, start, limit);
	}

	public Page getById(String pageId) throws PageException {
		if (StringUtil.isBlank(pageId)) {
			return null;
		}
		return this.pageDao.queryById(pageId);
	}

	public Page getByUrl(String url) throws PageException {
		if (StringUtil.isBlank(url)) {
			return null;
		}
		return this.pageDao.queryByUrl(url);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	public BlockInstanceService getBlockInstanceService() {
		return blockInstanceService;
	}

	public void setBlockInstanceService(BlockInstanceService blockInstanceService) {
		this.blockInstanceService = blockInstanceService;
	}

	public BlockPrototypeService getBlockPrototypeService() {
		return blockPrototypeService;
	}

	public void setBlockPrototypeService(BlockPrototypeService blockPrototypeService) {
		this.blockPrototypeService = blockPrototypeService;
	}

}
