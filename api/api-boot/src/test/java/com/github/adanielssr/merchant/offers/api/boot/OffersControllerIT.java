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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServiceConfiguration.class, BusinessConfiguration.class, DomainConfiguration.class },
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
                                DbUnitTestExecutionListener.class })
@EnableAutoConfiguration
public class OffersControllerIT {

    public static final String OFFERS_PATH = "/offers";

    public static final String MESSAGE_SPRING_KEY = "message";

    private static final Long GOOD_ID = 1L;

    private static final Long MERCHANT_ID = 1L;

    private static final Long NON_EXISTENT_MERCHANT = Long.MAX_VALUE;

    private static final Long NON_EXISTENT_GOOD = Long.MAX_VALUE;

    @Autowired
    private TestRestTemplate restTemplate;

    private Gson gson = new GsonBuilder().create();

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepository.xml")
    public void testFindAllOffersWithEmptyDB() throws IOException {
        List allOffers = restTemplate.getForObject(OFFERS_PATH, List.class);

        assertNotNull(allOffers);

        assertEquals(0, allOffers.size());
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/FilledRepository.xml")
    public void testFindAllOffers() throws IOException {
        List allOffers = restTemplate.getForObject(OFFERS_PATH, List.class);

        assertNotNull(allOffers);

        assertEquals(2, allOffers.size());
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingCurrencyCode() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setCurrencyCode(null);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("currencyCode"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingNewPrice() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setNewPrice(null);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("newPrice"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingDescription() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setDescription(null);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("description"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingMerchantOwnerId() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setMerchantOwnerId(null);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("merchantOwnerId"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferMissingRelatedGoodId() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setRelatedGoodId(null);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("relatedGoodId"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferInvalidNewPrice() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setNewPrice(-1.0D);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("newPrice"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferInvalidCurrencyCode() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setCurrencyCode("INVALID");

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("currencyCode"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferNonexistentMerchantId() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setMerchantOwnerId(NON_EXISTENT_MERCHANT);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.NOT_FOUND, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("Merchant"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferNonexistentGood() throws IOException {
        OfferDto offerDto = buildNewOfferDto();
        offerDto.setRelatedGoodId(NON_EXISTENT_GOOD);

        ResponseEntity<String> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, String.class);

        assertEquals(HttpStatus.NOT_FOUND, createdOfferDtoResponse.getStatusCode());
        Map<String, Object> jsonError = gson.fromJson(createdOfferDtoResponse.getBody(), Map.class);

        assertTrue(jsonError.containsKey(MESSAGE_SPRING_KEY));
        assertTrue(((String) jsonError.get(MESSAGE_SPRING_KEY)).contains("Good"));
    }

    @Test
    @DirtiesContext
    @DatabaseSetup("/db/EmptyRepositoryWithProductAndGood.xml")
    public void testCreateOfferSuccess() throws IOException {
        OfferDto offerDto = buildNewOfferDto();

        ResponseEntity<OfferDto> createdOfferDtoResponse = restTemplate
                .postForEntity(OFFERS_PATH, offerDto, OfferDto.class);

        assertEquals(HttpStatus.CREATED, createdOfferDtoResponse.getStatusCode());

        OfferDto bodyOfferDto = createdOfferDtoResponse.getBody();
        assertNotNull(bodyOfferDto);
        assertNotNull(bodyOfferDto.getId());
        assertEquals("ACTIVE", bodyOfferDto.getStatus());
        assertEquals(offerDto.getNewPrice(), bodyOfferDto.getNewPrice());
        assertEquals(offerDto.getCurrencyCode(), bodyOfferDto.getCurrencyCode());
        assertEquals(offerDto.getDescription(), bodyOfferDto.getDescription());
        assertEquals(offerDto.getMerchantOwnerId(), bodyOfferDto.getMerchantOwnerId());
        assertEquals(offerDto.getRelatedGoodId(), bodyOfferDto.getRelatedGoodId());
    }

    private OfferDto buildNewOfferDto() {
        return OfferDto.builder().currencyCode("EUR").newPrice(10.0D).description("Offer for Product A")
                .relatedGoodId(GOOD_ID).merchantOwnerId(MERCHANT_ID).build();
    }

}
