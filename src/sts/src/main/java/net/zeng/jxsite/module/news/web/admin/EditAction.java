/**
 * 
 */
package net.zeng.jxsite.module.news.web.admin;

import net.zeng.jxsite.module.news.model.Category;
import net.zeng.jxsite.module.news.model.Entry;
import net.zeng.jxsite.module.news.service.CategoryService;
import net.zeng.jxsite.module.news.service.EntryService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipartAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class EditAction extends MultipartAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String title;
	private String category;
	private String content;
	private String writer;

	private EntryService entryService;
	private CategoryService categoryService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public EditAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
		Entry entry = this.entryService.getById(getId());
		if (entry == null) {
			entry = new Entry();
		}
		if (StringUtil.isBlank(entry.getId())) {
			entry.setId(IDUtil.generate());
		}
		// Title
		if (StringUtil.notBlank(getTitle())) {
			entry.setTitle(getTitle());
		} else {
			return new XMLErrorView("name", "Name cannot be null!");
		}
		// Category
		Category category = this.categoryService.getById(getCategory());
		if (category == null) {
			entry.setCategory(this.categoryService.getDefault());
		} else {
			entry.setCategory(category);
		}
		// Writer
		if (StringUtil.isBlank(getWriter())) {
			entry.setWriter("SeaTurtleTech.com");
		} else {
			entry.setWriter(getWriter());
		}
		// Content
		entry.setContent(getContent());
		
		// File
		
		// Save
		this.entryService.save(entry);		
		
		return XMLView.SUCCESS;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public EntryService getEntryService() {
		return entryService;
	}

	public void setEntryService(EntryService entryService) {
		this.entryService = entryService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
