/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.zeng.jxsite.module.system.model.Theme;
import net.zeng.util.IDUtil;

/**
 * @author snzeng
 * 
 */
public class ThemeServiceImpl implements ThemeService {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ThemeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public List<?> getAll(String themeRootDir) throws ThemeException {
		File themesFolder = new File(themeRootDir);
		List<Theme> themes = new ArrayList<Theme>();
		for(File themeFolder : themesFolder.listFiles()) {
			Theme theme = new Theme();
			theme.setId(IDUtil.generate());
			theme.setName(themeFolder.getName());
			themes.add(theme);
		}
		return themes;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
