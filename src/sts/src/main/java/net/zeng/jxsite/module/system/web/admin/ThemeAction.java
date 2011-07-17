/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.system.model.Theme;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.jxsite.module.system.service.ThemeService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;

/**
 * @author snzeng
 * 
 */
public class ThemeAction extends MultipleAction {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configurationService;
	private ThemeService themeService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ThemeAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		String themeRootDir = this.configurationService.getSiteHome() + "web/themes/";
		List<?> themes = getThemeService().getAll(themeRootDir);
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		for (Object obj : themes) {
			Theme theme = (Theme) obj;
			Element ele = root.addElement("theme");
			ele.addElement("id").addText(theme.getId() + "");
			ele.addElement("name").addText(theme.getName() + "");
		}
		return new XMLView(doc);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ThemeService getThemeService() {
		return themeService;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

}
