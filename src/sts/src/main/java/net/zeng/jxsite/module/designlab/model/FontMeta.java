/**
 * 
 */
package net.zeng.jxsite.module.designlab.model;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.util.StringUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author snzeng
 * @since 6.0
 */
public class FontMeta {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public static final int OPEN = 1;
	public static final int NEW = 0;
	public static final int CLOSED = -1;
	public static final Integer DEFAULT_SIZE = 16;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private String file;
	private String image;

	private int status = NEW;

	/** Not saved in database. */
	private String directory;

	// more ...

	private String description;
	private Date createdTime;
	private Date updatedTime;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public FontMeta() {
	}

	// ~ 静态方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public static void parseMeta(FontMeta font, File dir) {
		font.setId(dir.getName());
		File[] metaFile = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile() && "meta.xml".equals(pathname.getName());
			}
		});
		if (metaFile.length > 0) {
			SAXReader reader = new SAXReader();
			try { // Use the first one
				Document doc = reader.read(metaFile[0]);
				Element root = doc.getRootElement();
				String id = root.elementText("id");
				if (!font.id.equals(id)) {
					throw new DesignLabException("ID should be same as directory name!!!");
				}
				String name = root.elementText("name");
				if (StringUtil.notBlank(name)) {
					font.setName(name);
				} else {
					font.setName(id);
				}
				font.setFile(root.elementText("file"));
				font.setImage(root.elementText("image"));
				font.setStatus(NEW);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public File getTTFFile() {
		if (this.directory != null) {
			String path = this.directory + "/" + this.file;
			return new File(path);
		}
		return null;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
