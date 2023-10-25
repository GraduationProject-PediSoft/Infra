package co.edu.javeriana.pedisoft.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class GlobalConfig {
    @Value("${issuerURI}")
    private String issuerURI;
    @Bean
    public NimbusReactiveJwtDecoder reactiveJwtDecoder(){
        return NimbusReactiveJwtDecoder.withIssuerLocation(issuerURI).build();
    }
}
