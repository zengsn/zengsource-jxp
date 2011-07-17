/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.List;

import net.zeng.jxsite.module.system.model.Menu;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.service.ModuleService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.HTMLView;

/**
 * @author snzeng
 * 
 */
public class TreeAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ModuleService moduleService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public TreeAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String getIndent(int level) {
		String indent = "";
		for (int i = 0; i < level; i++) {
			indent += "  ";
		}
		return indent;
	}

	@Override
	protected AbstractView doService() throws MVCException {
		List<Module> modules = this.moduleService.getAllModules();
		StringBuilder sb = new StringBuilder();
		boolean first = true; // expand first node
		sb.append("["); // Start
		for (Module module : modules) {
			if (!module.isOnline()) {
				continue; // Only show menu of online modules
			}
			Menu menu = module.getAdminMenu();
			sb.append("{\n");
			sb.append("  id:'" + module.getId() + "',\n");
			sb.append("  text:'" + module.getName() + "',\n");
			sb.append("  expanded:" + first + ",\n");
			sb.append("  children:[");
			if (menu != null) {
				Menu[] children = menu.getSortedChildren();
				for (Menu item : children) {
					appendItem(sb, 2, item);
					sb.append(",");
				}
				if (sb.charAt(sb.length() - 1) == ',') {
					sb.deleteCharAt(sb.length() - 1);
				}
			}
			sb.append("]\n");
			sb.append("},");

			if (first) {
				first = false;
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]"); // End
		return new HTMLView(sb.toString());
	}

	private void appendItem(StringBuilder sb, int indent, Menu menu) {
		sb.append("{\n");
		sb.append(getIndent(indent) + "id:'" + menu.getId() + "',\n");
		sb.append(getIndent(indent) + "text:'" + menu.getName() + "',\n");
		sb.append(getIndent(indent) + "leaf:" + menu.isLeaf() + ",\n");
		if (!menu.isLeaf()) {
			sb.append(getIndent(indent) + "children:[");
			Menu[] children = menu.getSortedChildren();
			for (Menu item : children) {
				appendItem(sb, indent + 1, item);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]\n");
		} else {
			sb.append(getIndent(indent) + "href:'" + menu.getUrl() + "'\n");
		}
		sb.append(getIndent(indent - 1) + "}");
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

}
