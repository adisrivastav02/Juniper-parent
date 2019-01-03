package com.iig.gcp.login.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iig.gcp.AppRole;
import com.iig.gcp.login.dto.UserAccount;

@Service
@Transactional
public class UserDetailsContextMapperImpl implements UserDetailsContextMapper {

	@Autowired
	private LoginService loginService;
	
	/*@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("######################################" + arg0);
		return null;
	}*/

	@Override
	public UserDetails mapUserFromContext(DirContextOperations arg0, String arg1,
			Collection<? extends GrantedAuthority> arg2) {
		UserAccount arrUserAccount= null;
		List<String> userRoles = null;
		try {
			arrUserAccount= loginService.findUserFromId(arg1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			arrUserAccount = new UserAccount();
			arrUserAccount.setUser_id(arg1);
		}
		try {
			userRoles= loginService.findUserRoles(arg1);
			userRoles.add(AppRole.GENERIC_USER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			userRoles = new ArrayList<String>();
			userRoles.add(AppRole.GENERIC_USER);
		}
		
		return new UserDetailsImpl(arrUserAccount,userRoles);
	}

	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		// TODO Auto-generated method stub
		System.out.println("done ######");
		
	}

}
