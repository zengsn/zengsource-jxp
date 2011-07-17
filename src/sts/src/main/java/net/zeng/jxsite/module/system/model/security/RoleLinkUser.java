/**
 * 
 */
package net.zeng.jxsite.module.system.model.security;

import java.util.Date;

/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class RoleLinkUser {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String roleId;
	private String userId;
	private int index;
	private Date createdTime;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public RoleLinkUser() {
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String username) {
		this.userId = username;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
