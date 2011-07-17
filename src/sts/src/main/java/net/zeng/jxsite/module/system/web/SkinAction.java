/**
 * 
 */
package net.zeng.jxsite.module.system.web;

import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.InletServlet;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class SkinAction extends GenericAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private ConfigurationService configurationService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public SkinAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	protected AbstractView doService() throws MVCException {
		String siteTheme = configurationService.getString(SystemConstants.CFG_SITE_THEME);
		if (StringUtil.isBlank(siteTheme)) {
			siteTheme = "default";
		}
		String pageUrlSuffix = getContext().getURLSuffix(InletServlet.PARAM_URL_PAGE);
		setForward("/themes/" + siteTheme + "/index." + pageUrlSuffix);
		return super.doService();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}
	
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
}
