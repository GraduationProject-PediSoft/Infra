package co.edu.javeriana.pedisoft.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator staticRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("explorer", r-> r.path("/explorer/**")
                        .filters(f -> f.rewritePath("/explorer/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://algorithm-explorer")
                )
                .route("files", r-> r.path("/files/**")
                        .filters(f -> f.rewritePath("/files/(?<remaining>.*)", "/${remaining}"))
                        .uri("http://filemanager:9000")
                )
                .build();
    }

}
