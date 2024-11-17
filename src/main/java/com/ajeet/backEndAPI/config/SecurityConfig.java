package com.ajeet.backEndAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
	
	// add basic authorization to api request.

	
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated()
				).httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	// in memory authentication 
	// create user and give role
	@Bean
	public UserDetailsService userDetailsService (){
		UserDetails ajeet = User.builder()
				.username("ajeet")
				.password(passwordEncoder().encode("ajeet"))
				.roles("ADMIN")
				.build();
		
		UserDetails vishal = User.builder()
				.username("vishal")
				.password(passwordEncoder().encode("vishal"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(ajeet,vishal);
	}
}
