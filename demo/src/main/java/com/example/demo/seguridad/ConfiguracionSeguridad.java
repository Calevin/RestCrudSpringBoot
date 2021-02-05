package com.example.demo.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.errores.CustomAccessDeniedHandler;
import com.example.demo.errores.JwtAuthenticationEntryPoint;
import com.example.demo.seguridad.jwt.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Autowired
	public ConfiguracionSeguridad(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
			, CustomAccessDeniedHandler customAccessDeniedHandler
			, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder
			, JwtAuthorizationFilter jwtAuthorizationFilter) {
		super();
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
			.and() 
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)			
			.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
				.antMatchers(HttpMethod.GET, "/producto/**").hasRole(RolUsuario.USER.toString())
				.antMatchers(HttpMethod.POST, "/producto/**").hasRole(RolUsuario.ADMIN.toString())
				.antMatchers(HttpMethod.PUT, "/producto/**").hasRole(RolUsuario.ADMIN.toString())
				.antMatchers(HttpMethod.DELETE, "/producto/**").hasRole(RolUsuario.ADMIN.toString())
				.antMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole(RolUsuario.values().toString())
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Para despues usar en un filtro
		return super.authenticationManagerBean();
	}
	
	
}
