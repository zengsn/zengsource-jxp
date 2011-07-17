/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import net.zeng.jxsite.module.designlab.model.Design;
import net.zeng.jxsite.module.designlab.model.TextItem;

/**
 * @author snzeng
 * @since 6.0
 */
public interface DesignService {
	
	public boolean save(Design design);

	public Design getById(String id);

	public TextItem getTextItem(String id);

	public void createTextImage(String tmpDir, TextItem textItem);

}
