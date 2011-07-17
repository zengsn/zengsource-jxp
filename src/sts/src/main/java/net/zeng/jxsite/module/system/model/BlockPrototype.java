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
public class BlockPrototype implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String STATIC = "static";
	public static final String DYNAMIC = "dynamic";
	/** for multiple pages */
	public static final String PAGE = "page";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private String type;
	private String template;
	private String pageUrl;
	private String html = "Block: " + id;

	private boolean singleton;

	private Module module;
	private Set<BlockSetting> settings;

	private Set<BlockInstance> instances;

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public BlockPrototype() {
		type = STATIC;
		settings = new HashSet<BlockSetting>();
		instances = new HashSet<BlockInstance>();
	}

	public BlockPrototype(String blockId) {
		this();
		this.id = blockId;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	/** Create block from page. */
	public void buildPageBlock(Page page) {
		setType(PAGE); // !!!
		setId(page.getUrl());
		setName(page.getUrl());
		setPageUrl(page.getUrl());
		setModule(page.getModule());		
	}
	
	public boolean isPage() {
		return PAGE.equals(type);
	}

	public void addSetting(BlockSetting setting) {
		this.settings.add(setting);
	}

	public Set<Page> getPages() {
		Set<Page> pages = new HashSet<Page>();
		for (BlockInstance ins : this.instances) {
			pages.addAll(ins.getPages());
		}
		return pages;
	}

	public BlockInstance newInstance(Page page) {
		BlockInstance ins = new BlockInstance(this, page);
		this.instances.add(ins);
		return ins;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BlockPrototype)) {
			return false;
		}
		if (this.id == null) {
			return false;
		}
		return this.id.equals(((BlockPrototype) obj).getId());
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Set<BlockSetting> getSettings() {
		return settings;
	}

	public void setSettings(Set<BlockSetting> settings) {
		this.settings = settings;
	}

	public Set<BlockInstance> getInstances() {
		return instances;
	}

	public void setInstances(Set<BlockInstance> instances) {
		this.instances = instances;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
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
