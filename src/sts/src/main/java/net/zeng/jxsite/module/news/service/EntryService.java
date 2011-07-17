/**
 * 
 */
package net.zeng.jxsite.module.news.service;

import java.util.List;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.model.Entry;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface EntryService {

	public Integer getCount(String q, String category) throws NewsException;

	public List<?> search(String q, String category, Integer start, Integer limit) throws NewsException;

	public Entry getById(String id) throws NewsException;

	public void save(Entry entry) throws NewsException;

	public List<?> getLatest(int number) throws NewsException;

}
