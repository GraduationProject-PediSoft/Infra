package co.edu.javeriana.pedisoft.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwksUri;
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex.pathMatchers(HttpMethod.POST, "/files").denyAll()
                        //This validation is done by the usermanager microservice, so here is not necessary to check
                        .pathMatchers("/user/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(
                                it -> it.jwkSetUri(jwksUri)
                        )
                )
                .build();
    }
}
