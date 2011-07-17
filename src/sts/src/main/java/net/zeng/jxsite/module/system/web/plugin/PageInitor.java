/**
 * 
 */
package net.zeng.jxsite.module.system.web.plugin;

import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.PageService;
import net.zeng.mvc.plugin.PluginException;
import net.zeng.mvc.plugin.PluginTemplate;

/**
 * @author snzeng
 * @since 6.0
 * @deprecated replaced with InitPagePlugin
 */
public class PageInitor extends PluginTemplate {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private PageService pageService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public PageInitor() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void enable() throws PluginException {
		String url = getContext().getActionHierachy();
		Page page = this.pageService.getByUrl("/" + url);
		if (page != null) {
			logger.info("View page: " + url);
			getRequest().setAttribute("page", page);
		} else {
			logger.warn("No such page: " + url);
			throw new PluginException("No such page: " + url);
		}
	}

	public void disable() throws PluginException {
		// TODO Auto-generated method stub

	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

}
