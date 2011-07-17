/**
 * 
 */
package net.zeng.jxsite.module;

import java.util.List;
import java.util.Properties;

import net.zeng.jxsite.module.system.model.Module;

/**
 * @author snzeng
 * 
 */
public interface ModuleService {
	
	// Path
	
	public String getSiteRoot() throws ModuleException;
	
	public String getUploadRoot() throws ModuleException;
	
	public String getImageUploadRoot() throws ModuleException;
	
	public Properties getDefaults()throws ModuleException; 

	// Application //////////////////////////////////////////////////

	public Module getModule(String id) throws ModuleNoFoundException;

	// Management //////////////////////////////////////////////////
	
	public void saveModule(Module module) throws ModuleNoFoundException;
	
	public Module reloadModule(String moduleId) throws ModuleNoFoundException;
	
	@Deprecated
	public void reloadConfigurations(Module module) throws ModuleNoFoundException;

	/** */
	public List<Module> getAllModules() throws ModuleNoFoundException;

	public void activateModule(String moduleId) throws ModuleNoFoundException;

	public void inactivateModule(String moduleId) throws ModuleNoFoundException;

}
