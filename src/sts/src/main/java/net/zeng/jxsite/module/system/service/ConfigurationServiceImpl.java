/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.zeng.cache.CacheService;
import net.zeng.jxsite.Constants;
import net.zeng.jxsite.module.ModuleException;
import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.dao.ConfigurationDao;
import net.zeng.jxsite.module.system.model.Configuration;
import net.zeng.util.ClassLoaderUtil;
import net.zeng.util.PropertiesUtil;
import net.zeng.util.StringUtil;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author snzeng
 * 
 */
public class ConfigurationServiceImpl implements ConfigurationService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;
	private static final String CACHE_ID = "cache-configs";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	Logger logger = Logger.getLogger(getClass());

	private ConfigurationDao configurationDao;
	private CacheService cacheService;
	private String cacheMode;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigurationServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Properties getDefaults() throws ModuleException {
		Properties props = new Properties();
		try {
			props.load(ClassLoaderUtil.getResourceAsStream("defaults.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	public void initConfigs(String xmlPath) throws ConfigurationException {
		// Parse XML
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(xmlPath));
			Element root = doc.getRootElement();
			String group = root.attributeValue("group");
			if (StringUtil.isBlank(group)) {
				throw new NullGroupException("Null group of configuration!");
			}
			// Check database
			List<?> configList = getConfigsByGroup(group);
			if (configList != null && configList.size() > 0) {
				throw new DuplicatedInitializationException(
						"Group configurations are already initialized!");
			}
			List<?> configs = root.elements();
			for (Object obj : configs) {
				Element ele = (Element) obj;
				String key = ele.elementText("key");
				if (StringUtil.isBlank(key)) {
					throw new NullKeyExcpetion("Null key of configuration!");
				}
				if (!key.startsWith(group + ".")) {
					throw new IllegalKeyExcpetion("Key should start with \"GROUP.\"!");
				}
				String value = ele.elementText("value");
				Configuration config = new Configuration();
				config.setKey(key);
				config.setGroup(group);
				config.setValue(value);
				// Save to DB
				this.configurationDao.save(config);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ConfigurationException(e);
		}
	}

	private boolean enableCache() {
		Configuration config = null;
		if (this.cacheMode == null) {
			String key = SystemConstants.CFG_CACHE_MODE;
			config = this.configurationDao.queryByKey(key);
			if (config != null) {
				this.cacheMode = config.getValue();
			}
		}
		return Constants.CACHE_IN_MEMORY.equals(this.cacheMode);
	}

	private Configuration getConfigurationFromCache(String key) {
		Map<?, ?> cache = (Map<?, ?>) this.cacheService.get(CACHE_ID);
		if (cache == null) {
			// cache = new HashMap<String, Configuration>();
			// this.cacheService.cache(CACHE_ID, cache);
			return null;
		}
		// return cache;
		return (Configuration) cache.get(key);
	}

	@SuppressWarnings("unchecked")
	private void cacheConfiguration(Configuration config) {
		Map<String, Configuration> cacheObj = (Map<String, Configuration>) this.cacheService
				.get(CACHE_ID);
		if (cacheObj == null) {
			cacheObj = new HashMap<String, Configuration>();
			this.cacheService.cache(CACHE_ID, cacheObj);
		}
		cacheObj.put(config.getKey(), config);
	}

	private void removeFromCache(Configuration config) {
		Map<?, ?> cacheObj = (Map<?, ?>) this.cacheService.get(CACHE_ID);
		if (cacheObj != null) {
			cacheObj.remove(config.getKey());
		}
	}

	// Management //////////////////////////////////////////////////////////

	public Configuration getByKey(String key) throws ConfigurationException {
		Configuration config = null;
		if (enableCache()) { // find from cache
			config = getConfigurationFromCache(key);
		}
		if (config == null) { // find from database
			config = this.configurationDao.queryByKey(key);
			if (enableCache() && config != null) { // cahce to memory
				cacheConfiguration(config);
			}
		}
		if (config == null) { // find default configuration
			Properties defaults = getDefaults();
			String value = PropertiesUtil.getValue(defaults, key);
			config = new Configuration();
			config.setKey(key);
			config.setName(value);
			config.setValue(value);
			if (key.indexOf(".") > -1) {
				config.setGroup(key.substring(0, key.indexOf(".")));
			}
			save(config); // save
		}
		return config;
	}

	public void save(Configuration config) throws ConfigurationException {
		Configuration dbConfig = this.configurationDao.queryByKey(config.getKey());
		if (dbConfig != null) {
			dbConfig.copyData(config);
			this.configurationDao.save(dbConfig);
			removeFromCache(config);
		}
		this.configurationDao.save(config);
	}

	public Configuration getById(String id) throws ConfigurationException {
		return this.configurationDao.queryById(id);
	}

	public List<?> getConfigsByGroup(String group) throws ConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTotlaCount(String query) throws ConfigurationException {
		return this.configurationDao.queryCount(query);
	}

	public List<?> search(String query, String group, int start, int limit)
			throws ConfigurationException {
		return this.configurationDao.query(query, group, start, limit);
	}

	// Application /////////////////////////////////////////////////////////

	public String getString(String key) throws ConfigurationException {
		Configuration config = getByKey(key);
		return config == null ? null : config.getValue();
	}

	public boolean getBoolean(String key) throws ConfigurationException {
		Configuration config = getByKey(key);
		return config != null && config.getBoolean();
	}

	// Convenience ////////////////////////////////////////////////////////

	public String getSiteHome() throws ModuleException {
		return getString(SystemConstants.CFG_SITE_HOME);
	}
	
	public String getSiteUpload() throws ModuleException {
		return getString(SystemConstants.CFG_SITE_UPLOAD);
	}
	
	public String getSiteTmp() throws ModuleException {
		return getString(SystemConstants.CFG_SITE_TMP);
	}
	
	public String getExtHome() throws ModuleException {
		return getString(SystemConstants.CFG_EXT_HOME);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigurationDao getConfigurationDao() {
		return configurationDao;
	}

	public void setConfigurationDao(ConfigurationDao configurationDao) {
		this.configurationDao = configurationDao;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

}
