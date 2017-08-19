package com.github.adanielssr.merchant.offers.business.services;

import java.util.Arrays;
import java.util.List;

import com.github.adanielssr.merchant.offers.business.BusinessTestConfiguration;
import com.github.adanielssr.merchant.offers.business.config.BusinessConfiguration;
import com.github.adanielssr.merchant.offers.business.mappers.OfferMapper;
import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BusinessConfiguration.class, BusinessTestConfiguration.class })
@DirtiesContext
public class OfferServiceTest {

    @Autowired
    private OfferService service;

    @Autowired
    private OfferRepository repository;

    @Autowired
    private OfferMapper mapper;

    @Test
    @DirtiesContext
    public void findAll() throws Exception {
        Offer offer = new Offer();
        when(repository.findAll()).thenReturn(Arrays.asList(offer));

        List<OfferDto> result = service.findAll();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(mapper.map(offer), result.get(0));

        verify(repository, times(1)).findAll();
    }
}