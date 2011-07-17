/**
 * 
 */
package net.zeng.jxsite.module.designlab.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Gallery Image Info.
 * 
 * @author snzeng
 * @since 6.0
 */
public class GalleryImage implements Serializable {

	private static final long serialVersionUID = 1L;

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public static final int OPEN = 0;
	public static final int HIDDEN = -1;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private Integer status = OPEN;
	private String filename;

	/** If this image can be colored. Default is false. */
	private Boolean colorable = false;
	/** If this image can be resized. Default is true. */
	private Boolean resizable = true;

	private GalleryCatalog catalog;
	private String creator;

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImage() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public String getFilePath() {
		return this.catalog.getHierarchyPath().append(this.filename).toString();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Boolean getColorable() {
		return colorable;
	}

	public void setColorable(Boolean colorable) {
		this.colorable = colorable;
	}

	public Boolean getResizable() {
		return resizable;
	}

	public void setResizable(Boolean resizable) {
		this.resizable = resizable;
	}

	public GalleryCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(GalleryCatalog catalog) {
		this.catalog = catalog;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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
