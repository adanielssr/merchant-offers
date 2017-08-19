package com.github.adanielssr.merchant.offers.business.services;

import java.util.ArrayList;
import java.util.List;

import com.github.adanielssr.merchant.offers.business.mappers.OfferMapper;
import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OfferService {

    private final OfferRepository repository;

    private final OfferMapper mapper;

    @Autowired
    public OfferService(OfferRepository repository, OfferMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * retrieves all The tags in the DB
     *
     * @return a list with all the tags in the DB
     */
    public List<OfferDto> findAll() {
        List<OfferDto> offers = new ArrayList<>();
        repository.findAll().forEach(offer -> offers.add(mapper.map(offer)));
        return offers;
    }
}
