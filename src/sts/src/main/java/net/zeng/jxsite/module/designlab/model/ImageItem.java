/**
 * 
 */
package net.zeng.jxsite.module.designlab.model;

import java.util.Date;

/**
 * @author snzeng
 * 
 */
public class ImageItem {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private String id;
	private String name;

	private Design design;	
	private GalleryImage image;

	// Customizations ...

	private int z;
	private float x;
	private float y;
	private int width;
	private int height;
	private int rotation;
	private boolean isFront;

	// more ...
	
	protected String description;
	protected Date createdTime;
	protected Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ImageItem() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public String toString() {
		return "{id:'" + id + "',x:" + x + ",y:" + y + ",z:" + z + ",w:" + width + ",h:" + height + ",src:'" + image.getFilePath() + "'}";
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryImage getImage() {
		return image;
	}

	public void setImage(GalleryImage image) {
		this.image = image;
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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	//Add for bug of Hibernate
	public boolean getIsFront() {
		return isFront;
	}

	public boolean isFront() {
		return isFront;
	}

	//Add for bug of Hibernate
	public void setIsFront(boolean isFront) {
		this.isFront = isFront;
	}

	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}

	public Design getDesign() {
		return design;
	}

	public void setDesign(Design design) {
		this.design = design;
	}

}
