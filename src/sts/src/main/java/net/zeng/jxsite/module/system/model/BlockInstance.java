/**
 * 
 */
package net.zeng.jxsite.module.system.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author snzeng
 * 
 */
public class BlockInstance implements Serializable, Comparable<BlockInstance> {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private String cls;
	private String style;
	private Integer index = 1;
	private Integer colspan = 1;
	private Integer rowspan = 1;
	private String html;
	private Set<Page> pages;
	private BlockPrototype prototype;
	private Set<BlockSetting> settings;

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockInstance() {
		settings = new HashSet<BlockSetting>();
		pages = new HashSet<Page>();
	}

	public BlockInstance(BlockPrototype prototype, Page page) {
		this();
		this.name = prototype.getName();
		// this.settings = prototype.getSettings();
		this.prototype = prototype;
		//this.addPage(page);
		if (page != null) {
			this.name = prototype.getName() + " (" + page.getName() + ")";
			page.addBlockInstance(this);
		}
		for (BlockSetting setting : prototype.getSettings()) {
			BlockSetting newSetting = new BlockSetting(setting);
			newSetting.setInstance(this);
			this.addSetting(newSetting);
		}
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void addSetting(BlockSetting setting) {
		// for (BlockSetting bs : this.settings) {
		// if (bs != null && bs.getKey().equals(setting.getKey()) {
		// return; // Same setting exists.
		// }
		// }
		this.settings.add(setting);
	}

	public void addPage(Page page) {
		this.pages.add(page);
	}

	public void removePage(Page page) {
		this.pages.remove(page);
	}

	public int compareTo(BlockInstance other) {
		return this.id.compareTo(other.id);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

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

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public Set<BlockSetting> getSettings() {
		return settings;
	}

	public void setSettings(Set<BlockSetting> settings) {
		this.settings = settings;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public Integer getRowspan() {
		return rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public BlockPrototype getPrototype() {
		return prototype;
	}

	public void setPrototype(BlockPrototype prototype) {
		this.prototype = prototype;
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
