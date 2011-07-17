/**
 * 
 */
package net.zeng.jxsite.module.system.web;

import net.zeng.jxsite.module.system.service.ModuleService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;

/**
 * @author snzeng
 * 
 */
public class HomeAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ModuleService moduleService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HomeAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
//		logger.info("Go to home page ...");
//		SystemModule systemModule = this.moduleService.getSystemModule();
//		ConfigurationService configService = systemModule.getConfigurationService();
//		// Site name
//		String siteName = configService.getString(SystemConfigKeys.SITE_NAME);
//		setAttribute("siteName", siteName == null ? "Welcome !" : "Welcome to " + siteName + "!");
//		// Home Page
//		String siteTheme = configService.getString(SystemConfigKeys.SITE_THEME);
//		if (StringUtil.isBlank(siteTheme)) {
//			siteTheme = "default";
//		}
//		String homePage = "/themes/" + siteTheme + "/index.jsp";
//		return new DispatchView(homePage);
		return super.doService();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

}
