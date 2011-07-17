/**
 * 
 */
package net.zeng.jxsite.module.news.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.news.model.Category;
import net.zeng.jxsite.module.news.model.Entry;
import net.zeng.jxsite.module.news.service.EntryService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class ManageAction extends MultipleAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String category;

	private EntryService entryService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ManageAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		Integer totalCount = this.entryService.getCount(getQ(), getCategory());
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("reponse").addAttribute("success", "true");
		if (totalCount <= 0) {
			root.addElement("totalCount").addText(0 + "");
		} else {
			root.addElement("totalCount").addText(totalCount + "");
			List<?> newsEntries = this.entryService.search(getQ(), getCategory(), getStartInt(),
					getLimitInt());
			for (Object obj : newsEntries) {
				Entry entry = (Entry) obj;
				Element e = root.addElement("Entry");
				addEntryObjectToXmlElement(e, entry);
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doLoad() throws MVCException {
		if (StringUtil.isBlank(getId())) {
			return new XMLErrorView("name", "No ID found!");
		}
		Entry entry = this.entryService.getById(getId());
		if (entry == null) {
			return new XMLErrorView("name", "No entry found!");
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element ele = root.addElement("Entry");
		addEntryObjectToXmlElement(ele, entry);
		return new XMLView(doc);
	}

	private void addEntryObjectToXmlElement(Element e, Entry entry) {
		e.addElement("id").addText(entry.getId() + "");
		e.addElement("title").addText(entry.getTitle() + "");
		e.addElement("writer").addText(entry.getWriter() + "");
		e.addElement("content").addText(entry.getContent() + "");
		e.addElement("status").addText(entry.getStatus() + "");

		Category category = entry.getCategory();
		if (category == null) {
			e.addElement("category").addText(Category.DEFAULT);
		} else {
			e.addElement("category").addText(category.getName());
		}
		e.addElement("viewCount").addText(entry.getViewCount() + "");
		e.addElement("createdTime").addText(entry.getCreatedTime() + "");
		e.addElement("updatedTime").addText(entry.getUpdatedTime() + "");
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public EntryService getEntryService() {
		return entryService;
	}

	public void setEntryService(EntryService entryService) {
		this.entryService = entryService;
	}
}
