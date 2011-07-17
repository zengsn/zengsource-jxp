/**
 * 
 */
package net.zeng.jxsite.module.news.service;

import java.util.List;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.dao.EntryDao;
import net.zeng.jxsite.module.news.model.Entry;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class EntryServiceImpl implements EntryService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private EntryDao entryDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public EntryServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Integer getCount(String q, String categoryId) throws NewsException {
		return this.entryDao.queryCount(q, categoryId);
	}

	public List<?> search(String q, String categoryId, Integer start, Integer limit)
			throws NewsException {
		return this.entryDao.query(q, categoryId, start, limit);
	}

	public Entry getById(String id) throws NewsException {
		if (StringUtil.notBlank(id)) {
			return this.entryDao.queryById(id);
		}
		return null;
	}
	
	public void save(Entry entry) throws NewsException {
		if (entry != null) {
			this.entryDao.save(entry);
		}
	}
	
	public List<?> getLatest(int limit) throws NewsException {
		return this.entryDao.queryTop(limit);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public EntryDao getEntryDao() {
		return entryDao;
	}

	public void setEntryDao(EntryDao entryDao) {
		this.entryDao = entryDao;
	}
}
