/**
 * 
 */
package net.zeng.jxsite.module.system.service;

import java.util.List;

import net.zeng.jxsite.module.system.model.security.AclClass;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface AclClassService {

	public int searchCount(String q);

	public List<?> search(String q, Integer start, Integer limit);

	public AclClass getById(String id);

	public AclClass getByClassName(String name);

	public void save(AclClass aclClass);

}
