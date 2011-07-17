/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.List;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.dao.GalleryCatalogDao;
import net.zeng.jxsite.module.designlab.dao.GalleryImageDao;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.model.GalleryImage;
import net.zeng.util.DateUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class GalleryImageServiceImpl implements GalleryImageService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private GalleryImageDao galleryImageDao;
	private GalleryCatalogDao galleryCatalogDao;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImageServiceImpl() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImage getById(String id) throws DesignLabException {
		if (StringUtil.isBlank(id)) {
			return null;
		}
		return this.galleryImageDao.queryById(id);
	}

	public void save(GalleryImage galleryImage) throws DesignLabException {
		this.galleryImageDao.save(galleryImage);
	}

	public int findCount(String q, String catalogId) throws DesignLabException {
		GalleryCatalog catalog = this.galleryCatalogDao.queryById(catalogId);
		return this.galleryImageDao.queryCount(q, catalog);
	}

	public List<?> find(String q, String catalogId, int start, int limit)
			throws DesignLabException {
		GalleryCatalog catalog = this.galleryCatalogDao.queryById(catalogId);
		return this.galleryImageDao.query(q, catalog, start, limit);
	}

	public boolean loadImageFromDisk(String directory, GalleryCatalog parent)
			throws DesignLabException {
		File galleryDirectory = new File(directory);
		File[] catalogDirectories = galleryDirectory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		for (File catalogDirectory : catalogDirectories) {
			String dirName = catalogDirectory.getName();
			GalleryCatalog catalog = this.galleryCatalogDao.queryByDirectory(dirName, parent);
			if (catalog == null) { // New
				catalog = new GalleryCatalog();
				catalog.setDirName(dirName);
				catalog.setParent(parent);
				File[] nameFile = catalogDirectory.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.startsWith(GalleryCatalog.NAME_PREFIX)
								&& name.endsWith(GalleryCatalog.NAME_SUFFIX);
					}
				});
				if (nameFile.length > 0) {
					catalog.parseNameFile(nameFile[0].getName());
				} else {
					catalog.setName(dirName);
				}
				// Save it
				this.galleryCatalogDao.save(catalog);
			}
			// Load image
			File[] images = catalogDirectory.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".gif") || name.endsWith(".jpg") || name.endsWith(".png");
				}
			});
			for (File image : images) {
				GalleryImage galleryImage = this.galleryImageDao.queryByFilename(image.getName(),
						catalog);
				if (galleryImage == null) {
					galleryImage = new GalleryImage();
					galleryImage.setName(image.getName());
					galleryImage.setFilename(image.getName());
					galleryImage.setCatalog(catalog);
					galleryImage.setDescription("Loaded from disk at "
							+ DateUtil.format(new Date(), DateUtil.FULL_CN));
					this.galleryImageDao.save(galleryImage); // Save it
				}
			}
			// Load sub-catalog
			loadImageFromDisk(catalogDirectory.getAbsolutePath(), catalog);
		}
		return true;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImageDao getGalleryImageDao() {
		return galleryImageDao;
	}

	public void setGalleryImageDao(GalleryImageDao galleryImageDao) {
		this.galleryImageDao = galleryImageDao;
	}

	public GalleryCatalogDao getGalleryCatalogDao() {
		return galleryCatalogDao;
	}

	public void setGalleryCatalogDao(GalleryCatalogDao galleryCatalogDao) {
		this.galleryCatalogDao = galleryCatalogDao;
	}
}
