package com.atis.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class WebSecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new AtisUserDetailsService();
	}
	
	  @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	  
	  public DaoAuthenticationProvider authenticationProvider() {
		  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		  authProvider.setUserDetailsService(userDetailsService());
		  authProvider.setPasswordEncoder(passwordEncoder());
		  
		  return authProvider;
	  }
	  
	
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.authenticationProvider(authenticationProvider());
	  }
	  
	
	  @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
		    .antMatchers("/users/**", "/settings/**","/countries/**","/counties/**").hasAuthority("Admin")
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		       .loginPage("/login")
		       .usernameParameter("email")
		       .permitAll()
		     .and().logout().permitAll();
	  
		  
		  return http.build();
		 
	  }
	  
	  @Bean
	  public WebSecurityCustomizer websecurityCustomizer() throws Exception {
		  return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	  }
	

}
