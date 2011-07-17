/**
 * 
 */
package net.zeng.jxsite.module.system.dao;

import java.util.List;

import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Module;
import net.zeng.jxsite.module.system.service.BlockException;

/**
 * @author snzeng
 * 
 */
public interface BlockPrototypeDao {

	public void save(BlockPrototype prototype) throws BlockException;

	public Integer queryCount(String q) throws BlockException;

	public Integer queryCount(Module module) throws BlockException;

	public List<?> query(String q, Integer start, Integer limit) throws BlockException;

	public List<?> query(Module module, Integer start, Integer limit) throws BlockException;

	public BlockPrototype queryById(String id) throws BlockException;

	public BlockPrototype queryByPageUrl(String pageUrl) throws BlockException;

}
