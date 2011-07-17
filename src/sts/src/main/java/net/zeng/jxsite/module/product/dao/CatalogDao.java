/**
 * 
 */
package net.zeng.jxsite.module.product.dao;

import java.util.List;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.model.Catalog;

/**
 * @author zeng.xiaoning
 *
 */
public interface CatalogDao {

	public void save(Catalog catalog) throws ProductException;

	public Catalog query(String name, String parent) throws ProductException;

	public Catalog queryById(String id) throws ProductException;

	public List<?> queryByParent(Catalog parent) throws ProductException;
}
