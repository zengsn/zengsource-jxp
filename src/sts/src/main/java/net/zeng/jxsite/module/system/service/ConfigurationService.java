/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import net.zeng.jxsite.module.ModuleException;
import net.zeng.jxsite.module.system.model.Configuration;

/**
 * @author snzeng
 * @since 6.0
 */
public interface ConfigurationService extends Serializable {

	// Initialization //////////////////////////////////////////////////////

	public void initConfigs(String xmlPath) throws ConfigurationException;
	
	public Properties getDefaults()throws ModuleException; 

	// Management //////////////////////////////////////////////////////////
	
	public void save(Configuration config) throws ConfigurationException;

	public Configuration getById(String id) throws ConfigurationException;

	public List<?> getConfigsByGroup(String group) throws ConfigurationException;

	public int getTotlaCount(String query) throws ConfigurationException;

	public List<?> search(String query, String group, int start, int limit) throws ConfigurationException;

	// Application /////////////////////////////////////////////////////////

	public Configuration getByKey(String key) throws ConfigurationException;

	public String getString(String key) throws ConfigurationException;

	public boolean getBoolean(String key) throws ConfigurationException;
	
	// Convenience ////////////////////////////////////////////////////////
	
	public String getSiteHome() throws ModuleException;
	
	public String getSiteUpload() throws ModuleException;
	
	public String getSiteTmp() throws ModuleException;
	
	public String getExtHome() throws ModuleException;
	
}
