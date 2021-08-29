package com.gptw.authenticationservice.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gptw.authenticationservice.security.JWTAuthenticationFilter;
import com.gptw.authenticationservice.security.JWTAuthorizationFilter;
import com.gptw.authenticationservice.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	//Somente banco de dados h2 liberado
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
			"/index",
			"/login"
	};
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if(Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
			
			http.cors().and().csrf().disable();
			
			http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers("/js/**").permitAll()
				.anyRequest().authenticated();
			
			http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
			
			http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
			
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	//Defifindo acesso básico de multiplas fontes para todos os caminhos
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		
		return source;
	}
	
}
