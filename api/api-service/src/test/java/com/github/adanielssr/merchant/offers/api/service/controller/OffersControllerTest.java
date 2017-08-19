package com.github.adanielssr.merchant.offers.api.service.controller;

import java.util.Collections;
import java.util.List;

import com.github.adanielssr.merchant.offers.api.service.ServiceConfiguration;
import com.github.adanielssr.merchant.offers.api.service.ServiceTestConfiguration;
import com.github.adanielssr.merchant.offers.business.exceptions.OfferNotFoundException;
import com.github.adanielssr.merchant.offers.business.services.OfferService;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestConfiguration.class, ServiceConfiguration.class, })
public class OffersControllerTest {

    @Autowired
    private OffersController controller;

    @Autowired
    private OfferService service;

    @Test
    public void testFindAll_Empty() throws OfferNotFoundException {
        Mockito.when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OfferDto>> result = controller.getTags();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());

        Assert.assertNotNull(result.getBody());
        Assert.assertTrue(result.getBody().isEmpty());
    }

    @Test
    public void testFindAll() throws OfferNotFoundException {
        Mockito.when(service.findAll())
                .thenReturn(Collections.singletonList(OfferDto.builder().id(1L).name("offer 1").build()));

        ResponseEntity<List<OfferDto>> result = controller.getTags();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());

        Assert.assertNotNull(result.getBody());
        Assert.assertEquals(1, result.getBody().size());
    }
}
