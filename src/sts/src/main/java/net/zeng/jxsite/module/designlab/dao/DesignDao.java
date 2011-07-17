/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao;

import net.zeng.jxsite.module.designlab.model.Design;

/**
 * @author snzeng
 *
 */
public interface DesignDao {
	
	public void save(Design design);

	public Design queryById(String id);

}
