/*

 */

package net.zeng.jxsite.module.system.model.security;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {

	// ~~~ STATIC ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	public static final int ENABLED = 1;
	public static final int DISABLED = 2;

	// ~~~ PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String id;

	private String username;

	private String password;

	private int enabled;

	private Role wrapper = new Role();

	// TODO change to List
	private List<Role> roles = new LinkedList<Role>();

	// ~~~ CONSTRUCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public User() {
		this.enabled = ENABLED;
	}

	public User(String username, String password, int enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	// ~~~ FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void disable() {
		setEnabled(DISABLED);
	}

	public void enable() {
		setEnabled(ENABLED);
	}

	public void addRole(Role role) {
		if (role != null) {
			this.roles.add(role);
		}
	}

	public void removeRole(Role role) {
		if (role != null) {
			for (Role r : this.roles) {
				if (role.getId().equals(r.getId())) {
					this.roles.remove(r);
					break;
				}
			}
		}
	}

	@Override
	public String toString() {
		return getUsername();
	}

	// ~~~ G^SETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Role getWrapper() {
		return wrapper;
	}

	public void setWrapper(Role wrapper) {
		this.wrapper = wrapper;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
