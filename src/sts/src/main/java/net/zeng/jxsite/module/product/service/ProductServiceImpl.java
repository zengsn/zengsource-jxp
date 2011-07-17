/**
 * 
 */
package net.zeng.jxsite.module.product.service;

import java.util.List;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.dao.ProductDao;
import net.zeng.jxsite.module.product.model.Product;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class ProductServiceImpl implements ProductService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ProductDao productDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ProductServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public List<?> search(String q, String catalogId, Integer start, Integer limit)
			throws ProductException {
		return this.productDao.query(q, catalogId, start, limit);
	}

	public Integer getCount(String q, String catalogId) throws ProductException {
		return this.productDao.queryCount(q, catalogId);
	}

	public Product getById(String id) throws ProductException {
		if (StringUtil.notBlank(id)) {
			return this.productDao.queryById(id);
		}
		return null;
	}
	
	public void save(Product product) throws ProductException {
		if(product != null) {
			this.productDao.save(product);
		}		
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
