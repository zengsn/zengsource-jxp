/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.model.BlockPrototype;
import net.zeng.jxsite.module.system.model.Module;

/**
 * @author snzeng
 *
 */
public interface BlockPrototypeService {

	public void save(BlockPrototype prototype) throws BlockException;

	public Integer searchCount(String q) throws BlockException;

	public Integer searchCount(Module module) throws BlockException;

	public List<?> search(String q, Integer start, Integer limit) throws BlockException;

	public List<?> search(Module module, Integer start, Integer limit) throws BlockException;

	public BlockPrototype getById(String id) throws BlockException;

	public BlockPrototype getByPageUrl(String pageUrl) throws BlockException;


}
