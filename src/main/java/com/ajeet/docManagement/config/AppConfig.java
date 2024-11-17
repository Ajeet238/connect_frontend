package com.ajeet.docManagement.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {
	@SuppressWarnings("removal")
	@Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Disable CSRF for simplicity (be cautious in production)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**").permitAll()  // Allow access to all endpoints starting with /auth/
                .anyRequest().authenticated()  // All other requests require authentication
            );

        return http.build();
    }
//	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//		System.out.print("insidefilter");
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//		.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
//		.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).csrf().disable()
//		.cors().configurationSource(new CorsConfigurationSource() {
//			
//			@Override
//			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//				CorsConfiguration cfg = new CorsConfiguration();
//			cfg.setAllowCredentials(true);
//				
//				cfg.setAllowedMethods(Collections.singletonList("*"));
//				
//				cfg.setAllowedHeaders(Collections.singletonList("*"));
//				cfg.setExposedHeaders(Arrays.asList("Authorization"));
//				cfg.setMaxAge(3600L);
//				
//				return cfg;
//			}
//		})
//		.and().httpBasic().and().formLogin();
//		return http.build();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.print("passwordEncoder");
		return new BCryptPasswordEncoder();
	}
}
