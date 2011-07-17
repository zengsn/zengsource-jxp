/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.ArrayList;
import java.util.List;

import net.zeng.jxsite.module.system.model.Menu;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.service.ModuleService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;

/**
 * @author snzeng
 * 
 */
public class MainAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ModuleService moduleService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public MainAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
		List<Module> modules = this.moduleService.getAllModules();
		setAttribute("modules", modules);

		// All leaf menu
		List<Menu> leafMenuList = new ArrayList<Menu>();
		for (Module module : modules) {
			if (!module.isOnline()) {
				continue;
			}
			Menu adminMenu = module.getAdminMenu();
			if (adminMenu != null) {
				leafMenuList.addAll(adminMenu.getLeaves());
			}
		}
		setAttribute("leafMenuList", leafMenuList);

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
