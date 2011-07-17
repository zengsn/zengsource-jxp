/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao;

import java.util.List;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.model.GalleryImage;

/**
 * @author snzeng
 * @since 6.0
 */
public interface GalleryImageDao {

	public GalleryImage queryById(String id) throws DesignLabException;

	public void save(GalleryImage galleryImage) throws DesignLabException;

	public int queryCount(String q, GalleryCatalog catalog) throws DesignLabException;

	public List<?> query(String q, GalleryCatalog catalog, int start, int limit) throws DesignLabException;

	public GalleryImage queryByFilename(String filename, GalleryCatalog catalog) throws DesignLabException;

}
