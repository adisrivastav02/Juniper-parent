package com.iig.gcp;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

@Component
public class CustomAuthenticationProvider1 implements AuthenticationProvider {

	
public static final long EXPIRATION_TIME = 864_000_000;
	
	public static final String SECRET = "SecretKeyToGenJWTs";
	
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

       /* if (shouldAuthenticateAgainstThirdPartySystem(username, password)) {

            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
              name, password, new ArrayList<>());
        } else {
            return null;
        }*/
        
        if(name.equals("admin") && password.equals("Info@1234")) {
        	
        	String token = JWT.create()
	                .withSubject(name)
	                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .sign(HMAC512(SECRET.getBytes()));
        	
        	System.out.println(token);
        	Authentication auth = new
                    UsernamePasswordAuthenticationToken(name, token, new ArrayList<>());
        	return auth;
        }else {
        	return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}