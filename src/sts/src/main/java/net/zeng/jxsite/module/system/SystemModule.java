/**
 * 
 */
package net.zeng.jxsite.module.system;

import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.service.BlockInstanceService;
import net.zeng.jxsite.module.system.service.BlockPrototypeService;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.jxsite.module.system.service.PageService;
import net.zeng.jxsite.module.system.service.ThemeService;

/**
 * @author snzeng
 * @since 6.0
 * @deprecated
 */
public class SystemModule extends Module implements SystemConfigKeys {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_HOME = "/home";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configurationService;
	private BlockPrototypeService blockPrototypeService;
	private BlockInstanceService blockInstanceService;
	private PageService pageService;
	private ThemeService themeService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public SystemModule() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	
	public BlockInstanceService getBlockInstanceService() {
		return blockInstanceService;
	}
	
	public void setBlockInstanceService(BlockInstanceService blockInstanceService) {
		this.blockInstanceService = blockInstanceService;
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
	
	public ThemeService getThemeService() {
		return themeService;
	}
	
	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

}
