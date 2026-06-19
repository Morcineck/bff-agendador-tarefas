package com.morcineck.bffagendador.infastructure.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public FeignErro feingErro(){
        return new FeignErro();
    }
}
