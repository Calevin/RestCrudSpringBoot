package com.example.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.errores.CustomAccessDeniedHandler;
import com.example.demo.errores.CustomBasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {

	private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public ConfiguracionSeguridad(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint
			, CustomAccessDeniedHandler customAccessDeniedHandler
			, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		super();
		this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.authenticationEntryPoint(customBasicAuthenticationEntryPoint)
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
			.and() 
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/producto/**").hasRole(RolUsuario.USER.toString())
				.antMatchers(HttpMethod.POST, "/producto/**").hasRole(RolUsuario.ADMIN.toString())
				.antMatchers(HttpMethod.PUT, "/producto/**").hasRole(RolUsuario.ADMIN.toString())
				.antMatchers(HttpMethod.DELETE, "/producto/**").hasRole(RolUsuario.ADMIN.toString())
				.antMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole(RolUsuario.values().toString())
			.anyRequest().authenticated()
			.and()
			.csrf().disable();
		
	}
}
