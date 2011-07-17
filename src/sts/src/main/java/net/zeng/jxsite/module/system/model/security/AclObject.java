/**
 * 
 */
package net.zeng.jxsite.module.system.model.security;

import java.util.Date;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclObject {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private String id;
	private Role owner;
	private AclClass aclClass;
	private SecureObject secureObject;
	
	private Date createdTime;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public AclObject() {
		// TODO Auto-generated constructor stub
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getOwner() {
		return owner;
	}

	public void setOwner(Role owner) {
		this.owner = owner;
	}

	public AclClass getAclClass() {
		return aclClass;
	}

	public void setAclClass(AclClass aclClass) {
		this.aclClass = aclClass;
	}

	public SecureObject getSecureObject() {
		return secureObject;
	}

	public void setSecureObject(SecureObject secureObject) {
		this.secureObject = secureObject;
	}
	
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
