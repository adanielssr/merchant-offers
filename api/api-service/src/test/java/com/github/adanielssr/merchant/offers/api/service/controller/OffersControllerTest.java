package com.github.adanielssr.merchant.offers.api.service.controller;

import java.util.Collections;
import java.util.List;

import com.github.adanielssr.merchant.offers.api.service.ServiceConfiguration;
import com.github.adanielssr.merchant.offers.api.service.ServiceTestConfiguration;
import com.github.adanielssr.merchant.offers.business.exceptions.GoodNotFoundException;
import com.github.adanielssr.merchant.offers.business.exceptions.MerchantNotFoundException;
import com.github.adanielssr.merchant.offers.business.services.OfferService;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestConfiguration.class, ServiceConfiguration.class, })
public class OffersControllerTest {

    @Autowired
    private OffersController controller;

    @Autowired
    private OfferService service;

    @Test
    @DirtiesContext
    public void testFindAllEmpty() {
        Mockito.when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OfferDto>> result = controller.getAllOffers();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertTrue(result.getBody().isEmpty());
    }

    @Test
    @DirtiesContext
    public void testFindAll() {
        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(OfferDto.builder().id(1L).build()));

        ResponseEntity<List<OfferDto>> result = controller.getAllOffers();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertEquals(1, result.getBody().size());
        Assert.assertEquals((Long) 1L, result.getBody().iterator().next().getId());
    }

    @Test(expected = MerchantNotFoundException.class)
    @DirtiesContext
    public void testCreateOfferWithNotFoundMerchant() throws MerchantNotFoundException, GoodNotFoundException {
        Mockito.when(service.createOffer(any(OfferDto.class))).thenThrow(MerchantNotFoundException.class);

        controller.createOffer(OfferDto.builder().id(1L).build());
    }

    @Test(expected = GoodNotFoundException.class)
    @DirtiesContext
    public void testCreateOfferWithNotFoundGood() throws MerchantNotFoundException, GoodNotFoundException {
        Mockito.when(service.createOffer(any(OfferDto.class))).thenThrow(GoodNotFoundException.class);

        controller.createOffer(OfferDto.builder().id(1L).build());
    }

    @Test(expected = RuntimeException.class)
    @DirtiesContext
    public void testCreateOfferWithAnyRuntimeException() throws MerchantNotFoundException, GoodNotFoundException {
        Mockito.when(service.createOffer(any(OfferDto.class))).thenThrow(RuntimeException.class);

        controller.createOffer(OfferDto.builder().id(1L).build());
    }

    @Test
    @DirtiesContext
    public void testCreateOffer() throws MerchantNotFoundException, GoodNotFoundException {
        OfferDto offerDto = OfferDto.builder().id(1L).build();
        Mockito.when(service.createOffer(any(OfferDto.class))).thenReturn(offerDto);

        ResponseEntity<OfferDto> result = controller.createOffer(offerDto);

        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertEquals(offerDto, result.getBody());
    }
}
