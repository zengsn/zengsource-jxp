/**
 * 
 */
package net.zeng.jxsite.module.news.dao;

import java.util.List;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.model.Entry;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface EntryDao {

	public Integer queryCount(String q, String categoryId) throws NewsException;

	public List<?> query(String q, String categoryId, Integer start, Integer limit) throws NewsException;

	public Entry queryById(String id) throws NewsException;

	public void save(Entry entry) throws NewsException;

	public List<?> queryTop(int limit) throws NewsException;

}
