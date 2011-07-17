/**
 * 
 */
package net.zeng.jxsite.module.news.web;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.news.model.Entry;
import net.zeng.jxsite.module.news.service.EntryService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.GenericAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.DateUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class ListAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private EntryService entryService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public ListAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public AbstractView doService() throws MVCException {
		List<?> latestEntries = this.entryService.getLatest(5);
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response");
		root.addElement("totalCount").addText("5");
		for(Object obj : latestEntries) {
			Entry entry = (Entry) obj;
			Element ele = root.addElement("Entry");
			ele.addElement("id").addText(entry.getId() + "");
			ele.addElement("title").addText(entry.getTitle() + "[" + entry.getViewCount() + "]");
			ele.addElement("viewCount").addText(entry.getViewCount() + "");
			ele.addElement("date").addText(DateUtil.format(entry.getCreatedTime(), "MM月dd日") + "");
		}
		return new XMLView(doc);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public EntryService getEntryService() {
		return entryService;
	}

	public void setEntryService(EntryService entryService) {
		this.entryService = entryService;
	}
}
