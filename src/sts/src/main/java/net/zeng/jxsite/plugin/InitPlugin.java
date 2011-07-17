/**
 * 
 */
package net.zeng.jxsite.plugin;

import org.apache.log4j.Logger;

import net.zeng.mvc.plugin.Plugable;
import net.zeng.mvc.plugin.PluginException;

/**
 * @author snzeng
 *
 */
public class InitPlugin implements Plugable {
	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	Logger logger = Logger.getLogger(getClass());

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public InitPlugin() {
	}


	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public void disable() throws PluginException {
		// Nothing
	}
	
	public void enable() throws PluginException {
		logger.info("Init Site ... ");
	}

	// ~ g^setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
