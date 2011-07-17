/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

/**
 * @author snzeng
 *
 */
public interface ThemeService {

	public List<?> getAll(String themeRootDir) throws ThemeException;

}
