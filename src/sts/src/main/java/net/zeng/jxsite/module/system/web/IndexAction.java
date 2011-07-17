/**
 * 
 */
package net.zeng.jxsite.module.system.web;

import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.Redirect;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.DispatchView;
import net.zeng.mvc.view.RedirectView;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class IndexAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configurationService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public IndexAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {

		// Get Home Page Setting
		String homePage = this.configurationService.getString(SystemConstants.CFG_SITE_INDEX);
		if (StringUtil.notBlank(homePage)) {
			logger.info("Home page is set to: " + homePage);
			String url = getContext().getFullContextPath() + homePage
					+ getContext().getActionSuffix();
			Redirect redirect = new Redirect(url);
			return new RedirectView(redirect);
		} else {
			homePage = "/index.jsp";
		}
		
		// Site name
		String siteName = this.configurationService.getString(SystemConstants.CFG_SITE_NAME);
		setAttribute("siteName", siteName == null ? "Welcome !" : "Welcome to " + siteName + "!");

		// Theme name
		String siteTheme = this.configurationService.getString(SystemConstants.CFG_SITE_THEME);
		if (StringUtil.isBlank(siteTheme)) {
			siteTheme = "default";
		}
		setAttribute("theme", "default");
		
		logger.info("Goto default home page: " + homePage + " ...");
		return new DispatchView(homePage);

	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}



}
