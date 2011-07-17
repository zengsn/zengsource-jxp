/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import net.zeng.jxsite.module.ModuleNoFoundException;
import net.zeng.jxsite.module.system.model.Menu;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.dao.MenuDao;
import net.zeng.jxsite.module.system.dao.ModuleDao;
import net.zeng.jxsite.module.system.model.Configuration;
import net.zeng.jxsite.module.system.model.BlockPrototype;

/**
 * @author snzeng
 * 
 */
public class ModuleServiceImpl implements ModuleService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private MenuDao menuDao;
	private ModuleDao moduleDao;
	private ConfigurationService configurationService;
	private BlockPrototypeService blockPrototypeService;
	private PageService pageService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ModuleServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// Application //////////////////////////////////////////////////

	public Module getModule(String id) throws ModuleNoFoundException {
		// Load configuration from database
		Module module = this.moduleDao.queryById(id);
		if (module == null) { // Init from configuration
			if ("system".equals(id)) { // System module is preload
				module = reloadModule(id);
			} else {
				module = initModule(id);
				if (module.isOnline()) { // Set from module.xml
					saveModule(module);
				}
			}
		}
		return module;
	}

	// Management //////////////////////////////////////////////////

	public void saveModule(Module module) throws ModuleNoFoundException {

		module.setStatus(Module.ONLINE);

		this.moduleDao.save(module);

		// Save menus
		for (Menu menu : module.getMenuSet()) {
			menu.setModule(module);
			this.menuDao.save(menu);
		}

		// Save blocks
		Set<BlockPrototype> prototypes = module.getBlockPrototypes();
		for (BlockPrototype prototype : prototypes) {
			prototype.setModule(module);
			this.blockPrototypeService.save(prototype);
		}

		// Save pages
		Set<Page> pages = module.getPages();
		for (Page page : pages) {
			page.setModule(module);
			this.pageService.save(page);
		}
	}

	public void deleteModule(String moduleId) throws ModuleNoFoundException {
		// TODO Auto-generated method stub

	}

	private Module initModule(String moduleId) throws ModuleNoFoundException {
		String moduleRoot = getModuleRoot();
		Module module = this.moduleDao.queryById(moduleId);
		if (module == null) {
			module = new Module();
		}

		// First-time initailization of module
		String configPath = moduleRoot + "/" + moduleId + "/" + module.getConfigFile();
		ModuleInitializer initializer = new XMLModuleInitializer(module, configPath);
		initializer.init();
		return module;
	}

	public Module reloadModule(String moduleId) throws ModuleNoFoundException {
		Module module = initModule(moduleId);
		this.saveModule(module);

		// Initialize module configurations
		for (Configuration config : module.getConfigurations()) {
			this.configurationService.save(config);
		}

		// Return the configuration in database
		return module;
	}

	public void reloadConfigurations(Module module) throws ModuleNoFoundException {
		module = initModule(module.getId());

		// Initialize module configurations
		for (Configuration config : module.getConfigurations()) {
			this.configurationService.save(config);
		}
	}

	private String getModuleRoot() {
		return this.configurationService.getSiteHome() + "/modules";
	}

	public List<Module> getAllModules() throws ModuleNoFoundException {
		File moduleRootDir = new File(getModuleRoot());
		File[] moduleDirArr = moduleRootDir.listFiles();
		List<Module> moduleList = new ArrayList<Module>();
		for (File moduleDir : moduleDirArr) {
			String moduleId = moduleDir.getName();
			Module module = getModule(moduleId);
			moduleList.add(module);
		}
		Collections.sort(moduleList, new Comparator<Module>() {
			public int compare(Module o1, Module o2) {
				return o1.getIndex() - o2.getIndex();
			}
		});
		return moduleList;
	}

	public void activateModule(String moduleId) throws ModuleNoFoundException {
		// TODO Auto-generated method stub

	}

	public void inactivateModule(String moduleId) throws ModuleNoFoundException {
		// TODO Auto-generated method stub

	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public ModuleDao getModuleDao() {
		return moduleDao;
	}

	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public BlockPrototypeService getBlockPrototypeService() {
		return blockPrototypeService;
	}

	public void setBlockPrototypeService(BlockPrototypeService blockPrototypeService) {
		this.blockPrototypeService = blockPrototypeService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

}
