/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.PageException;

/**
 * @author snzeng
 * 
 */
public interface PageDao {

	public Page queryById(String pageId) throws PageException;

	public Page queryByUrl(String url) throws PageException;

	public Integer queryCount(String q) throws PageException;

	public List<?> query(String q, Integer start, Integer limit) throws PageException;

	public void save(Page page) throws PageException;

}
