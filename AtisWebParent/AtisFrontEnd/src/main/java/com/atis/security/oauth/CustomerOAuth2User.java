package com.atis.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomerOAuth2User implements OAuth2User {
	
	private OAuth2User oauth2user;
	
	

	public CustomerOAuth2User(OAuth2User user) {
		this.oauth2user = user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oauth2user.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oauth2user.getAuthorities();
	}

	@Override
	public String getName() {
		return oauth2user.getAttribute("name");
	}
	
	public String getEmail() {
		return oauth2user.getAttribute("email");
	}
	
	public String getFullName() {
		return oauth2user.getAttribute("name");

		
	}

}
