/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao;

import java.util.List;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.model.GalleryCatalog;

/**
 * @author snzeng
 *
 */
public interface GalleryCatalogDao {

	public void save(GalleryCatalog catalog) throws DesignLabException;

	public List<?> queryByParent(GalleryCatalog parent) throws DesignLabException;

	public GalleryCatalog queryInParent(String name, GalleryCatalog parent) throws DesignLabException;

	public GalleryCatalog queryById(String id) throws DesignLabException;
	
	public GalleryCatalog queryByDirectory(String dirName, GalleryCatalog parent) throws DesignLabException;

}
