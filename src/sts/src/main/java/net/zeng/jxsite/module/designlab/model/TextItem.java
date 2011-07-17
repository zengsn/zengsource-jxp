/**
 * 
 */
package net.zeng.jxsite.module.designlab.model;

import java.util.Date;

/**
 * @author snzeng
 * @since 6.0
 */
public class TextItem {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;

	private Design design;

	// Customizations ...

	private int z;
	private float x;
	private float y;
	private boolean isFront;

	private FontMeta font;
	private String textColor;
	private int textSize;
	private int textStyle;

	private String shape;
	private String outline;
	private String rotation;

	private String image;

	// more ...

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public TextItem() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

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

	public Design getDesign() {
		return design;
	}

	public void setDesign(Design design) {
		this.design = design;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
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

	public boolean isFront() {
		return isFront;
	}

	public boolean getIsFront() {
		return isFront();
	}

	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}

	public void setIsFront(boolean isFront) {
		setFront(isFront);
	}

	public FontMeta getFont() {
		return font;
	}

	public void setFont(FontMeta font) {
		this.font = font;
	}

	public String getText() {
		return getName();
	}

	public void setText(String text) {
		this.name = text;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public int getTextStyle() {
		return textStyle;
	}

	public void setTextStyle(int textStyle) {
		this.textStyle = textStyle;
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

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getRotation() {
		return rotation;
	}

	public void setRotation(String rotation) {
		this.rotation = rotation;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
