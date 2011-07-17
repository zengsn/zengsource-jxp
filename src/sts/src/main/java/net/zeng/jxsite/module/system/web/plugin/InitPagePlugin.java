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
 */
public class InitPagePlugin extends PluginTemplate {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private PageService pageService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public InitPagePlugin() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void enable() throws PluginException {
		String url = getContext().getActionHierachy();
		// String pageURLSuffix =
		// MVCContext.getInstance().getURLSuffix(InletServlet.PARAM_URL_PAGE);
		// if (!url.endsWith(pageURLSuffix)) {
		// return; // Only handle page URL
		// }
		int poc = url.lastIndexOf(".");
		if (poc > -1) {
			url = url.substring(0, poc);
		}
		Page page = this.pageService.getByUrl("/" + url);
		if (page != null) {
			getRequest().setAttribute("_PAGE_", page);
			logger.info("### View page: " + url + " with blocks: " + page.getCurrentBlocks());
			// } else if (url != null && url.startsWith("admin")) {
			// logger.warn("Ingnore admin page: " + url);
		} else {
			logger.warn("No such page: " + url);
			logger.warn("Do NOT use page URL suffix for none page accessing!!!");
			// throw new PluginException("No such page: " + url);
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
