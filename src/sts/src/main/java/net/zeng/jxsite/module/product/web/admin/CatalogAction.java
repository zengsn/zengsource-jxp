/**
 * 
 */
package net.zeng.jxsite.module.product.web.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.product.model.Catalog;
import net.zeng.jxsite.module.product.service.CatalogService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.HTMLView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class CatalogAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private CatalogService catalogService;

	// private String id;
	// private String name;
	private String index;
	private String parent;
	private String description;

	// List type: xml or json
	private String type;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CatalogAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		List<?> rootCatalogs = getCatalogService().listRoot();
		if ("xml".equals(getType())) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("response").addAttribute("success", "true");
			if (rootCatalogs == null) {
				root.addElement("totalCount").addText(0 + "");
			} else {
				root.addElement("totalCount").addText(rootCatalogs.size() + "");
			}
			for (Object obj : rootCatalogs) {
				Catalog catalog = (Catalog) obj;
				if (catalog != null) {
					addElement(root, catalog);
				}
			}
			return new XMLView(doc);
		} else { // JSON
			if (rootCatalogs == null) {
				return new HTMLView("[]");
			}
			StringBuffer json = new StringBuffer();
			json.append("[");
			Collections.sort(rootCatalogs, new Comparator<Object>() {
				public int compare(Object o1, Object o2) {
					if (o1 == null && o2 == null) {
						return 0;
					}
					if (o1 == null) {
						return -1;
					}
					if (o2 == null) {
						return 1;
					}
					Catalog c1 = (Catalog) o1;
					Catalog c2 = (Catalog) o2;
					return c1.getIndex() - c2.getIndex();
				}
			});
			for (Object obj : rootCatalogs) {
				Catalog catalog = (Catalog) obj;
				if (catalog != null) {
					addNode(json, catalog, "");
				}
				json.append(",");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]\n");

			return new HTMLView(json.toString());
		}
	}

	private void addElement(Element root, Catalog catalog) throws MVCException {
		Element ele = root.addElement("Catalog");
		ele.addElement("id").addText(catalog.getId() + "");
		ele.addElement("name").addText(catalog.getName() + "");
		ele.addElement("index").addText(catalog.getIndex() + "");
		if (catalog.getParent() == null) {
			ele.addElement("parent").addText("");
		} else {
			ele.addElement("parent").addText(catalog.getParent().getId() + "");
		}
		ele.addElement("description").addText(catalog.getDescription() + "");
		List<Catalog> children = catalog.getChildren();
		if (children != null && children.size() > 0) {
			for (Catalog child : children) {
				if (child != null) { // Why get one null???
					addElement(root, child);
				}
			}
		}
	}

	private void addNode(StringBuffer json, Catalog catalog, String indent) {
		indent += "  ";
		json.append("{\n");
		json.append(indent + "id:'" + catalog.getId() + "',\n");
		// json.append(indent + "catname:'" + catalog.getName() + "',\n");
		json.append(indent + "text:'" + catalog.getName() + "',\n");
		json.append(indent + "index:" + catalog.getIndex() + ",\n");
		json.append(indent + "parent:'"
				+ (catalog.getParent() == null ? "" : catalog.getParent().getId()) + "',\n");
		json.append(indent + "parentName:'"
				+ (catalog.getParent() == null ? "顶级目录" : catalog.getParent().getName()) + "',\n");
		json.append(indent + "description:'" + catalog.getDescription() + "',\n");
		// View Config
		// json.append(indent + "uiProvider:'col',\n");
		// json.append(indent + "cls:'master-task',\n");
		List<Catalog> children = catalog.getChildren();
		if (children == null || children.size() == 0) {
			// json.append(indent + "iconCls:'task',\n");
			json.append(indent + "leaf:true\n");
		} else {
			// json.append(indent + "iconCls:'task-folder',\n");
			json.append(indent + "children:[");
			for (Catalog child : children) {
				if (child != null) {
					addNode(json, child, indent);
					json.append(",");
				}
			}
			json.deleteCharAt(json.length() - 1);
			json.append(indent + "]\n");
		}
		json.append(indent.substring(2) + "}");
	}

	public AbstractView doLoad() throws MVCException {
		Catalog catalog = getCatalogService().getById(getId());
		if (catalog == null) {
			XMLErrorView errorView = new XMLErrorView();
			errorView.put("name", "Catalog not existed!");
			return errorView;
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("Catalog");
		ele.addElement("id").addText(catalog.getId() + "");
		ele.addElement("name").addText(catalog.getName() + "");
		ele.addElement("index").addText(catalog.getIndex() + "");
		if (catalog.getParent() == null) {
			ele.addElement("parent").addText("");
		} else {
			ele.addElement("parent").addText(catalog.getParent().getName() + "");
		}
		ele.addElement("description").addText(catalog.getDescription() + "");
		return new XMLView(doc);
	}

	public AbstractView doEdit() throws MVCException {
		Catalog catalog = null;
		// Check :: Should not be 2 catalogs with same Name and Parent.
		if (StringUtil.isBlank(getId())) { // New
			catalog = getCatalogService().find(getName(), getParent());
			if (catalog == null) {
				catalog = new Catalog();
			} else { // Same Catalog existed already.
				XMLErrorView errorView = new XMLErrorView();
				errorView.put("name", "Same Catalog existed!");
				errorView.put("parent", "Same Catalog existed!");
				return errorView;
			}
		} else {
			catalog = getCatalogService().getById(getId());
			if (catalog == null) { // Catalog was not existed.
				catalog = new Catalog();
			}
		}
		// Check Parent
		if (catalog.getParent() != null && catalog.getParent().getName().equals(getParent())) {
			// Same parent, ignore it
		} else { // Check Parent
			Catalog parent = getCatalogService().getById(getParent());
			catalog.setParent(parent);
		}
		// Properties
		catalog.setName(getName());
		catalog.setIndex(NumberUtil.string2Integer(getIndex(), 0));
		catalog.setDescription(getDescription());
		// Save
		getCatalogService().save(catalog);

		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

}
