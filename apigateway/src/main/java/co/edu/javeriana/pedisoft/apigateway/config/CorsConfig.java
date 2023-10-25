package co.edu.javeriana.pedisoft.apigateway.config;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig extends CorsConfiguration {
    @Bean
    public CorsWebFilter corsFilter(){
        val config = new CorsConfiguration();
        config.setAllowCredentials( true );
        config.setAllowedOrigins( List.of( "*" ) );
        config.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD" ) );
        config.setAllowedHeaders( List.of( "origin", "content-type", "accept", "authorization", "cookie" ) );
        val source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );
        return new CorsWebFilter( source );
    }
}
