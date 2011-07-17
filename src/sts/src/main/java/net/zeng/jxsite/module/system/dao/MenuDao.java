/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import net.zeng.jxsite.module.system.model.Menu;

/**
 * @author snzeng
 *
 */
public interface MenuDao {

	public void save(Menu menu);

	public Menu queryById(String id);

	public void delete(Menu menu);
	
}
