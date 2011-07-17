/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.io.Serializable;



/**
 * @author snzeng
 *
 */
public interface ModuleInitializer extends Serializable {
	
	public void init() throws InitializationException;
	
}
