/**
 * 
 */
package net.zeng.jxsite.module.designlab.web.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.designlab.DesignLabConstants;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.service.GalleryCatalogService;
import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.HTMLView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;
import net.zeng.mvc.MVCException;

/**
 * @author snzeng
 * @since 6.0
 */
public class GalleryCatalogAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configurationService;
	private GalleryCatalogService galleryCatalogService;

	private String index;
	private String parent;
	private String dirName;
	private String description;

	// List type: xml or json
	private String type;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryCatalogAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		List<?> rootCatalogs = this.galleryCatalogService.listRoot();
		if ("xml".equals(this.type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("response").addAttribute("success", "true");
			if (rootCatalogs == null) {
				root.addElement("totalCount").addText(0 + "");
			} else {
				root.addElement("totalCount").addText(rootCatalogs.size() + "");
			}
			for (Object obj : rootCatalogs) {
				GalleryCatalog catalog = (GalleryCatalog) obj;
				if (catalog != null) {
					addElement(root, catalog, "");
				}
			}
			return new XMLView(doc);

		} else {
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
					GalleryCatalog c1 = (GalleryCatalog) o1;
					GalleryCatalog c2 = (GalleryCatalog) o2;
					return c1.getIndex() - c2.getIndex();
				}
			});
			for (Object obj : rootCatalogs) {
				GalleryCatalog catalog = (GalleryCatalog) obj;
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

	private void addNode(StringBuffer json, GalleryCatalog catalog, String indent) {
		indent += "  ";
		json.append("{\n");
		json.append(indent + "id:'" + catalog.getId() + "',\n");
		// json.append(indent + "catname:'" + catalog.getName() + "',\n");
		json.append(indent + "text:'" + catalog.getName() + "',\n");
		// Attibutes
		json.append(indent + "index:" + catalog.getIndex() + ",\n");
		json.append(indent + "dirName:'" + catalog.getDirName() + "',\n");
		json.append(indent + "parent:'"
				+ (catalog.getParent() == null ? "顶级目录" : catalog.getParent().getName()) + "',\n");
		json.append(indent + "description:'" + catalog.getDescription() + "',\n");
		// View Config
		// json.append(indent + "uiProvider:'col',\n");
		// json.append(indent + "cls:'master-task',\n");
		Set<GalleryCatalog> children = catalog.getChildren();
		if (children == null || children.size() == 0) {
			// json.append(indent + "iconCls:'task',\n");
			json.append(indent + "leaf:true\n");
		} else {
			// json.append(indent + "iconCls:'task-folder',\n");
			json.append(indent + "children:[");
			GalleryCatalog[] sorted = catalog.getSortedChildren();
			for (GalleryCatalog child : sorted) {
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

	private void addElement(Element root, GalleryCatalog catalog, String indent)
			throws MVCException {
		Element ele = root.addElement("Catalog");
		ele.addElement("id").addText(catalog.getId() + "");
		ele.addElement("name").addText(indent + "" + catalog.getName() + "");
		ele.addElement("index").addText(catalog.getIndex() + "");
		ele.addElement("dirName").addText(catalog.getDirName() + "");
		if (catalog.getParent() == null) {
			ele.addElement("parent").addText("");
		} else {
			ele.addElement("parent").addText(catalog.getParent().getId() + "");
		}
		ele.addElement("description").addText(catalog.getDescription() + "");
		Set<GalleryCatalog> children = catalog.getChildren();
		if (children != null && children.size() > 0) {
			GalleryCatalog[] sorted = catalog.getSortedChildren();
			for (GalleryCatalog child : sorted) {
				if (child != null) { // Why get one null???
					// if (StringUtil.notBlank(indent)) {
					addElement(root, child, indent + "+");
					// } else {
					// addElement(root, child, "--");
					// }
				}
			}
		}
	}

	public AbstractView doLoad() throws MVCException {
		GalleryCatalog catalog = this.galleryCatalogService.getById(getId());
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("Catalog");
		ele.addElement("id").addText(catalog.getId() + "");
		ele.addElement("name").addText(catalog.getName() + "");
		ele.addElement("index").addText(catalog.getIndex() + "");
		// if(StringUtil.isBlank(catalog.getDirName())) {
		// ele.addElement("dirName").addText(catalog.getDirName() + "");
		// } else {
		ele.addElement("dirName").addText(catalog.getDirName() + "");
		// }
		if (catalog.getParent() == null) {
			ele.addElement("parent").addText("");
		} else {
			// ele.addElement("parent").addText(catalog.getParent().getName() +
			// "");
			ele.addElement("parent").addText(catalog.getParent().getId() + "");
		}
		ele.addElement("description").addText(catalog.getDescription() + "");
		return new XMLView(doc);
	}

	public AbstractView doSave() throws MVCException {
		GalleryCatalog catalog = null;
		// Check :: Should not be 2 catalogs with same Name and Parent.
		if (StringUtil.isBlank(getId())) { // New
			catalog = this.galleryCatalogService.find(getName(), this.parent);
			if (catalog == null) {
				catalog = new GalleryCatalog();
			} else { // Same Catalog existed already.
				XMLErrorView errorView = new XMLErrorView();
				errorView.put("name", "Same Catalog existed!");
				errorView.put("parent", "Same Catalog existed!");
				return errorView;
			}
		} else {
			catalog = this.galleryCatalogService.getById(getId());
			if (catalog == null) { // Catalog was not existed.
				catalog = new GalleryCatalog();
			}
		}
		// Find Parent
		GalleryCatalog parent = this.galleryCatalogService.getById(this.parent);
		if (parent == null) {
			parent = this.galleryCatalogService.getUserCatalog();
		}
		// Check that this is a different catalog
		String galleryUploadDirectory = this.configurationService.getSiteUpload()
				+ SystemConstants.IMAGES_DIR + DesignLabConstants.GALLERY_DIR;
		if (catalog.getParent() == null || !catalog.getParent().getId().equals(parent.getId())) {
			if (parent.getId().equals(catalog.getId())) {
				// Not allow set self as parent
				return new XMLErrorView("parent", "Parent cannot be self!!!");
			}
			// catalog.setParent(parent);
			this.galleryCatalogService.move(catalog, parent, galleryUploadDirectory);
		}
		// Directory - Change and move directory
		if (!this.galleryCatalogService.rename(catalog, this.dirName, galleryUploadDirectory)) {
			throw new MVCException("Cannot rename folder [" + catalog.getDirName() + "] to ["
					+ this.dirName + "] !!!");
		}
		// Properties
		catalog.setName(getName());
		catalog.setIndex(NumberUtil.string2Integer(this.index, 0));
		catalog.setDescription(this.description);
		// Save
		this.galleryCatalogService.save(catalog);

		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
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

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public GalleryCatalogService getGalleryCatalogService() {
		return galleryCatalogService;
	}

	public void setGalleryCatalogService(GalleryCatalogService galleryCatalogService) {
		this.galleryCatalogService = galleryCatalogService;
	}

}
