package com.ourtodo.withme.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ourtodo.withme.global.security.JwtAccessDeniedHandler;
import com.ourtodo.withme.global.security.JwtAuthenticationEntryPoint;
import com.ourtodo.withme.global.security.token.TokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			// 스프링 시큐리티는 기본적으로 세션을 사용
			// 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
			.and()
			.authorizeRequests()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated() // 나머지 API 는 전부 인증 필요
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}

}

