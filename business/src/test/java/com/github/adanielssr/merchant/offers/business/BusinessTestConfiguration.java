package com.github.adanielssr.merchant.offers.business;

import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessTestConfiguration {

    @Bean
    public OfferRepository tagRepository() {
        return Mockito.mock(OfferRepository.class);
    }

}
