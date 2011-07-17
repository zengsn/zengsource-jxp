/* */

package net.zeng.jxsite.module.system.security.util;

import org.springframework.security.userdetails.hierarchicalroles.UserDetailsWrapper;
import org.springframework.security.userdetails.hierarchicalroles.RoleHierarchy;
import org.springframework.security.userdetails.UserDetails;

public class CustomUserDetailsWrapper extends UserDetailsWrapper {

	private static final long serialVersionUID = 1L;
	private Object userInfo;

	public CustomUserDetailsWrapper(UserDetails userDetails, RoleHierarchy roleHierarchy) {
		super(userDetails, roleHierarchy);
	}

	public CustomUserDetailsWrapper(UserDetails userDetails, RoleHierarchy roleHierarchy,
			Object userInfo) {
		super(userDetails, roleHierarchy);
		this.userInfo = userInfo;
	}

	public Object getUserInfo() {
		return userInfo;
	}

}
