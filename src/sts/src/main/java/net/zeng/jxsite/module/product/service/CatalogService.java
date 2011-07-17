/**
 * 
 */
package net.zeng.jxsite.module.product.service;

import java.util.List;

import net.zeng.jxsite.module.product.ProductException;
import net.zeng.jxsite.module.product.model.Catalog;

/**
 * @author zeng.xiaoning
 *
 */
public interface CatalogService {

	public void save(Catalog catalog) throws ProductException;

	public List<?> listRoot() throws ProductException;

	public Catalog find(String name, String parent) throws ProductException;

	public Catalog getById(String id) throws ProductException;
	
	public Catalog getDefault() throws ProductException;
	
}
