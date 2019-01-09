/**
 * 
 */
package com.iig.gcp.login.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.iig.gcp.login.dto.UserAccount;

/**
 * @author sivakumar.r14
 *
 */
public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	private UserAccount user;

	public UserDetailsImpl(UserAccount appUser, List<String> roleNames) {
		this.user = appUser;
		for (String roleName : roleNames) {

			GrantedAuthority grant = new SimpleGrantedAuthority(roleName);
			this.list.add(grant);
		}
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.list;
	}

	@Override
	public String getPassword() {
		return this.user.getJwt_token();
	}

	@Override
	public String getUsername() {
		return this.user.getUser_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
