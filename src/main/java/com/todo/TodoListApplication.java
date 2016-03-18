package com.todo;

import com.todo.service.user.UserService;
import com.todo.utils.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

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
		private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

		@Autowired
		private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

		@Autowired
		private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

		@Autowired
		private Http401UnauthorizedEntryPoint authenticationEntryPoint;

		@Autowired
		private CustomAccessDeniedHandler customAccessDeniedHandler;

		@Autowired
		private CustomAuthenticationProvider customAuthenticationProvider;

		@Autowired
		private UserService userService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable() //csrfTokenRepository(csrfTokenRepository())
//			.and()
				.formLogin()
				.loginProcessingUrl("/api/authentication")
				.successHandler(ajaxAuthenticationSuccessHandler)
				.failureHandler(ajaxAuthenticationFailureHandler)
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/api/auth/logout")
				.logoutSuccessHandler(ajaxLogoutSuccessHandler)
				.logoutSuccessUrl("/")
				.invalidateHttpSession(Boolean.TRUE)
				.deleteCookies("JSESSIONID")
				.permitAll()
			.and()
				.headers()
				.frameOptions()
				.disable()
			.and()
				.authorizeRequests()
				.antMatchers("/api/auth/logged").authenticated()
				.antMatchers("/api/auth/getUser").authenticated()
				.antMatchers("/api/todo/**").authenticated()
				.antMatchers("/**").permitAll()
			.and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
//				.accessDeniedHandler(customAccessDeniedHandler)
			.and()
//				.addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
				.headers().cacheControl();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService);
			auth.authenticationProvider(customAuthenticationProvider);
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}
}
