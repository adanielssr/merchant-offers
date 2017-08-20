package com.github.adanielssr.merchant.offers.api.boot;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.github.adanielssr.merchant.offers.api.service.ServiceConfiguration;
import com.github.adanielssr.merchant.offers.business.config.BusinessConfiguration;
import com.github.adanielssr.merchant.offers.domain.DomainConfiguration;
import com.github.adanielssr.merchant.offers.dto.OfferDto;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, ServiceConfiguration.class, BusinessConfiguration.class,
        DomainConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
                                DbUnitTestExecutionListener.class })
@EnableAutoConfiguration
public class OffersControllerIT {

    public static final String OFFERS_PATH = "/offers";

    public static final String MESSAGE_SPRING_KEY = "message";

    private static final Long GOOD_ID = 1L;

    private static final Long MERCHANT_ID = 1L;

    @Autowired
    private TestRestTemplate restTemplate;

    private Gson gson = new GsonBuilder().create();

    @Test
    @DatabaseSetup("/db/EmptyRepository.xml")
    public void testFindAllOffers_Empty() throws IOException {
        List allOffers = this.restTemplate.getForObject(OFFERS_PATH, List.class);

        assertNotNull(allOffers);

        assertEquals(0, allOffers.size());
    }

    @Test
    @DatabaseSetup("/db/FilledRepository.xml")
    public void testFindAllOffers() throws IOException {
        List allOffers = this.restTemplate.getForObject(OFFERS_PATH, List.class);

        assertNotNull(allOffers);

        assertEquals(2, allOffers.size());
    }

    @Test
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingCurrencyCode() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setCurrencyCode(null);

        ResponseEntity<String> createdOfferDtoResponse = this.restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("currencyCode"));
    }

    @Test
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingNewPrice() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setNewPrice(null);

        ResponseEntity<String> createdOfferDtoResponse = this.restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("newPrice"));
    }

    private OfferDto buildNewOfferDto() {
        return OfferDto.builder().currencyCode("EUR").newPrice(10.0D).description("Offer for Product A")
                .relatedGoodId(GOOD_ID).merchantOwnerId(MERCHANT_ID).build();
    }

}
