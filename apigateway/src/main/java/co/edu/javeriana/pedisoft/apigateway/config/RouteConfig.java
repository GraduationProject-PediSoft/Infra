package co.edu.javeriana.pedisoft.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public RouteLocator staticRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("explorer", r-> r.path("/explorer/**")
                        .filters(f -> f.rewritePath("/explorer/(?<remaining>.*)", "/${remaining}").filters())
                        .uri("lb://algorithm-explorer")
                )
                .route("files", r-> r.path("/files/**")
                        .filters(f -> f.filter(corsFilter).rewritePath("/files/(?<remaining>.*)", "/${remaining}"))
                        .uri("http://filemanager:8080")
                )
                .route("usermanager", r -> r.path("/user/**")
                        .filters(f -> f.rewritePath("/user/(?<remaining>.*)", "/${remaining}"))
                        .uri("http://usermanager:8080")
                )
                .build();
    }



}
