package com.todo;

import com.todo.utils.security.AuthenticationFilter;
import com.todo.utils.security.CustomTokenAuthenticationProvider;
import com.todo.utils.security.CustomUsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
public class TodoListApplication {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Configuration
	@EnableWebSecurity
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private CustomUsernamePasswordAuthenticationProvider customUsernamePasswordAuthenticationProvider;

		@Autowired
		private CustomTokenAuthenticationProvider customTokenAuthenticationProvider;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
				.antMatchers("/api/auth/logged").authenticated()
				.antMatchers("/api/auth/getUser").authenticated()
				.antMatchers("/api/todo/**").authenticated()
				.antMatchers("/**").permitAll()
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedEntryPoint());

			http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
				.authenticationProvider(customUsernamePasswordAuthenticationProvider)
				.authenticationProvider(customTokenAuthenticationProvider);
		}

		@Bean
		public AuthenticationEntryPoint unauthorizedEntryPoint() {
			return (request, response, authException)
					-> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}
}
