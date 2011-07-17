/**
 * 
 */
package net.zeng.jxsite.module.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author snzeng
 * 
 */
public class Menu implements Serializable {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	public static final String INNER = "inner";

	public static final String LEAF = "leaf";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String url;
	private String name;
	private String type;
	private String icon;
	private Integer index;
	private Menu parent;
	private Module module;
	private Set<Menu> children;

	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Menu() {
		this.index = 0;
		this.children = new HashSet<Menu>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void addItem(Menu item) {
		this.children.add(item);
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children == null || children.size() == 0;
	}

	public List<Menu> getLeaves() {
		List<Menu> leaves = new ArrayList<Menu>();
		Menu[] sorted = getSortedChildren();
		for (Menu item : sorted) {
			if (item == null) {
				continue;
			}
			if (item.isLeaf()) {
				leaves.add(item);
			} else {
				leaves.addAll(item.getLeaves());
			}
		}
		return leaves;
	}

	public Set<Menu> diffChildren(Menu menu) {
		if (menu == null) {
			return getChildren();
		} else if (menu.getParent() != null && !this.id.equals(menu.id)) {
			return getChildren();
		}
		Set<Menu> set1 = new HashSet<Menu>();
		for (Menu item1 : getChildren()) {
			int i = 0;
			for (Menu item2 : menu.getChildren()) {
				i++;
				if (item1.id.equals(item2.id)) {
					if (item1.isLeaf() && item2.isLeaf()) {
						break;
					} else if (!item1.isLeaf() && !item2.isLeaf()) {
						Set<Menu> list2 = item1.diffChildren(item2);
						if (list2.size() > 0) {
							set1.addAll(list2);
							set1.add(item1);
							break;
						}
					}
				}
			}
			if (i >= menu.getChildren().size()) {
				set1.add(item1);
			}
		}
		return set1;
	}

	public Menu[] getSortedChildren() {
		Menu[] children = this.children.toArray(new Menu[0]);
		Arrays.sort(children, new Comparator<Menu>() {
			public int compare(Menu m1, Menu m2) {
				return m1.getIndex() - m2.getIndex();
			}
		});
		return children;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
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
