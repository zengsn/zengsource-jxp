/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Page;
import net.zeng.jxsite.module.system.service.BlockException;

/**
 * @author snzeng
 * 
 */
public interface BlockInstanceDao {

	public Integer queryCount(Page page, String q) throws BlockException;

	public List<?> query(BlockPrototype prototype) throws BlockException;

	public List<?> query(Page page, String q, Integer start, Integer limit) throws BlockException;

	public BlockInstance query(Page page, BlockPrototype prototype) throws BlockException;

	public BlockInstance queryById(String id) throws BlockException;

	public void save(BlockInstance instance) throws BlockException;

	public void delete(BlockInstance instance) throws BlockException;

}
