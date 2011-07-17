/**
 * 
 */
package net.zeng.jxsite.module.designlab.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author snzeng
 * @since 6.0
 */
public class GalleryCatalog implements Serializable {

	private static final long serialVersionUID = 1L;

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public static final String USER = "user-catalog";
	public static final String NAME_PREFIX = "~~";
	public static final String NAME_SUFFIX = "~~";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private Integer index = 0;
	private String dirName;
	private String description;

	private GalleryCatalog parent;
	private Set<GalleryCatalog> children;

	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GalleryCatalog() {
		children = new HashSet<GalleryCatalog>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public String toString() {
		return getName();
	}

	/** Get the stored directory in file system. */
	public StringBuilder getHierarchyPath() {
		StringBuilder path = new StringBuilder();
		GalleryCatalog catalog = this;
		while (catalog != null) {
			// if (StringUtil.isBlank(this.dirName)) {
			// path.insert(0, catalog.getId() + "/");
			// } else {
			path.insert(0, catalog.getDirName() + "/");
			// }
			catalog = catalog.getParent();
		}
		return path;
	}

	public String getNameFile() {
		return NAME_PREFIX + this.name + NAME_SUFFIX;
	}

	public String parseNameFile(String nameFile) {
		this.name = nameFile;
		this.name = this.name.replaceAll(NAME_PREFIX, "");
		this.name = this.name.replaceAll(NAME_SUFFIX, "");
		return this.name;
	}
	
	public GalleryCatalog[] getSortedChildren() {
		GalleryCatalog[] sorted = this.children.toArray(new GalleryCatalog[0]);
		Arrays.sort(sorted, new Comparator<GalleryCatalog>() {
			public int compare(GalleryCatalog o1, GalleryCatalog o2) {
				return o1.getIndex() - o2.getIndex();
			}
		});
		return sorted;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getDirName() {
		return (dirName == null || dirName.length() == 0) ? id : dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GalleryCatalog getParent() {
		return parent;
	}

	public void setParent(GalleryCatalog parent) {
		this.parent = parent;
	}

	public Set<GalleryCatalog> getChildren() {
		return children;
	}

	public void setChildren(Set<GalleryCatalog> children) {
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
