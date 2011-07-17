/**
 * 
 */
package net.zeng.jxsite.module.product.web.admin;

import java.io.File;

import net.zeng.jxsite.module.product.ProductConstants;
import net.zeng.jxsite.module.product.model.Catalog;
import net.zeng.jxsite.module.product.model.Product;
import net.zeng.jxsite.module.product.service.CatalogService;
import net.zeng.jxsite.module.product.service.ProductService;
import net.zeng.jxsite.module.system.service.ConfigurationService;
import net.zeng.mvc.MVCException;
import net.zeng.mvc.action.MultipartAction;
import net.zeng.mvc.view.AbstractView;
import net.zeng.mvc.view.XMLErrorView;
import net.zeng.mvc.view.XMLView;
import net.zeng.util.FileUtil;
import net.zeng.util.IDUtil;
import net.zeng.util.NumberUtil;
import net.zeng.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class EditAction extends MultipartAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ProductService productService;
	private CatalogService catalogService;
	private ConfigurationService configurationService;

	private String catalog;
	private String price;
	private String currency;
	private String material;
	private String shipping;
	private String usage;
	private String totalCount;
	private String description;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public EditAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MVCException {
		String id = getId();
		Product product = null;
		if (StringUtil.notBlank(id)) {
			product = getProductService().getById(id);
		}
		if (product == null) {
			product = new Product();
		}
		if (StringUtil.isBlank(product.getId())) {
			product.setId(IDUtil.generate());
		}
		// Name
		if (StringUtil.notBlank(getName())) {
			product.setName(getName());
		} else {
			return new XMLErrorView("name", "Name cannot be null!");
		}
		// Catalog
		String catalogId = getCatalog();
		Catalog catalog = getCatalogService().getById(catalogId);
		if (catalog == null) {
			// return new XMLErrorView("catalog", "Catalog cannot be null!");
			product.setCatalog(getCatalogService().getDefault());
		} else {
			product.setCatalog(catalog);
		}
		// Total Count
		int totalCount = NumberUtil.string2Integer(getTotalCount(), 0);
		product.setTotalCount(totalCount);
		// Price
		float price = NumberUtil.string2Float(getPrice(), 0.0f);
		product.setPrice(price);
		// Currency
		if (StringUtil.notBlank(getCurrency())) {
			product.setCurrency(getCurrency());
		} else {
			product.setCurrency("￥");
		}
		// Material
		product.setMaterial(getMaterial());
		// Shipping
		product.setShipping(getShipping());
		// Usage
		product.setUsage(getUsage());
		// Description
		product.setDescription(getDescription());

		String uploadFolder = this.configurationService.getSiteUpload();
		String imageFolder = uploadFolder + ProductConstants.UPLOAD_IMAGE;
		FileUtil.createDirectory(imageFolder);
		String attachFolder = uploadFolder + ProductConstants.UPLOAD_ATTACH;
		FileUtil.createDirectory(attachFolder);

		// imageSingle
		String filename = imageFolder + "/" + product.getId();
		File diskFile = saveImage("imageSingle", filename);
		if (diskFile != null) {
			product.setImageSingle(diskFile.getName());
		}

		// imageFront
		filename = imageFolder + "/" + product.getId() + "-front";
		diskFile = saveImage("imageFront", filename);
		if (diskFile != null) {
			product.setImageFront(diskFile.getName());
		}

		// imageBack
		filename = imageFolder + "/" + product.getId() + "-back";
		diskFile = saveImage("imageBack", filename);
		if (diskFile != null) {
			product.setImageBack(diskFile.getName());
		}

		// imageLeft
		filename = imageFolder + "/" + product.getId() + "-left";
		diskFile = saveImage("imageLeft", filename);
		if (diskFile != null) {
			product.setImageLeft(diskFile.getName());
		}

		// imageRight
		filename = imageFolder + "/" + product.getId() + "-right";
		diskFile = saveImage("imageRight", filename);
		if (diskFile != null) {
			product.setImageRight(diskFile.getName());
		}

		// imageAbove
		filename = imageFolder + "/" + product.getId() + "-above";
		diskFile = saveImage("imageAbove", filename);
		if (diskFile != null) {
			product.setImageAbove(diskFile.getName());
		}

		// imageUnder
		filename = imageFolder + "/" + product.getId() + "-under";
		diskFile = saveImage("imageUnder", filename);
		if (diskFile != null) {
			product.setImageUnder(diskFile.getName());
		}

		// attachment
		filename = attachFolder + "/" + product.getId() + "-attach";
		diskFile = saveImage("attachment", filename);
		if (diskFile != null) {
			product.setAttachment(diskFile.getName());
		}

		// Save
		getProductService().save(product);

		// Reture
		return XMLView.SUCCESS;
	}

	private File saveImage(String imageField, String filename) {
		// Write file
		return saveFile(imageField, filename);
		// Check file
		// TODO
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getCatalog() {
		return catalog;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
