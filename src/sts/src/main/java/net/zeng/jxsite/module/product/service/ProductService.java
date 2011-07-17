/**
 * 
 */
package net.zeng.jxsite.module.product.service;

import java.util.List;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.model.Product;

/**
 * @author zeng.xiaoning
 * 
 */
public interface ProductService {

	public List<?> search(String q, String catalogId, Integer start, Integer limit)
			throws ProductException;

	Integer getCount(String q, String catalogId) throws ProductException;

	public Product getById(String id) throws ProductException;

	public void save(Product product) throws ProductException;

}
