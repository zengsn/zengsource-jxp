/**
 * 
 */
package net.zeng.jxsite.module.product.dao;

import java.util.List;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.model.Product;

/**
 * @author zeng.xiaoning
 *
 */
public interface ProductDao {

	public List<?> query(String q, String catalogId, Integer start, Integer limit) throws ProductException;

	public Integer queryCount(String q, String catalogId) throws ProductException;

	public Product queryById(String id) throws ProductException;

	public void save(Product product) throws ProductException;

}
