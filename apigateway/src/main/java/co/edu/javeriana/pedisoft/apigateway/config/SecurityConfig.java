package co.edu.javeriana.pedisoft.apigateway.config;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.List;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${issuerURI}")
    private String issuerURI;
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex.pathMatchers(HttpMethod.POST, "/files/").denyAll()
                        //This validation is done by the usermanager microservice, so here is not necessary to check
                        .pathMatchers("/user/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(
                                it -> it.jwtDecoder(JwtDecoders.fromIssuerLocation(issuerURI))
                        )
                )
                .build();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        val config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        val source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
