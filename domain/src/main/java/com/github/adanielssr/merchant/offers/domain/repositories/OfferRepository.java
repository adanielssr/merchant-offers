package com.github.adanielssr.merchant.offers.domain.repositories;

import com.github.adanielssr.merchant.offers.domain.entities.Offer;

import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Long> {
}
