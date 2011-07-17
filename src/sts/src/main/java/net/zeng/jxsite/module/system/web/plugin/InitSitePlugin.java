/**
 * 
 */
package net.zeng.jxsite.module.system.web.plugin;

import javax.servlet.http.HttpServletRequest;

import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCContext;
import net.zeng.mvc.plugin.Plugable;
import net.zeng.mvc.plugin.PluginException;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class InitSitePlugin implements Plugable {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public InitSitePlugin() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void enable() throws PluginException {

		HttpServletRequest request = MVCContext.getInstance().getRequest();

		// Site name
		String siteName = configService.getString(SystemConstants.CFG_SITE_NAME);
		request.setAttribute("_SITE_", siteName == null ? "Welcome !" : siteName);

		// Theme name
		String siteTheme = configService.getString(SystemConstants.CFG_SITE_THEME);
		if (StringUtil.isBlank(siteTheme)) {
			siteTheme = "default";
		}
		request.setAttribute("_THEME_", siteTheme);

	}

	public void disable() throws PluginException {
		// TODO Auto-generated method stub

	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ConfigurationService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigurationService configService) {
		this.configService = configService;
	}
}
