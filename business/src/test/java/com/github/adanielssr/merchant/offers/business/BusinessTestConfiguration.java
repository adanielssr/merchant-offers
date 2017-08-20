package com.github.adanielssr.merchant.offers.business;

import com.github.adanielssr.merchant.offers.domain.repositories.GoodRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.MerchantRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessTestConfiguration {

    @Bean
    public OfferRepository offerRepository() {
        return Mockito.mock(OfferRepository.class);
    }

    @Bean
    public GoodRepository goodRepository() {
        return Mockito.mock(GoodRepository.class);
    }

    @Bean
    public MerchantRepository merchantRepository() {
        return Mockito.mock(MerchantRepository.class);
    }

}
