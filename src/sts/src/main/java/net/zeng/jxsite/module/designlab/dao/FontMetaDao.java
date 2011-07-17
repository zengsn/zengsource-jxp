/**
 * 
 */
package net.zeng.jxsite.module.designlab.dao;

import java.util.List;

import net.zeng.jxsite.module.designlab.model.FontMeta;

/**
 * @author snzeng
 * @since 6.0
 */
public interface FontMetaDao {

	public List<?> queryAll();

	public FontMeta queryById(String id);

	public void save(FontMeta font);

}
