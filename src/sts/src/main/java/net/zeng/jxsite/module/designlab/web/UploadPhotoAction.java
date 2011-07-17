/**
 * 
 */
package net.zeng.jxsite.module.designlab.web;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import net.zeng.jxsite.module.designlab.DesignLabConstants;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.model.GalleryImage;
import net.zeng.jxsite.module.designlab.service.GalleryCatalogService;
import net.zeng.jxsite.module.designlab.service.GalleryImageService;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipartAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.JSONResultView;
import net.zeng.util.IDUtil;
import net.zeng.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public class UploadPhotoAction extends MultipartAction {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ConfigurationService configurationService;
	private GalleryImageService galleryImageService;
	private GalleryCatalogService galleryCatalogService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UploadPhotoAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
		GalleryImage image = new GalleryImage();
		image.setId(IDUtil.generate());
		// Name
		if (StringUtil.notBlank(getName())) {
			image.setName(getName());
			// } else { // Use filename
			// return new XMLErrorView("name", "Name cannot be null!");
		}
		// Status
		image.setStatus(GalleryImage.OPEN);
		// Catalog
		GalleryCatalog catalog = this.galleryCatalogService.getUserCatalog();
		image.setCatalog(catalog);
		// File
		FileItem sourceFile = getFileItem("photo");
		String uploadFolder = this.configurationService.getSiteUpload() + "/images"
				+ DesignLabConstants.GALLERY_DIR;
		if (sourceFile != null) {
			String storedDir = uploadFolder + "/" + image.getCatalog().getHierarchyPath();
			// Save to disk
			File diskFile = saveFile("photo", storedDir + image.getId());
			if (diskFile != null) {
				image.setFilename(diskFile.getName());
			}
			// use filename as image name if needed
			if (StringUtil.isBlank(image.getName())) {
				image.setName(sourceFile.getName());
			}
			image.setDescription(sourceFile.getName());
		}

		// Save to DB
		this.galleryImageService.save(image);

		// return XMLView.SUCCESS;
		String path = image.getCatalog().getHierarchyPath() + image.getId();
		return new JSONResultView("src", path);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImageService getGalleryImageService() {
		return galleryImageService;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
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
