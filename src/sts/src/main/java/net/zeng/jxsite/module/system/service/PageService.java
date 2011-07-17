/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.model.Page;

/**
 * @author snzeng
 *
 */
public interface PageService {

	public List<?> search(String q, Integer start, Integer limit) throws PageException;

	public Integer searchCount(String q) throws PageException;

	public void save(Page page) throws PageException;

	public Page getById(String pageId) throws PageException;

	public Page getByUrl(String url) throws PageException;

}
