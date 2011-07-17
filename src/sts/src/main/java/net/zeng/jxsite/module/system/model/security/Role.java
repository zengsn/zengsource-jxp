/**
 * 
 */
package net.zeng.jxsite.module.system.model.security;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.zeng.jxsite.module.system.model.UserInfo;

/**
 * Role is the security ID.
 * 
 * @author zeng.xiaoning
 * @since 6.0
 */
public class Role {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;
	private String name;
	private String description;

	private boolean isWrapper;
	private List<AclEntry> permissons = new LinkedList<AclEntry>();

	private Set<User> users = new HashSet<User>();

	private Long userCount = 0L;

	private Date createdTime;
	private Date updatedTime;

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(String name) {
		this();
		this.name = name;
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void addUser(UserInfo user) {
		if (user != null) {
			this.increaseUserCount();
			this.users.add(user.getUser());
		}
	}

	public void increaseUserCount() {
		this.userCount++;
	}

	public void removeUser(UserInfo user) {
		if (user != null) {
			for (User u : this.users) {
				if (user.getUser().getUsername().equals(u.getUsername())) {
					this.decreaseUserCount();
					this.users.remove(u);
					break;
				}
			}
		}
	}

	public void decreaseUserCount() {
		this.userCount--;
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String sid) {
		this.name = sid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String name) {
		this.description = name;
	}

	public boolean isWrapper() {
		return isWrapper;
	}

	public boolean getIsWrapper() {
		return isWrapper();
	}

	public void setWrapper(boolean isWrapper) {
		this.isWrapper = isWrapper;
	}

	public void setIsWrapper(boolean isWrapper) {
		setWrapper(isWrapper);
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public List<AclEntry> getPermissons() {
		return permissons;
	}

	public void setPermissons(List<AclEntry> permissons) {
		this.permissons = permissons;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
