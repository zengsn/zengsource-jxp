/**
 * 
 */
package net.zeng.jxsite.module.designlab.web.admin;

import java.io.File;
import java.io.IOException;

import org.apache.commons.fileupload.FileItem;

import net.zeng.jxsite.module.designlab.DesignLabConstants;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.model.GalleryImage;
import net.zeng.jxsite.module.designlab.service.GalleryCatalogService;
import net.zeng.jxsite.module.designlab.service.GalleryImageService;
import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipartAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.FileUtil;
import net.zeng.util.IDUtil;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class GalleryImageUploadAction extends MultipartAction {

	private static final long serialVersionUID = 1L;

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String status;
	private String catalog;
	private String description;

	private ConfigurationService configurationService;
	private GalleryImageService galleryImageService;
	private GalleryCatalogService galleryCatalogService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImageUploadAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
		// ID
		String id = getId();
		GalleryImage image = null;
		if (StringUtil.notBlank(id)) {
			image = this.galleryImageService.getById(id);
		}
		if (image == null) {
			image = new GalleryImage();
		}
		if (StringUtil.isBlank(image.getId())) {
			image.setId(IDUtil.generate());
		}
		// Name
		if (StringUtil.notBlank(getName())) {
			image.setName(getName());
			// } else { // Use filename
			// return new XMLErrorView("name", "Name cannot be null!");
		}
		// Status
		int status = NumberUtil.string2Integer(this.status, GalleryImage.OPEN);
		image.setStatus(status);
		// Catalog
		String catalogId = this.catalog;
		GalleryCatalog catalog = this.galleryCatalogService.getById(catalogId);
		if (catalog == null) {
			catalog = this.galleryCatalogService.getUserCatalog();
		}
		GalleryCatalog oldCatalog = image.getCatalog();
		image.setCatalog(catalog);
		// File
		FileItem sourceFile = getFileItem("filename");
		String uploadFolder = this.configurationService.getSiteUpload()
				+ SystemConstants.IMAGES_DIR + DesignLabConstants.GALLERY_DIR;
		if (sourceFile != null) {
			String storedDir = uploadFolder + image.getCatalog().getHierarchyPath();
			prepareCatalogFolder(image.getCatalog()); // prepare folder
			// Save to disk
			// File diskFile = saveFile("filename", storedDir + image.getId());
			File diskFile = saveFile("filename", storedDir + sourceFile.getName());
			if (diskFile != null) {
				image.setFilename(diskFile.getName());
			}
			// use filename as image name if needed
			if (StringUtil.isBlank(image.getName())) {
				image.setName(sourceFile.getName());
			}
		} else if (oldCatalog != null && oldCatalog.getId() != null
				&& !oldCatalog.getId().equals(catalog.getId())) {
			// Catalog changed, need to move file
			String oldDir = oldCatalog.getHierarchyPath().toString();
			String newDir = image.getCatalog().getHierarchyPath().toString();
			prepareCatalogFolder(image.getCatalog()); // prepare folder
			File oldFile = new File(uploadFolder + oldDir + image.getFilename());
			if (oldFile.exists()) {
				File newFile = new File(uploadFolder + newDir + image.getFilename());
				if (!oldFile.renameTo(newFile)) {
					throw new MVCException("Cannot move file from [" + oldFile.getAbsolutePath()
							+ "] to [" + newFile.getAbsolutePath() + "] !!!");
				}
			}
		}
		// Description
		if (StringUtil.notBlank(this.description)) {
			image.setDescription(this.description);
		} else if (sourceFile != null) { // Use source filename
			image.setDescription(sourceFile.getName());
		} else {
			// Nothing
		}

		// Save to DB
		this.galleryImageService.save(image);

		return XMLView.SUCCESS;
	}

	private void prepareCatalogFolder(GalleryCatalog catalog) {
		String uploadFolder = this.configurationService.getSiteUpload()
				+ SystemConstants.IMAGES_DIR + DesignLabConstants.GALLERY_DIR;
		String catalogFolder = uploadFolder + catalog.getHierarchyPath();
		FileUtil.createDirectory(catalogFolder);
		File catalogMarker = new File(catalogFolder + catalog.getNameFile());
		if (!catalogMarker.exists() || !catalogMarker.isFile()) {
			try {
				catalogMarker.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
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

	public GalleryImageService getGalleryImageService() {
		return galleryImageService;
	}

	public void setGalleryImageService(GalleryImageService galleryImageService) {
		this.galleryImageService = galleryImageService;
	}

	public GalleryCatalogService getGalleryCatalogService() {
		return galleryCatalogService;
	}

	public void setGalleryCatalogService(GalleryCatalogService galleryCatalogService) {
		this.galleryCatalogService = galleryCatalogService;
	}

}
