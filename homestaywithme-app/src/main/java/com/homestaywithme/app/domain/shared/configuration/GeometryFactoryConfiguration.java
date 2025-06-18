package com.homestaywithme.app.domain.shared.configuration;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeometryFactoryConfiguration {
    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }
}
