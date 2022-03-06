package com.ourtodo.withme.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ourtodo.withme.global.interceptor.LogInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LogInterceptor logInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");
	}

	@Bean
	public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
		CommonsRequestLoggingFilter c = new CommonsRequestLoggingFilter();
		c.setIncludeHeaders(true);
		c.setIncludeQueryString(true);
		c.setIncludePayload(true);
		c.setIncludeClientInfo(true);
		c.setMaxPayloadLength(100000);
		return c;
	}

}
