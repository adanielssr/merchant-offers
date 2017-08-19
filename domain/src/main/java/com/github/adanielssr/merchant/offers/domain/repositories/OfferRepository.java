package com.github.adanielssr.merchant.offers.domain.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.github.adanielssr.merchant.offers.domain.entities.Offer;

import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, String> {

    Optional<Offer> findByName(String name);

    List<Offer> findByNameIn(Collection<String> names);
}
