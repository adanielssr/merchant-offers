package com.github.adanielssr.merchant.offers.business.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.adanielssr.merchant.offers.business.BusinessTestConfiguration;
import com.github.adanielssr.merchant.offers.business.config.BusinessConfiguration;
import com.github.adanielssr.merchant.offers.business.exceptions.GoodNotFoundException;
import com.github.adanielssr.merchant.offers.business.exceptions.InvalidParameterException;
import com.github.adanielssr.merchant.offers.business.exceptions.MerchantNotFoundException;
import com.github.adanielssr.merchant.offers.business.exceptions.MissingArgumentsException;
import com.github.adanielssr.merchant.offers.business.mappers.OfferMapper;
import com.github.adanielssr.merchant.offers.domain.entities.Good;
import com.github.adanielssr.merchant.offers.domain.entities.Merchant;
import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.domain.entities.OfferStatus;
import com.github.adanielssr.merchant.offers.domain.repositories.GoodRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.MerchantRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BusinessConfiguration.class, BusinessTestConfiguration.class })
@DirtiesContext
public class OfferServiceTest {

    private static final Long MERCHANT_ID = 123L;

    private static final Long GOOD_ID = 321L;

    private static final Long CREATED_ID = 12321L;

    @Autowired
    private OfferService service;

    @Autowired
    private OfferRepository repository;

    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OfferMapper mapper;

    @Test
    @DirtiesContext
    public void testFindAllEmpty() throws Exception {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<OfferDto> result = service.findAll();

        assertEquals(0, result.size());
    }

    @Test
    @DirtiesContext
    public void testFindAll() throws Exception {
        Offer offer = new Offer();
        when(repository.findAll()).thenReturn(Arrays.asList(offer));

        List<OfferDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(mapper.map(offer), result.get(0));

        verify(repository, times(1)).findAll();
    }

    @Test(expected = MissingArgumentsException.class)
    @DirtiesContext
    public void testCreateOfferWithNullOffer() throws Exception {
        service.createOffer(null);
    }

    @Test(expected = MissingArgumentsException.class)
    @DirtiesContext
    public void testCreateOfferWithNullCurrencyCode() throws Exception {
        service.createOffer(OfferDto.builder().build());
    }

    @Test(expected = MissingArgumentsException.class)
    @DirtiesContext
    public void testCreateOfferWithNullNewPrice() throws Exception {
        service.createOffer(OfferDto.builder().currencyCode("EUR").build());
    }

    @Test(expected = MissingArgumentsException.class)
    @DirtiesContext
    public void testCreateOfferWithNullDescription() throws Exception {
        service.createOffer(OfferDto.builder().currencyCode("EUR").newPrice(1.0D).build());
    }

    @Test(expected = MissingArgumentsException.class)
    @DirtiesContext
    public void testCreateOfferWithNullMerchantOwnerId() throws Exception {
        service.createOffer(
                OfferDto.builder().currencyCode("EUR").newPrice(1.0D).description("Some description").build());
    }

    @Test(expected = MissingArgumentsException.class)
    @DirtiesContext
    public void testCreateOfferWithNullRelatedGoodId() throws Exception {
        service.createOffer(OfferDto.builder().currencyCode("EUR").newPrice(1.0D).description("Some description")
                .merchantOwnerId(MERCHANT_ID).build());
    }

    @Test(expected = InvalidParameterException.class)
    @DirtiesContext
    public void testCreateOfferWithInvalidNewPrice() throws Exception {
        service.createOffer(OfferDto.builder().currencyCode("EUR").newPrice(0.0D).description("Some description")
                .merchantOwnerId(MERCHANT_ID).relatedGoodId(GOOD_ID).build());
    }

    @Test(expected = InvalidParameterException.class)
    @DirtiesContext
    public void testCreateOfferWithInvalidCurrencyCode() throws Exception {
        service.createOffer(OfferDto.builder().currencyCode("POUND").newPrice(1.0D).description("Some description")
                .merchantOwnerId(MERCHANT_ID).relatedGoodId(GOOD_ID).build());
    }

    @Test(expected = MerchantNotFoundException.class)
    @DirtiesContext
    public void testCreateOfferWithNotFoundMerchant() throws Exception {
        when(merchantRepository.findOne(eq(MERCHANT_ID))).thenReturn(null);

        service.createOffer(OfferDto.builder().currencyCode("EUR").newPrice(1.0D).description("Some description")
                .merchantOwnerId(MERCHANT_ID).relatedGoodId(GOOD_ID).build());
    }

    @Test(expected = GoodNotFoundException.class)
    @DirtiesContext
    public void testCreateOfferWithNotFoundRelatedGood() throws Exception {
        when(merchantRepository.findOne(eq(MERCHANT_ID))).thenReturn(Merchant.builder().id(MERCHANT_ID).build());
        when(goodRepository.findOne(eq(GOOD_ID))).thenReturn(null);

        service.createOffer(OfferDto.builder().currencyCode("EUR").newPrice(1.0D).description("Some description")
                .merchantOwnerId(MERCHANT_ID).relatedGoodId(GOOD_ID).build());
    }

    @Test
    @DirtiesContext
    public void testCreateOfferOverrideIdAndStatusAndRelatesWithMerchantAndGood() throws Exception {
        Merchant merchant = Merchant.builder().id(MERCHANT_ID).build();
        when(merchantRepository.findOne(eq(MERCHANT_ID))).thenReturn(merchant);
        Good good = Good.builder().id(GOOD_ID).build();
        when(goodRepository.findOne(eq(GOOD_ID))).thenReturn(good);

        ArgumentCaptor<Offer> offerArgumentCaptor = ArgumentCaptor.forClass(Offer.class);

        when(repository.save(offerArgumentCaptor.capture())).thenReturn(Offer.builder().build());

        Long fakeId = Long.MAX_VALUE;
        String invalidStatus = "INVALID_STATUS";

        service.createOffer(OfferDto.builder().id(fakeId).status(invalidStatus).currencyCode("EUR").newPrice(1.0D)
                .description("Some description").merchantOwnerId(MERCHANT_ID).relatedGoodId(GOOD_ID).build());

        verify(merchantRepository).findOne(eq(MERCHANT_ID));
        verify(goodRepository).findOne(eq(GOOD_ID));
        verify(repository).save(any(Offer.class));

        assertEquals(1, offerArgumentCaptor.getAllValues().size());
        Offer savedOffer = offerArgumentCaptor.getValue();
        assertNotEquals(fakeId, savedOffer.getId());
        assertNotEquals(invalidStatus, savedOffer.getStatus());

        assertNull(savedOffer.getId());
        assertNotNull(savedOffer.getStatus());
        assertEquals(OfferStatus.ACTIVE, savedOffer.getStatus());
        assertEquals(merchant, savedOffer.getOwner());
        assertEquals(good, savedOffer.getRelatedGood());
    }

    @Test
    @DirtiesContext
    public void testCreateOffer() throws Exception {
        Merchant merchant = Merchant.builder().id(MERCHANT_ID).build();
        when(merchantRepository.findOne(eq(MERCHANT_ID))).thenReturn(merchant);
        Good good = Good.builder().id(GOOD_ID).build();
        when(goodRepository.findOne(eq(GOOD_ID))).thenReturn(good);

        when(repository.save(any(Offer.class))).thenReturn(
                Offer.builder().id(CREATED_ID).status(OfferStatus.ACTIVE).currency("EUR").newPrice(1.0D)
                        .description("Some description").owner(merchant).relatedGood(good).build());

        OfferDto createdOffer = service.createOffer(
                OfferDto.builder().currencyCode("EUR").newPrice(1.0D).description("Some description")
                        .merchantOwnerId(MERCHANT_ID).relatedGoodId(GOOD_ID).build());

        assertNotNull(createdOffer);
        assertNotNull(createdOffer.getId());
        assertEquals(CREATED_ID, createdOffer.getId());
        assertEquals("EUR", createdOffer.getCurrencyCode());
        assertEquals((Double)1.0D, createdOffer.getNewPrice());
        assertEquals("Some description", createdOffer.getDescription());
        assertEquals(OfferStatus.ACTIVE.name(), createdOffer.getStatus());
        assertEquals(MERCHANT_ID, createdOffer.getMerchantOwnerId());
        assertEquals(GOOD_ID, createdOffer.getRelatedGoodId());
    }
}