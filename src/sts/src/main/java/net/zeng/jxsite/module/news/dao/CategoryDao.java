/**
 * 
 */
package net.zeng.jxsite.module.news.dao;

import java.util.List;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.model.Category;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface CategoryDao {

	public List<?> queryByParent(Category parent) throws NewsException;

	public Category query(String name, String parentId) throws NewsException;

	public Category queryById(String id) throws NewsException;

	public void save(Category category) throws NewsException;

}
