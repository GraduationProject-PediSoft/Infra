package co.edu.javeriana.pedisoft.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import java.util.List;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${JWK_URI}")
    private String jwkURI;
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
		.cors(Customizer.withDefaults())
                .authorizeExchange(ex -> ex.pathMatchers(HttpMethod.POST, "/files/").denyAll()
                        //This validation is done by the usermanager microservice, so here is not necessary to check
                        .pathMatchers("/user/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(
                                it -> it.jwkSetUri(jwkURI)
                        )
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));
        //configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
