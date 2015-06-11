package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;;

@SuppressWarnings("serial")
public class UserSecurityContext extends User implements Serializable {

	private com.thingtrack.tachoreader.domain.User user;
	
	public UserSecurityContext(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
	}
	
	public UserSecurityContext(com.thingtrack.tachoreader.domain.User user, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		this.user = user;
	}

	public com.thingtrack.tachoreader.domain.User getUser() {
		return this.user;
		
	}
}
