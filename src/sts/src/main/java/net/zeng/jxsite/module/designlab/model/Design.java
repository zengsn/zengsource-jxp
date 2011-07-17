/**
 * 
 */
package net.zeng.jxsite.module.designlab.model;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;

import net.zeng.jxsite.module.product.model.Product;

/**
 * @author snzeng
 * @since 6.0
 */
public class Design {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private String email;

	private String designer;

	/** Filename of print image. */
	private String printFilename;
	/** Filename of preview image. */
	private String previewFilename;

	private Product style;

	private Set<ImageItem> imageItems;

	// private Set<TextItem> frontTextItems;
	// private Set<TextItem> backTextItems;

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Design() {
		this.imageItems = new HashSet<ImageItem>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void addImage(ImageItem imageItem) {
		this.imageItems.add(imageItem);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getPrintFilename() {
		return printFilename;
	}

	public void setPrintFilename(String printFilename) {
		this.printFilename = printFilename;
	}

	public String getPreviewFilename() {
		return previewFilename;
	}

	public void setPreviewFilename(String previewFilename) {
		this.previewFilename = previewFilename;
	}

	public Product getStyle() {
		return style;
	}

	public void setStyle(Product style) {
		this.style = style;
	}

	public Set<ImageItem> getImageItems() {
		return imageItems;
	}

	public void setImageItems(Set<ImageItem> imageItems) {
		this.imageItems = imageItems;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}
