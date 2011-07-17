/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.ModuleNoFoundException;
import net.zeng.jxsite.module.system.model.Module;

/**
 * @author snzeng
 * 
 */
public interface ModuleService {
	
	// Path

	// Application //////////////////////////////////////////////////

	public Module getModule(String id) throws ModuleNoFoundException;

	// Management //////////////////////////////////////////////////
	
	public void saveModule(Module module) throws ModuleNoFoundException;
	
	public Module reloadModule(String moduleId) throws ModuleNoFoundException;
	
	public void reloadConfigurations(Module module) throws ModuleNoFoundException;

	/** */
	public List<Module> getAllModules() throws ModuleNoFoundException;

	public void activateModule(String moduleId) throws ModuleNoFoundException;

	public void inactivateModule(String moduleId) throws ModuleNoFoundException;

}
