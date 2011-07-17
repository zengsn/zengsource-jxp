/**
 * 
 */
package net.zeng.jxsite.module.system.web.admin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.BlockInstanceService;
import net.zeng.jxsite.module.system.service.BlockPrototypeService;
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
public class PageAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private PageService pageService;
	private BlockInstanceService blockInstanceService;
	private BlockPrototypeService blockPrototypeService;

	private String instance;
	private String prototype;

	private String cls;
	private String style;
	private String columns;
	private String description;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public PageAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		// Search
		String q = getQ();
		Integer start = getStartInt();
		Integer limit = getLimitInt();

		// Search Count
		Integer totalCount = getPageService().searchCount(q);

		// XML
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		if (totalCount == 0) {
			root.addElement("totalCount").addText("0");
		} else {
			root.addElement("totalCount").addText(totalCount + "");
			List<?> pages = getPageService().search(q, start, limit);
			for (Object obj : pages) {
				Page page = (Page) obj;
				Element ele = root.addElement("page");
				ele.addElement("id").addText(page.getId() + "");
				ele.addElement("name").addText(page.getName() + "");
				ele.addElement("module").addText(page.getModule().getName() + "");
				ele.addElement("mindex").addText(page.getModule().getIndex() + "");
				ele.addElement("url").addText(page.getUrl() + "");
				if (StringUtil.notBlank(this.prototype)) {
					int instanceNum = 0;
					for (BlockInstance ins : page.getBlockInstances()) {
						if (ins != null && ins.getPrototype() != null
								&& this.prototype.equals(ins.getPrototype().getId())) {
							instanceNum++;
						}
					}
					ele.addElement("instances").addText(instanceNum + "");
				}
				ele.addElement("current").addText(page.getCurrentBlocks() + "");
				ele.addElement("description").addText(page.getDescription() + "");
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doLoad() throws MVCException {
		String pageId = getId();
		Page page = getPageService().getById(pageId);
		if (page == null) {
			return new XMLErrorView("name", "Page doesn't exist!");
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("Page");
		ele.addElement("id").addText(page.getId() + "");
		ele.addElement("name").addText(page.getName() + "");
		ele.addElement("columns").addText(page.getColumns() + "");
		ele.addElement("cls").addText(page.getCls() == null ? "" : page.getCls());
		ele.addElement("style").addText(page.getStyle() == null ? "" : page.getStyle());
		ele.addElement("description").addText(
				page.getDescription() == null ? "" : page.getDescription());
		return new XMLView(doc);
	}

	public AbstractView doConfigInstance() throws MVCException {
		// Params
		String pageId = getId();
		String instanceId = getInstance();
		String field = getField();
		String value = getValue();
		// Assign block of page
		if (StringUtil.notBlank(pageId)) {
			Page page = getPageService().getById(pageId);
			if (page != null) {
				for (BlockInstance instance : page.getBlockInstances()) {
					if (instance != null && instance.getId().equals(instanceId)) {
						if ("index".equals(field)) {
							int oldIndex = instance.getIndex();
							int newIndex = NumberUtil.string2Integer(value, 1);
							logger.info("Adjust index [" + oldIndex + "] to [" + newIndex + "]");
							instance.setIndex(newIndex);
							// block
						} else if ("colspan".equals(field)) {
							int colspan = NumberUtil.string2Integer(value, 1);
							instance.setColspan(colspan);
							logger.info("Update colspan to " + colspan);
						} else if ("rowspan".equals(field)) {
							int rowspan = NumberUtil.string2Integer(value, 1);
							instance.setRowspan(rowspan);
							logger.info("Update rowspan to " + rowspan);
						}
						// Save
						logger.info("Save block instance: " + instance.getId());
						getBlockInstanceService().save(instance);
						break;
					}
				}
			} else {
				logger.warn("Page not found!");
			}
		}
		return XMLView.SUCCESS;
	}

	public AbstractView doAddInstance() throws MVCException {
		// Params
		String pageId = getId();
		String field = getField();
		String value = getValue();
		// Add instance into page
		if ("prototype".equals(field)) {
			Page page = getPageService().getById(pageId);
			if (page != null) {
				BlockPrototype prototype = getBlockPrototypeService().getById(value);
				if (prototype != null) {
					BlockInstance instance = prototype.newInstance(page);
					getBlockInstanceService().save(instance);
				}
			}
		} else {
			throw new MVCException("Impossible!");
		}
		return XMLView.SUCCESS;
	}

	/**
	 * Remove block instance from page.
	 * 
	 * @return XML
	 * @throws MVCException
	 */
	public AbstractView doRemoveInstance() throws MVCException {
		// Params
		String pageId = getId();
		String instanceIds = getInstance();
		// Search
		Page page = getPageService().getById(pageId);
		if (page != null && instanceIds != null) {
			String[] idArr = instanceIds.split(",");
			for (String id : idArr) {
				BlockInstance removed = page.removeBlockInstance(id);
				getPageService().save(page);
				if (removed != null && removed.getPages().size() == 0) {
					getBlockInstanceService().delete(removed);
				}
			}
		} else {
			//
		}
		return XMLView.SUCCESS;

	}

	public AbstractView doGetInstances() throws MVCException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		String pageId = getId();
		Page page = getPageService().getById(pageId);
		if (page != null && page.getBlockInstances() != null) {
			BlockInstance[] instances = new BlockInstance[page.getBlockInstances().size()];
			page.getBlockInstances().toArray(instances);
			Arrays.sort(instances, new Comparator<BlockInstance>() {
				public int compare(BlockInstance o1, BlockInstance o2) {
					if (o1 == null || o2 == null) {
						return 0;
					}
					return o1.getIndex() - o2.getIndex();
				}
			});
			for (BlockInstance instance : instances) {
				if (instance != null && instance.getPrototype() != null) {
					Element ele = root.addElement("instance");
					this.instanceIntoElement(ele, instance);
				}
			}
		}
		return new XMLView(doc);
	}

	private void instanceIntoElement(Element ele, BlockInstance instance) {
		if (instance != null && instance.getPrototype() != null) {
			ele.addElement("id").addText(instance.getId() + "");
			ele.addElement("name").addText(instance.getName() + "");
			//ele.addElement("module").addText(instance.getPrototype().getModule
			// ().
			// getName() + "");
			ele.addElement("prototype").addText(instance.getPrototype().getName() + "");
			ele.addElement("index").addText(instance.getIndex() + "");
			ele.addElement("style").addText(instance.getStyle() + "");
			ele.addElement("colspan").addText(instance.getColspan() + "");
			ele.addElement("rowspan").addText(instance.getRowspan() + "");
			ele.addElement("template").addText(instance.getPrototype().getTemplate() + "");
			ele.addElement("html").addText(instance.getHtml() + "");
			ele.addElement("description").addText(instance.getDescription() + "");
		}
	}

	public AbstractView doSave() throws MVCException {
		String pageId = getId();
		Page page = getPageService().getById(pageId);
		if (page != null) {
			if (StringUtil.notBlank(getName())) {
				page.setName(getName());
			} else {
				return new XMLErrorView("name", "Name cannot be empty!");
			}
			page.setColumns(NumberUtil.string2Integer(getColumns(), 1));
			page.setCls(getCls());
			page.setStyle(getStyle());
			page.setDescription(getDescription());
			getPageService().save(page);
		} else {
			// TODO
		}
		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //


	public String getPrototype() {
		return prototype;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public BlockInstanceService getBlockInstanceService() {
		return blockInstanceService;
	}

	public void setBlockInstanceService(BlockInstanceService blockInstanceService) {
		this.blockInstanceService = blockInstanceService;
	}

	public BlockPrototypeService getBlockPrototypeService() {
		return blockPrototypeService;
	}

	public void setBlockPrototypeService(BlockPrototypeService blockPrototypeService) {
		this.blockPrototypeService = blockPrototypeService;
	}

	public void setPrototype(String prototype) {
		this.prototype = prototype;
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

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
