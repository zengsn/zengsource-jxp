/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.BlockSetting;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.BlockInstanceService;
import net.zeng.jxsite.module.system.service.BlockPrototypeService;
import net.zeng.jxsite.module.system.service.ModuleService;
import net.zeng.jxsite.module.system.service.PageService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class BlockAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String page;
	private String instance;
	private String block;
	private String module;
	private String type;

	private String index;
	private String colspan;
	private String rowspan;
	private String cls;
	private String style;
	private String html;
	private String description;

	private String number;

	private ModuleService moduleService;
	private BlockPrototypeService blockPrototypeService;
	private BlockInstanceService blockInstanceService;
	private PageService pageService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		if ("prototype".equals(this.type)) {
			return listBlockPrototypes();
		} else {
			return listBlockInstances();
		}
	}

	private AbstractView listBlockPrototypes() throws MVCException {
		// Condition
		Integer start = getStartInt();
		Integer limit = getLimitInt();

		// Module Selecting
		String moduleId = getModule();
		Module module = null;
		if (!StringUtil.isBlank(moduleId)) {
			module = this.moduleService.getModule(moduleId);
		}

		// Total Count
		Integer totalCount = getBlockPrototypeService().searchCount(module);

		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		if (totalCount <= 0) {
			root.addElement("totalCount").addText("0");
		} else {
			root.addElement("totalCount").addText(totalCount + "");
			List<?> prototypes = getBlockPrototypeService().search(module, start, limit);
			for (Object obj : prototypes) {
				BlockPrototype prototype = (BlockPrototype) obj;
				Element ele = root.addElement("block");
				ele.addElement("id").addText(prototype.getId() + "");
				ele.addElement("name").addText(prototype.getName() + "");
				if (prototype.getModule() != null) {
					ele.addElement("module").addText(prototype.getModule().getName() + "");
				} else {
					ele.addElement("module").addText("Unknown");
				}

				StringBuilder pagesSB = new StringBuilder();
				for (Page page : prototype.getPages()) {
					pagesSB.append(page.getName() + ", ");
				}
				if (pagesSB.lastIndexOf(", ") > -1) {
					pagesSB.deleteCharAt(pagesSB.length() - 2);
				} else {
					pagesSB.append("无");
				}

				ele.addElement("pages").addText(pagesSB.toString());
				ele.addElement("singleton").addText(prototype.isSingleton() + "");
				ele.addElement("instances").addText(prototype.getInstances().size() + "");
				ele.addElement("index").addText("-");
				ele.addElement("colspan").addText("-");
				ele.addElement("rowspan").addText("-");
				ele.addElement("description").addText(prototype.getDescription() + "");
			}
		}
		return new XMLView(doc);
	}

	private AbstractView listBlockInstances() throws MVCException {
		// Condition
		Integer start = getStartInt();
		Integer limit = getLimitInt();
		String q = getQ();
		String page = getPage();

		// Total Count
		Integer totalCount = getBlockInstanceService().searchCount(page, q);

		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		if (totalCount <= 0) {
			root.addElement("totalCount").addText("0");
		} else {
			root.addElement("totalCount").addText(totalCount + "");
			List<?> prototypes = getBlockInstanceService().search(page, q, start, limit);
			for (Object obj : prototypes) {
				BlockInstance instance = (BlockInstance) obj;
				Element ele = root.addElement("block");
				ele.addElement("id").addText(instance.getId() + "");
				ele.addElement("name").addText(instance.getName() + "");
				ele.addElement("module").addText(
						instance.getPrototype().getModule().getName() + "");
				ele.addElement("pages").addText("page1, page2, page3");
				ele.addElement("index").addText(instance.getIndex() + "");
				ele.addElement("colspan").addText(instance.getColspan() + "");
				ele.addElement("rowspan").addText(instance.getRowspan() + "");
				ele.addElement("description").addText(instance.getDescription() + "");
			}
		}
		return new XMLView(doc);
	}

	/**
	 * Get the instances of specific prototype.
	 * 
	 * @return XML with all instances of the prototype
	 * @throws MVCException
	 */
	public AbstractView doGetInstances() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		// Search
		String prototypeId = getId();
		if (StringUtil.notBlank(prototypeId)) {
			BlockPrototype prototype = getBlockPrototypeService().getById(prototypeId);
			if (prototype != null) {
				for (BlockInstance instance : prototype.getInstances()) {
					Element ele = root.addElement("instance");
					this.instanceIntoElement(ele, instance);
				}
			}
		}
		return new XMLView(doc);
	}

	/**
	 * If prototype has one or more instance, load the first instance; or load
	 * the prototype itself for creating new instance. Input:
	 * type(prototype|instance), id
	 * 
	 * @return XML with instance.
	 * @throws MVCException
	 */
	public AbstractView doLoad() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		Element ele = root.addElement("instance");
		if ("prototype".equals(getType())) {
			String prototypeId = getId();
			if (StringUtil.notBlank(prototypeId)) {
				BlockPrototype prototype = getBlockPrototypeService().getById(prototypeId);
				if (prototype != null) {
					BlockInstance instance = null;
					for (BlockInstance ins : prototype.getInstances()) {
						if (ins != null) {
							instance = ins;
							break;
						}
					}
					if (instance == null) { // Create one default instance
						instance = prototype.newInstance(null);
						getBlockInstanceService().save(instance);
					}
					this.instanceIntoElement(ele, instance);
				}
			}
		} else if ("instance".equals(getType())) {
			String instanceId = getId();
			BlockInstance instance = getBlockInstanceService().getById(instanceId);
			if (instance != null) {
				this.instanceIntoElement(ele, instance);
			}
		} else {
			// Nothing
		}
		return new XMLView(doc);
	}

	private void instanceIntoElement(Element ele, BlockInstance instance) {
		ele.addElement("id").addText(instance.getId() + "");
		ele.addElement("name").addText(instance.getName() + "");
		ele.addElement("module").addText(instance.getPrototype().getModule().getName() + "");
		ele.addElement("index").addText(instance.getIndex() + "");
		ele.addElement("cls").addText(instance.getCls() + "");
		ele.addElement("style").addText(instance.getStyle() + "");
		ele.addElement("colspan").addText(instance.getColspan() + "");
		ele.addElement("rowspan").addText(instance.getRowspan() + "");
		ele.addElement("template").addText(instance.getPrototype().getTemplate() + "");
		ele.addElement("html").addText(instance.getHtml() + "");
		ele.addElement("description").addText(instance.getDescription() + "");
	}

	/**
	 * Get page of specific instance.
	 * 
	 * @return XML with all pages that instance displays on.
	 * @throws MVCException
	 */
	public AbstractView doGetPages() throws MVCException {
		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		// Search
		String instanceId = getId();
		if (StringUtil.notBlank(instanceId)) {
			BlockInstance instance = getBlockInstanceService().getById(instanceId);
			if (instance != null) { // Found
				Set<Page> pages = instance.getPages();
				for (Page page : pages) {
					addPageElement(root, page);
				}
			}
		} else { //
		}
		return new XMLView(doc);
	}

	@Deprecated
	public AbstractView doListPages() throws MVCException {
		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		// Search
		String prototypeId = getId();
		if (StringUtil.notBlank(prototypeId)) {
			BlockPrototype prototype = getBlockPrototypeService().getById(prototypeId);
			if (prototype != null) { // Found
				Set<Page> pages = prototype.getPages();
				for (Page page : pages) {
					addPageElement(root, page);
				}
			}
		} else { //
		}
		return new XMLView(doc);
	}

	private void addPageElement(Element root, Page page) {
		Element ele = root.addElement("page");
		ele.addElement("id").addText(page.getId() + "");
		ele.addElement("name").addText(page.getName() + "");
		ele.addElement("current").addText(page.getCurrentBlocks() + "");
	}

	/**
	 * Get all settings of specific instance.
	 * 
	 * @return XML with all settings.
	 * @throws MVCException
	 */
	public AbstractView doGetSettings() throws MVCException {
		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		// Search
		String instanceId = getId();
		if (StringUtil.notBlank(instanceId)) {
			BlockInstance instance = getBlockInstanceService().getById(instanceId);
			if (instance != null) {
				Set<BlockSetting> settings = instance.getSettings();
				for (BlockSetting setting : settings) {
					Element ele = root.addElement("setting");
					ele.addElement("id").addText(setting.getId() + "");
					ele.addElement("name").addText(setting.getName() + "");
					ele.addElement("key").addText(setting.getKey() + "");
					ele.addElement("value").addText(setting.getValue() + "");
					ele.addElement("usage").addText(setting.getUsage() + "");
				}
			}
		}
		return new XMLView(doc);
	}

	@Deprecated
	public AbstractView doListSettings() throws MVCException {
		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		// Search
		String blockId = getId();
		if (StringUtil.notBlank(blockId)) {
			Set<BlockSetting> settings = null;
			if (StringUtil.isBlank(getType()) || "prototype".equals(getType())) {
				BlockPrototype prototype = getBlockPrototypeService().getById(blockId);
				if (prototype != null) { // It's a BlockPrototype.
					settings = prototype.getSettings();
					logger.info("Get settings of block prototype: " + prototype.getId());
				}
			} else if ("instance".equals(getType())) {
				BlockInstance instance = getBlockInstanceService().getById(blockId);
				if (instance != null) { // It's a BlockInstance
					settings = instance.getSettings();
					logger.info("Get settings of block instance: " + instance.getId());
				}
			}
			if (settings != null) {
				for (BlockSetting setting : settings) {
					Element ele = root.addElement("setting");
					ele.addElement("id").addText(setting.getId() + "");
					ele.addElement("name").addText(setting.getName() + "");
					ele.addElement("key").addText(setting.getKey() + "");
					ele.addElement("value").addText(setting.getValue() + "");
					ele.addElement("usage").addText(setting.getUsage() + "");
				}
			}
		} else { //
			logger.error("Null block ID!");
		}
		return new XMLView(doc);
	}

	public AbstractView doDisplay() throws MVCException {
		String instanceId = getInstance();
		String pageId = getPage();
		Page page = getPageService().getById(pageId);
		if (page != null) {
			BlockInstance instance = getBlockInstanceService().getById(instanceId);
			if (instance != null) {
				instance.addPage(page);
				getBlockInstanceService().save(instance);
				return XMLView.SUCCESS;
			}
			return XMLView.SUCCESS; // TODO
		}
		return XMLView.SUCCESS; // TODO
	}

	public AbstractView doAddPages() throws MVCException {
		String blockId = getBlock();
		String pageIds = getPage();

		BlockPrototype prototype = getBlockPrototypeService().getById(blockId);
		if (prototype != null) {
			// Current displaying pages
			List<?> instances = getBlockInstanceService().search(prototype);
			if (StringUtil.notBlank(pageIds)) {
				// Save new-added pages
				String[] pageIdArr = pageIds.split(",");
				for (String pageId : pageIdArr) {
					Page page = getPageService().getById(pageId);
					if (page != null) {
						for (BlockInstance ins : page.getBlockInstances()) {
							if (ins != null && ins.getPrototype() != null
									&& prototype.getId().equals(ins.getPrototype().getId())) {
								return XMLView.SUCCESS;
							}
						}
						BlockInstance instance = new BlockInstance(prototype, page);
						logger.info("Save new block instance: " + instance.getName());
						getBlockInstanceService().save(instance);
					}
				}
				// Delete unused pages
				for (Object obj : instances) {
					BlockInstance ins = (BlockInstance) obj;
					if (ins != null) {
						// String pid = ins.getPage().getId();
						// if (Arrays.binarySearch(pageIdArr, pid) < 0) {
						// logger.info("Delete block instance: " + ins.getId());
						// getBlockInstanceService().delete(ins);
						// }
					}
				}
			} else { // Delete unused pages
				for (Object obj : instances) {
					BlockInstance ins = (BlockInstance) obj;
					if (ins != null) {
						logger.info("Delete block instance: " + ins.getId());
						getBlockInstanceService().delete(ins);
					}
				}
			}
		}
		return XMLView.SUCCESS;
	}

	public AbstractView doAddInstance() throws MVCException {
		String prototypeId = getBlock();
		String pageId = getPage();
		int instances = NumberUtil.string2Integer(getNumber(), 0);
		Page page = getPageService().getById(pageId);
		BlockPrototype prototype = getBlockPrototypeService().getById(prototypeId);
		if (page != null && prototype != null) {
			int currentInstances = 0;
			for (BlockInstance ins : page.getBlockInstances()) {
				if (ins != null && ins.getPrototype() != null
						&& prototypeId.equals(ins.getPrototype().getId())) {
					currentInstances++;
				}
			}
			// Cannot add more than one instance to one page for singleton
			// prototype
			if (currentInstances >= 1 && prototype.isSingleton()) {
				return XMLView.SUCCESS;
			}
			for (int i = 0; i < instances - currentInstances; i++) {
				BlockInstance instance = new BlockInstance(prototype, page);
				instance.setName(prototype.getName() + "-" + (currentInstances + i + 1));
				logger.info("Save new block instance: " + instance.getName());
				getBlockInstanceService().save(instance);
			}
		}
		return XMLView.SUCCESS;
	}

	public AbstractView doSave() throws MVCException {
		String blockId = getId();
		BlockInstance instance = getBlockInstanceService().getById(blockId);
		if (instance != null) {
			// Name
			if (StringUtil.isBlank(getName())) {
				return new XMLErrorView("name", "模板名不能为空!");
			} else {
				instance.setName(getName());
			}
			// Index
			int index = NumberUtil.string2Integer(getIndex(), 1);
			instance.setIndex(index);
			// Colspan
			int colspan = NumberUtil.string2Integer(getColspan(), 1);
			instance.setColspan(colspan);
			// Rowspan
			int rowspan = NumberUtil.string2Integer(getRowspan(), 1);
			instance.setRowspan(rowspan);
			// Cls
			instance.setCls(getCls());
			// Style
			instance.setStyle(getStyle());
			// Description
			instance.setDescription(getDescription());
			// HTML
			instance.setHtml(getHtml());
			// Save
			getBlockInstanceService().save(instance);
		}
		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getHtml() {
		return html;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public BlockPrototypeService getBlockPrototypeService() {
		return blockPrototypeService;
	}

	public void setBlockPrototypeService(BlockPrototypeService blockPrototypeService) {
		this.blockPrototypeService = blockPrototypeService;
	}

	public BlockInstanceService getBlockInstanceService() {
		return blockInstanceService;
	}

	public void setBlockInstanceService(BlockInstanceService blockInstanceService) {
		this.blockInstanceService = blockInstanceService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

}
