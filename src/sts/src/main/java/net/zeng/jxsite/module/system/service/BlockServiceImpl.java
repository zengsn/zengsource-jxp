/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import net.zeng.jxsite.module.system.dao.BlockInstanceDao;
import net.zeng.jxsite.module.system.dao.BlockPrototypeDao;

/**
 * @author snzeng
 *
 */
public class BlockServiceImpl implements BlockService {

	private BlockInstanceDao blockInstanceDao;
	private BlockPrototypeDao blockPrototypeDao;
	
	public BlockServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public BlockInstanceDao getBlockInstanceDao() {
		return blockInstanceDao;
	}
	
	public void setBlockInstanceDao(BlockInstanceDao blockInstanceDao) {
		this.blockInstanceDao = blockInstanceDao;
	}
	
	public BlockPrototypeDao getBlockPrototypeDao() {
		return blockPrototypeDao;
	}
	
	public void setBlockPrototypeDao(BlockPrototypeDao blockPrototypeDao) {
		this.blockPrototypeDao = blockPrototypeDao;
	}

}
