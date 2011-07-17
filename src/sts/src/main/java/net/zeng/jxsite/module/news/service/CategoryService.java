/**
 * 
 */
package net.zeng.jxsite.module.news.service;

import java.util.List;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.model.Category;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface CategoryService {

	public List<?> listRoot() throws NewsException;

	public Category find(String name, String parent) throws NewsException;

	public Category getById(String id) throws NewsException;

	public void save(Category category) throws NewsException;

	public Category getDefault() throws NewsException;

}
