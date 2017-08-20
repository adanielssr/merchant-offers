package com.github.adanielssr.merchant.offers.api.service.controller;

import java.util.List;

import com.github.adanielssr.merchant.offers.business.exceptions.GoodNotFoundException;
import com.github.adanielssr.merchant.offers.business.exceptions.MerchantNotFoundException;
import com.github.adanielssr.merchant.offers.business.services.OfferService;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "offers")
public class OffersController {

    private final OfferService service;

    @Autowired
    public OffersController(OfferService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OfferDto>> getAllOffers() {
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferDto offerDto)
            throws MerchantNotFoundException, GoodNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOffer(offerDto));
    }
}
