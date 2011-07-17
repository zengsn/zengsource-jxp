/**
 * 
 */
package net.zeng.jxsite.module.designlab.service;

import java.io.File;
import java.util.List;

import net.zeng.jxsite.module.designlab.model.FontMeta;

/**
 * @author snzeng
 * @since 6.0
 */
public interface FontMetaService {

	public List<?> getAll();

	public FontMeta getById(String id);

	public void save(FontMeta font);

	public void scanFontDirectory(File fontDir);

	/**
	 * Create snapshot for font.
	 * @param font font type 
	 * @param imageSize the snapshot image size, [width,height]
	 */
	public void createSnapshot(FontMeta font, int[] imageSize);

}
