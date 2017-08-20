package com.github.adanielssr.merchant.offers.business.mappers;

import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.dto.OfferDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "currency", target = "currencyCode")
    @Mapping(source = "relatedGood.id", target = "relatedGoodId")
    @Mapping(source = "owner.id", target = "merchantOwnerId")
    OfferDto map(Offer offer);

    @Mapping(source = "currencyCode", target = "currency")
    @Mapping(target = "relatedGood", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Offer map(OfferDto offerDto);

}
