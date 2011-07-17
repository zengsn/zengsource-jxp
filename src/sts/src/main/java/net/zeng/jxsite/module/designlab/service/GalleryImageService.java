/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import java.util.List;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;
import net.zeng.jxsite.module.designlab.model.GalleryImage;

/**
 * @author snzeng
 * @since 6.0
 */
public interface GalleryImageService {

	public GalleryImage getById(String id) throws DesignLabException;

	public void save(GalleryImage galleryImage) throws DesignLabException;

	public int findCount(String q, String catalogId) throws DesignLabException;

	public List<?> find(String q, String catalogId, int start, int limit) throws DesignLabException;
	
	public boolean loadImageFromDisk(String directory, GalleryCatalog parent) throws DesignLabException;

}
