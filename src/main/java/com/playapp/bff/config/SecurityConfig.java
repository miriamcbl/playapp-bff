package com.playapp.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(AbstractHttpConfigurer::disable).securityMatcher("/**")
				.authorizeHttpRequests(
						registry -> registry.requestMatchers(AntPathRequestMatcher.antMatcher("/ai/**")).permitAll()
								.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui.html"))
								.permitAll() // Permitir acceso a Swagger UI y la documentación de la API)
								.anyRequest().authenticated())

				.build();
    }

}
