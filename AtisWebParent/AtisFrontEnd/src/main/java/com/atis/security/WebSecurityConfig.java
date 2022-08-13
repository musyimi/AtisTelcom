package com.atis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.atis.security.oauth.CustomerOAuth2UserService;
import com.atis.security.oauth.OAuth2LoginSuccessHandler;

@Configuration

public class WebSecurityConfig {
	
	@Autowired
	private CustomerOAuth2UserService oAuth2UserService;
	@Autowired
	private OAuth2LoginSuccessHandler oauth2LoginHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
  
	
	    
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
		         .antMatchers("/customer").authenticated()		        
		         .anyRequest().permitAll()
		         .and()
		         .formLogin()
		             .loginPage("/login")
		             .usernameParameter("email")
		             .permitAll()
		         .and()
		        
		         .logout().permitAll()
		         .and()
		         .rememberMe()
		             .key("987654321_abcdefghijklmnopqrstuvwxyz")
		             .tokenValiditySeconds(7 * 24 * 60 * 60);
	  
		  
		  return http.build();
		 
	  }
	  
	  @Bean
	  public WebSecurityCustomizer websecurityCustomizer() throws Exception {
		  return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	  }
	  
	  @Bean
	  public UserDetailsService  userDetailsService() {
		  return new CustomerUserDetailsService();
	  }
	  
	  @Bean
	  public DaoAuthenticationProvider authenticationProvider() {
		  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		  
		  authProvider.setUserDetailsService(userDetailsService());
		  authProvider.setPasswordEncoder(passwordEncoder());
		  
		  return authProvider;
	  }
	

}
