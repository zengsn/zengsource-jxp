/* 
 */

package net.zeng.jxsite.module.system.security.util;

import net.zeng.jxsite.module.system.model.security.SecureObject;

import org.springframework.security.acls.sid.Sid;
import org.springframework.security.acls.*;

public interface AclSecurityUtil {

	public void addPermission(SecureObject securedObject, Permission permission,
			Class<?> clazz);

	public void addPermission(SecureObject securedObject, Sid recipient,
			Permission permission, Class<?> clazz);

	public void deletePermission(SecureObject securedObject, Sid recipient,
			Permission permission, Class<?> clazz);

}
