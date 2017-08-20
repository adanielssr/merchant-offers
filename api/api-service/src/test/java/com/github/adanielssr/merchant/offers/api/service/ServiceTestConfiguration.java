package com.github.adanielssr.merchant.offers.api.service;

import com.github.adanielssr.merchant.offers.business.services.OfferService;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfiguration {

    @Bean
    public OfferService offerService() {
        return Mockito.mock(OfferService.class);
    }
}
