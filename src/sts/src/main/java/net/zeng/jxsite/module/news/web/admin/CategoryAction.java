/**
 * 
 */
package net.zeng.jxsite.module.news.web.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.news.model.Category;
import net.zeng.jxsite.module.news.service.CategoryService;
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
 * @since 6.0
 */
public class CategoryAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private String type;
	private String parent;
	private String index;
	
	private CategoryService categoryService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CategoryAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public AbstractView doList() throws MVCException {
		List<?> rootCategories = this.categoryService.listRoot();
		if ("xml".equals(this.type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("response").addAttribute("success", "true");
			if (rootCategories == null) {
				root.addElement("totalCount").addText(0 + "");
			} else {
				root.addElement("totalCount").addText(rootCategories.size() + "");
			}
			for (Object obj : rootCategories) {
				Category category = (Category) obj;
				if (category != null) {
					addElement(root, category);
				}
			}
			return new XMLView(doc);
		} else { // JSON
			if (rootCategories == null) {
				return new HTMLView("[]");
			}
			StringBuffer json = new StringBuffer();
			json.append("[");
			Collections.sort(rootCategories, new Comparator<Object>() {
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
					Category c1 = (Category) o1;
					Category c2 = (Category) o2;					
					return c1.getIndex() - c2.getIndex();
				}
			});
			for (Object obj : rootCategories) {
				Category category = (Category) obj;
				if (category != null) {
					addNode(json, category, "");
				}
				json.append(",");
			}
			json.deleteCharAt(json.length() - 1);
			json.append("]\n");
			return new HTMLView(json.toString());
		}
	}
	
	

	private void addElement(Element root, Category category) {
		Element ele = root.addElement("Category");
		ele.addElement("id").addText(category.getId() + "");
		ele.addElement("name").addText(category.getName() + "");
		ele.addElement("index").addText(category.getIndex() + "");
		if (category.getParent() == null) {
			ele.addElement("parent").addText("");
		} else {
			ele.addElement("parent").addText(category.getParent().getId() + "");
		}
		//ele.addElement("description").addText(category.getDescription() + "");
		List<Category> children = category.getChildren();
		if (children != null && children.size() > 0) {
			for (Category child : children) {
				if (child != null) { // Why get one null???
					addElement(root, child);
				}
			}
		}
	}

	private void addNode(StringBuffer json, Category category, String indent) {
		indent += "  ";
		json.append("{\n");
		json.append(indent + "id:'" + category.getId() + "',\n");
		// json.append(indent + "catname:'" + catalog.getName() + "',\n");
		json.append(indent + "text:'" + category.getName() + "',\n");
		json.append(indent + "index:" + category.getIndex() + ",\n");
		json.append(indent + "parent:'"
				+ (category.getParent() == null ? "" : category.getParent().getId()) + "',\n");
		json.append(indent + "parentName:'"
				+ (category.getParent() == null ? "顶级分类" : category.getParent().getName()) + "',\n");
		//json.append(indent + "description:'" + catalog.getDescription() + "',\n");
		// View Config
		// json.append(indent + "uiProvider:'col',\n");
		// json.append(indent + "cls:'master-task',\n");
		List<Category> children = category.getChildren();
		if (children == null || children.size() == 0) {
			// json.append(indent + "iconCls:'task',\n");
			json.append(indent + "leaf:true\n");
		} else {
			// json.append(indent + "iconCls:'task-folder',\n");
			json.append(indent + "children:[");
			for(Category child : children) {
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
	
	public AbstractView doEdit() throws MVCException {
		Category category = null;
		// Check :: Should not be 2 category with same Name and Parent.
		if (StringUtil.isBlank(getId())) { // New
			category = this.categoryService.find(getName(), getParent());
			if (category == null) {
				category = new Category();
			} else { // Same Catalog existed already.
				XMLErrorView errorView = new XMLErrorView();
				errorView.put("name", "Same Catalog existed!");
				errorView.put("parent", "Same Catalog existed!");
				return errorView;
			}
		} else { // update category
			category = this.categoryService.getById(getId());
			if (category == null) {
				category = new Category();
			}			
		}
		// Check Parent
		if (category.getParent() != null && category.getParent().getName().equals(getParent())) {
			// Same Parent
		} else { // Check Parent
			Category parent = this.categoryService.getById(getParent());
			category.setParent(parent);
		}
		// properties
		category.setName(getName());
		category.setIndex(NumberUtil.string2Integer(getIndex(), 1));
		// Save 
		this.categoryService.save(category);
		return XMLView.SUCCESS;
	}
	
	public AbstractView doLoad() throws MVCException {
		Category category = this.categoryService.getById(getId());
		if (category == null) {
			XMLErrorView errorView = new XMLErrorView();
			errorView.put("name", "Catalog not existed!");
			return errorView;
		}

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("Category");
		ele.addElement("id").addText(category.getId() + "");
		ele.addElement("name").addText(category.getName() + "");
		ele.addElement("index").addText(category.getIndex() + "");
		if (category.getParent() == null) {
			ele.addElement("parent").addText("");
		} else {
			ele.addElement("parent").addText(category.getParent().getName() + "");
		}
		return new XMLView(doc);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
}
