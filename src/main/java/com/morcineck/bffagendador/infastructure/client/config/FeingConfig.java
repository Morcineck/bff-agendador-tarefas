package com.morcineck.bffagendador.infastructure.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeingConfig {

    @Bean
    public FeingErro feingErro(){
        return new FeingErro();
    }
}
