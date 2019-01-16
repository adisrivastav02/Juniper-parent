package com.iig.gcp.login.service;


import java.sql.Date;
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

import io.jsonwebtoken.Jwts;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@Transactional
public class UserDetailsContextMapperImpl implements UserDetailsContextMapper {

	public static final long EXPIRATION_TIME = 864_000_000;
	
	public static final String SECRET = "SecretKeyToGenJWTs";
	
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public UserDetails mapUserFromContext(DirContextOperations arg0, String arg1,
			Collection<? extends GrantedAuthority> arg2) {
		UserAccount arrUserAccount= null;
		List<String> userRoles = null;
		String token = null;
		try {
			arrUserAccount= loginService.findUserFromId(arg1);
			 token = JWT.create()
	                .withSubject(arg1)
	                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .sign(HMAC512(SECRET.getBytes()));
			 arrUserAccount.setJwt_token(token);
			 System.out.println("Token Parent" + token);
		} catch (Exception e) {
			e.printStackTrace();
			arrUserAccount = new UserAccount();
			arrUserAccount.setUser_id(arg1);
		}
		try {
			userRoles= loginService.findUserRoles(arg1);
			userRoles.add(AppRole.GENERIC_USER);
		} catch (Exception e) {
			e.printStackTrace();
			userRoles = new ArrayList<String>();
			userRoles.add(AppRole.GENERIC_USER);
		}
		
		return new UserDetailsImpl(arrUserAccount,userRoles);
	}
	

	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		System.out.println("done ######");
	}
}
