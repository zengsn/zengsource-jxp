/**
 * 
 */
package net.zeng.jxsite.module.product.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zeng.xiaoning
 * 
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int NEW = 0;

	private String id; // DB
	private String name; // 
	private String manager; // Product Manager

	private Integer status = NEW;
	private Integer images = 0;

	private Float price = 0.00f; // 2 decimal
	private String currency = "￥"; // Default is RMB
	private String material;
	private String shipping;
	private String usage;

	private Catalog catalog; // Nested catalog

	private Integer viewCount = 0;
	private Integer soldCount = 0; // Sold counting number
	private Integer totalCount = 0; // Total counting number
	private Integer orderCount = 0; // Current ordering number

	private String imageSingle;
	private String imageFront;
	private String imageBack;
	private String imageLeft;
	private String imageRight;
	private String imageAbove;
	private String imageUnder;
	private String attachment;

	private String description; // Some comments

	private Date createdTime; //
	private Date updatedTime; //

	public Product() {
	}

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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Integer getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(Integer soldCount) {
		this.soldCount = soldCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getImages() {
		return images;
	}

	public void setImages(Integer images) {
		this.images = images;
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

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getImageSingle() {
		return imageSingle;
	}

	public void setImageSingle(String imageSingle) {
		this.imageSingle = imageSingle;
	}

	public String getImageFront() {
		return imageFront;
	}

	public void setImageFront(String imageFront) {
		this.imageFront = imageFront;
	}

	public String getImageBack() {
		return imageBack;
	}

	public void setImageBack(String imageBack) {
		this.imageBack = imageBack;
	}

	public String getImageLeft() {
		return imageLeft;
	}

	public void setImageLeft(String imageLeft) {
		this.imageLeft = imageLeft;
	}

	public String getImageRight() {
		return imageRight;
	}

	public void setImageRight(String imageRight) {
		this.imageRight = imageRight;
	}

	public String getImageAbove() {
		return imageAbove;
	}

	public void setImageAbove(String imageAbove) {
		this.imageAbove = imageAbove;
	}

	public String getImageUnder() {
		return imageUnder;
	}

	public void setImageUnder(String imageUnder) {
		this.imageUnder = imageUnder;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
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
