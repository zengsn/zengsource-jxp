/**
 * 
 */
package net.zeng.jxsite.module.news.service;

import java.util.List;

import net.zeng.jxsite.module.news.NewsException;
import net.zeng.jxsite.module.news.dao.CategoryDao;
import net.zeng.jxsite.module.news.model.Category;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class CategoryServiceImpl implements CategoryService {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private CategoryDao categoryDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CategoryServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public List<?> listRoot() throws NewsException {
		return this.categoryDao.queryByParent(null);
	}

	public Category find(String name, String parentId) throws NewsException {
		return this.categoryDao.query(name, parentId);
	}

	public Category getById(String id) throws NewsException {
		if (StringUtil.notBlank(id)) {
			return this.categoryDao.queryById(id);
		}
		return null;
	}
	
	public void save(Category category) throws NewsException {
		if (category != null) {
			this.categoryDao.save(category);
		}
	}
	
	public Category getDefault() throws NewsException {
		Category defaultCategory = getById(Category.DEFAULT);
		if (defaultCategory == null) {
			defaultCategory = new Category();
			defaultCategory.setId(Category.DEFAULT);
			defaultCategory.setName(Category.DEFAULT);
			save(defaultCategory);
		}
		return defaultCategory;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
}
