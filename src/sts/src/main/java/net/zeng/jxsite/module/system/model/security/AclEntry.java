/**
 * 
 */
package net.zeng.jxsite.module.system.model.security;

import java.util.Date;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class AclEntry {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public static final Integer ZERO = 0;
	public static final Integer READ = 1;
	public static final Integer WRITE = 2;
	public static final Integer READ_WRITE = 3;

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private Role role;
	private Integer mask = ZERO;
	private AclObject aclObject;
	
	private Date createdTime;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AclEntry() {
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

	public AclObject getAclObject() {
		return aclObject;
	}

	public void setAclObject(AclObject aclObject) {
		this.aclObject = aclObject;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getMask() {
		return mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
