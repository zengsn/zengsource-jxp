/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import net.zeng.jxsite.module.designlab.DesignLabException;
import net.zeng.jxsite.module.designlab.model.Design;

/**
 * @author snzeng
 *
 */
public interface PrintService {
	
	public void build(Design print) throws DesignLabException;

}
