/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.model.BlockInstance;
import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Page;

/**
 * @author zeng.xiaoning
 *
 */
public interface BlockInstanceService {

	public Integer searchCount(String page, String q) throws BlockException;

	public List<?> search(BlockPrototype prototype) throws BlockException;

	public List<?> search(String page, String q, Integer start, Integer limit) throws BlockException;

	public BlockInstance getById(String blockId) throws BlockException;

	public void save(BlockInstance instance) throws BlockException;

	public void delete(BlockInstance instance) throws BlockException;

	public BlockInstance search(Page page, BlockPrototype defaultPrototype) throws BlockException;

}
