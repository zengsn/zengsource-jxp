/**
 * 
 */
package net.zeng.jxsite.module.designlab.web.admin;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.zeng.jxsite.module.designlab.DesignLabConstants;
import net.zeng.jxsite.module.designlab.model.GalleryImage;
import net.zeng.jxsite.module.designlab.service.GalleryImageService;
import net.zeng.jxsite.module.system.SystemConstants;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipleAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;

/**
 * @author snzeng
 * @since 6.0
 */
public class GalleryImageAction extends MultipleAction {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String catalog;

	private ConfigurationService configurationService;
	private GalleryImageService galleryImageService;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImageAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractView doList() throws MVCException {
		int totalCount = this.galleryImageService.findCount(getQ(), this.catalog);
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		if (totalCount <= 0) {
			root.addElement("totalCount").addText("0");
		} else {
			root.addElement("totalCount").addText(totalCount + "");
			int start = getStartInt();
			int limit = getLimitInt();
			List<?> images = this.galleryImageService.find(getQ(), this.catalog, start, limit);
			for (Object obj : images) {
				GalleryImage image = (GalleryImage) obj;
				Element e = root.addElement("image");
				buildImageElement(e, image);
			}
		}
		return new XMLView(doc);
	}

	public AbstractView doLoad() throws MVCException {
		GalleryImage image = this.galleryImageService.getById(getId());
		if (image == null) {
			return new XMLErrorView("name", "No such image!");
		}
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("response").addAttribute("success", "true");
		Element e = root.addElement("Image");
		buildImageElement(e, image);
		return new XMLView(doc);
	}

	private void buildImageElement(Element e, GalleryImage image) {
		e.addElement("id").addText(image.getId());
		e.addElement("name").addText(image.getName());
		e.addElement("status").addText(image.getStatus() + "");
		e.addElement("creator").addText(image.getCreator() + "");
		e.addElement("src").addText(
				image.getCatalog().getHierarchyPath() + image.getFilename() + "");
		if (image.getCatalog() != null) {
			e.addElement("catalog").addText(image.getCatalog().getName());
			// e.addElement("catalog").addText(image.getCatalog().getId());
		} else {
			// Exception
		}
		e.addElement("colorable").addText(image.getColorable() + "");
		e.addElement("resizable").addText(image.getResizable() + "");
		e.addElement("description").addText(image.getDescription() + "");
		e.addElement("createdTime").addText(image.getCreatedTime() + "");
	}

	public AbstractView doRefreshDisk() throws MVCException {
		String uploadFolder = this.configurationService.getSiteUpload()
				+ SystemConstants.IMAGES_DIR + DesignLabConstants.GALLERY_DIR;
		if (this.galleryImageService.loadImageFromDisk(uploadFolder, null)) {
			return XMLView.SUCCESS;
		}
		return null;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
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

}
