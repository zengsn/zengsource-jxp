/**
 * 
 */
package net.zeng.jxsite.module;

import java.io.Serializable;


/**
 * @author snzeng
 *
 */
public interface ModuleInitializer extends Serializable {
	
	public void init() throws InitializationException;
	
}
