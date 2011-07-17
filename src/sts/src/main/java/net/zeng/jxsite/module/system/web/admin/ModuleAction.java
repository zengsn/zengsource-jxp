/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.service.ModuleService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class ModuleAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ModuleService moduleService;

	private String moduleId;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ModuleAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/** action=listModules */
	public AbstractView doListModules() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		List<Module> modules = this.moduleService.getAllModules();
		// logger.info("Get modules: " + modules);

		// Total count
		root.addElement("totalCount").addText(modules.size() + "");

		// Modules
		for (Module module : modules) {
			Element e = root.addElement("module");
			e.addElement("id").addText(module.getId());
			e.addElement("name").addText(module.getName());
			e.addElement("index").addText(module.getIndex() + "");
			e.addElement("status").addText(module.getStatus() + "");
			e.addElement("configFile").addText(module.getConfigFile() + "");
			e.addElement("createdTime").addText(module.getCreatedTime() + "");
			e.addElement("updatedTime").addText(module.getUpdatedTime() + "");
		}
		return new XMLView(doc);
	}
	
	public AbstractView doSelectList() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		List<Module> modules = this.moduleService.getAllModules();
		// logger.info("Get modules: " + modules);

		// All Select
		Element all = root.addElement("module");
		all.addElement("id").addText("");
		all.addElement("name").addText("所有模块");

		// Modules
		for (Module module : modules) {
			Element e = root.addElement("module");
			e.addElement("id").addText(module.getId());
			e.addElement("name").addText(module.getName());
		}
		return new XMLView(doc);
	}

	public AbstractView doReloadModule() throws MVCException {
		logger.info("Reload modules: " + this.moduleId);
		if (StringUtil.notBlank(this.moduleId)) {
			for (String id : this.moduleId.split(",")) {
				this.moduleService.reloadModule(id);
			}
		}
		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

}
