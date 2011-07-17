/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import java.util.List;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;

/**
 * @author snzeng
 * @since 6.0
 */
public interface GalleryCatalogService {
	
	public void save(GalleryCatalog catalog) throws DesignLabException;
	
	public GalleryCatalog getById(String id) throws DesignLabException;
	
	public GalleryCatalog getUserCatalog() throws DesignLabException;

	public List<?> listRoot() throws DesignLabException;

	public GalleryCatalog find(String name, String parent) throws DesignLabException;
	
	public boolean move(GalleryCatalog catalog, GalleryCatalog newParent, String galleryUploadDirectory) throws DesignLabException;
	
	public boolean rename(GalleryCatalog catalog, String newDirName, String galleryUploadDirectory) throws DesignLabException;

}
