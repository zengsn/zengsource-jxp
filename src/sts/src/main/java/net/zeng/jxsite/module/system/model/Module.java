/**
 * 
 */
package net.zeng.jxsite.module.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Module is a pluggable component of JXSite.
 * 
 * @author snzeng
 * @since 6.0
 */
public class Module implements Comparable<Module>, Serializable {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;
	/** brand new module, need to be initialized. */
	public static final Integer UNINIT = 0;
	/** module has already been initialized and ready to work. */
	public static final Integer READY = 1;
	/** module is working online. */
	public static final Integer ONLINE = 2;
	/** module is closed by administrator. */
	public static final Integer OFFLINE = 3;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private Integer status;
	private Integer index = 0;
	/** 1:n */
	private Set<Menu> menuSet;

	@Deprecated
	private Map<String, Menu> menuMap;

	// TODO reserved
	private Map<String, String> iconMap;
	private List<Configuration> configurations;

	private Set<BlockPrototype> blockPrototypes;

	private Set<Page> pages;

	private Date createdTime;
	private Date updatedTime;

	private String configFile = "module.xml";

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Module() {
		this.status = UNINIT;
		this.pages = new HashSet<Page>();
		this.menuSet = new HashSet<Menu>();
		this.iconMap = new HashMap<String, String>();
		this.configurations = new ArrayList<Configuration>();
		this.blockPrototypes = new HashSet<BlockPrototype>();
	}

	public Module(Module module) {
		this.id = module.id;
		this.name = module.name;
		this.index = module.index;
		this.status = module.status;
		this.pages = module.pages;
		this.menuSet = new HashSet<Menu>();
		this.createdTime = module.createdTime;
		this.updatedTime = module.updatedTime;
		this.configurations = module.configurations;
		this.blockPrototypes = module.blockPrototypes;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public int compareTo(Module o) {
		return this.id.compareTo(o.id);
	}

	public void copyAll(Module module) {
		this.id = module.id;
		this.name = module.name;
		this.index = module.index;
		this.status = module.status;
		this.menuSet = new HashSet<Menu>();
		this.createdTime = module.createdTime;
		this.updatedTime = module.updatedTime;
		this.configurations = module.configurations;
		this.blockPrototypes = module.blockPrototypes;
	}

	public void copy4DB(Module module) {
		this.name = module.name;
		this.index = module.index;
		this.status = module.status;
	}

	public String getIcon(String iconKey) {
		return this.iconMap.get(iconKey);
	}

	public void addIcon(String key, String path) {
		this.iconMap.put(key, path);
	}

	public void addConfiguration(Configuration config) {
		this.configurations.add(config);
	}

	public void removeMenu(String menuType) {
		this.menuMap.remove(menuType);
	}

	public void addMenu(String menuType, Menu menu) {
		this.menuMap.put(menuType, menu);
	}

	public void addMenu(Menu menu) {
		Menu oldMenu = getMenu(menu.getId());
		if (oldMenu != null) {
			this.menuSet.remove(oldMenu);
		}
		this.menuSet.add(menu);
	}

	public Menu getMenu(String menuId) {
		for (Menu menu : this.menuSet) {
			if (menu.getId().equals(menuId)) {
				return menu;
			}
		}
		return null;
	}
	
	public Menu getAdminMenu() {
		return getMenu(this.id + "-admin");
	}

	public void addBlockPrototype(BlockPrototype prototype) {
		if (prototype == null) {
			return;
		}
		// TODO i don't know why Set needs this ???
		for (BlockPrototype prototype2 : this.blockPrototypes) {
			if (prototype.equals(prototype2)) {
				return; // Don't add
			}
		}
		this.blockPrototypes.add(prototype);
	}

	public void addPage(Page page) {
		this.pages.add(page);
	}

	public boolean isOnline() {
		return ONLINE.equals(this.status);
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

	public Integer getStatus() {
		return status;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Menu> getMenuSet() {
		return menuSet;
	}

	public void setMenuSet(Set<Menu> menuSet) {
		this.menuSet = menuSet;
	}

	public Map<String, Menu> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(Map<String, Menu> menuMap) {
		this.menuMap = menuMap;
	}

	public Map<String, String> getIconMap() {
		return iconMap;
	}

	public void setIconMap(Map<String, String> iconMap) {
		this.iconMap = iconMap;
	}

	public Set<BlockPrototype> getBlockPrototypes() {
		return blockPrototypes;
	}

	public void setBlockPrototypes(Set<BlockPrototype> blockPrototypes) {
		this.blockPrototypes = blockPrototypes;
	}

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
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

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public List<Configuration> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<Configuration> configurations) {
		this.configurations = configurations;
	}

}
