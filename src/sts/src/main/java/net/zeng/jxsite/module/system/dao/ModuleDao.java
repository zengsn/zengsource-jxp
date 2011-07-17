/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import net.zeng.jxsite.module.system.model.Module;

/**
 * @author snzeng
 *
 */
public interface ModuleDao {

	public void insert(Module module);
	
	public void save(Module module);
	
	public Module queryById(String id);

}
