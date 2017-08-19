package com.github.adanielssr.merchant.offers.business.mappers;

import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    OfferDto map(Offer offer);

}
