/**
 * 
 */
package net.zeng.jxsite.module.product.service;

import java.util.List;

import org.apache.log4j.Logger;

import net.zeng.cache.CacheService;
import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.dao.CatalogDao;
import net.zeng.jxsite.module.product.model.Catalog;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class CatalogServiceImpl implements CatalogService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	Logger logger = Logger.getLogger(getClass());

	private CacheService cacheService;
	private CatalogDao catalogDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CatalogServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Catalog getById(String id) throws ProductException {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return this.catalogDao.queryById(id);
	}

	public void save(Catalog catalog) throws ProductException {
		this.catalogDao.save(catalog);
	}

	public List<?> listRoot() throws ProductException {
		return this.catalogDao.queryByParent(null);
	}

	public Catalog find(String name, String parentId) throws ProductException {
		return this.catalogDao.query(name, parentId);
	}
	
	public Catalog getDefault() throws ProductException {
		Catalog defaultCatalog = getById(Catalog.DEFAULT);
		if(defaultCatalog == null) {
			defaultCatalog = new Catalog();
			defaultCatalog.setId(Catalog.DEFAULT);
			defaultCatalog.setName(Catalog.DEFAULT);
			defaultCatalog.setDescription("Default Catalog.");
			save(defaultCatalog);
		}
		return defaultCatalog;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public CatalogDao getCatalogDao() {
		return catalogDao;
	}

	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
}
