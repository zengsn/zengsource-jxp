/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.dao.GalleryCatalogDao;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class GalleryCatalogServiceImpl implements GalleryCatalogService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private GalleryCatalogDao galleryCatalogDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryCatalogServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void save(GalleryCatalog catalog) throws DesignLabException {
		if (catalog != null) {
			this.galleryCatalogDao.save(catalog);
		}
	}

	public GalleryCatalog getById(String id) throws DesignLabException {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return this.galleryCatalogDao.queryById(id);
	}

	public GalleryCatalog getUserCatalog() throws DesignLabException {
		GalleryCatalog userCatalog = getById(GalleryCatalog.USER);
		if (userCatalog == null) { // First time creation
			userCatalog = new GalleryCatalog();
			userCatalog.setId(GalleryCatalog.USER);
			userCatalog.setName("用户上传");
			save(userCatalog);
		}
		return userCatalog;
	}

	public List<?> listRoot() throws DesignLabException {
		List<?> rootCatalogs = this.galleryCatalogDao.queryByParent(null);
		if (rootCatalogs == null || rootCatalogs.size() == 0) {
			GalleryCatalog userCatalog = getUserCatalog(); // run first
			List<GalleryCatalog> onlyUserCatalog = new ArrayList<GalleryCatalog>();
			onlyUserCatalog.add(userCatalog);
			return onlyUserCatalog;
		}
		return rootCatalogs;
	}

	public GalleryCatalog find(String name, String parent) throws DesignLabException {
		if (StringUtil.isBlank(name)) {
			return null;
		}
		if (StringUtil.notBlank(parent)) {
			GalleryCatalog pCatalog = getById(parent);
			return this.galleryCatalogDao.queryInParent(name, pCatalog);
		} else {
			return this.galleryCatalogDao.queryInParent(name, null);
		}
	}

	public boolean move(GalleryCatalog catalog, GalleryCatalog newParent,
			String galleryUploadDirectory) throws DesignLabException {
		if (catalog.getParent().getId().equals(newParent.getId())) {
			return true;
		}
		String oldPath = galleryUploadDirectory + catalog.getHierarchyPath().toString();
		File oldDir = new File(oldPath);
		// Change Parent
		GalleryCatalog oldParent = catalog.getParent();
		catalog.setParent(newParent); // Set new parent
		String newPath = galleryUploadDirectory + catalog.getHierarchyPath().toString();
		File newDir = new File(newPath);
		if (oldDir.renameTo(newDir)) {
			return true;
		} else {
			catalog.setParent(oldParent); // Reset old parent
			return false;
		}
	}

	public boolean rename(GalleryCatalog catalog, String newDirName, String galleryUploadDirectory)
			throws DesignLabException {
		if (catalog.getDirName().equals(newDirName)) {
			return true;
		}
		String oldPath = galleryUploadDirectory + catalog.getHierarchyPath().toString();
		File oldDir = new File(oldPath);
		String oldDirName = catalog.getDirName();
		catalog.setDirName(newDirName);
		String newPath = galleryUploadDirectory + catalog.getHierarchyPath().toString();
		File newDir = new File(newPath);
		if (oldDir.renameTo(newDir)) {
			return true;
		} else {
			catalog.setDirName(oldDirName); // Reset old directory name
			return false;
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryCatalogDao getGalleryCatalogDao() {
		return galleryCatalogDao;
	}

	public void setGalleryCatalogDao(GalleryCatalogDao galleryCatalogDao) {
		this.galleryCatalogDao = galleryCatalogDao;
	}

}
