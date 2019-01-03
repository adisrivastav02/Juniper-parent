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
		// TODO Auto-generated method stub
		return this.list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUser_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
