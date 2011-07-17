/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.Configuration;
import net.zeng.jxsite.module.system.service.ConfigurationException;

/**
 * @author snzeng
 *
 */
public interface ConfigurationDao {

	public void save(Configuration configuration) throws ConfigurationException;
	
	public Configuration queryByKey(String key) throws ConfigurationException;

	public List<?> query(String query, String group, int start, int limit) throws ConfigurationException;

	public List<?> queryByGroup(String group, Integer start, Integer limit) throws ConfigurationException;

	public int queryCount(String query) throws ConfigurationException;

	public Configuration queryById(String id) throws ConfigurationException;

	
}
