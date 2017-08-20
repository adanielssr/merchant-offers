package com.github.adanielssr.merchant.offers.domain.repositories;

import com.github.adanielssr.merchant.offers.domain.entities.Merchant;

import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

}
