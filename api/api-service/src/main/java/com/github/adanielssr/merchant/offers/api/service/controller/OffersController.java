package com.github.adanielssr.merchant.offers.api.service.controller;

import java.util.List;

import com.github.adanielssr.merchant.offers.business.services.OfferService;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "offers")
public class OffersController {

    private OfferService service;

    @Autowired
    public OffersController(OfferService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<OfferDto>> getTags() {
        return ResponseEntity.ok(service.findAll());
    }
}
