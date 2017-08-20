package com.github.adanielssr.merchant.offers.business.services;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.github.adanielssr.merchant.offers.business.exceptions.GoodNotFoundException;
import com.github.adanielssr.merchant.offers.business.exceptions.InvalidParameterException;
import com.github.adanielssr.merchant.offers.business.exceptions.MerchantNotFoundException;
import com.github.adanielssr.merchant.offers.business.mappers.OfferMapper;
import com.github.adanielssr.merchant.offers.business.utils.ValidationUtils;
import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.domain.entities.OfferStatus;
import com.github.adanielssr.merchant.offers.domain.repositories.GoodRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.MerchantRepository;
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

    private final GoodRepository goodRepository;

    private final MerchantRepository merchantRepository;

    private final OfferMapper mapper;

    private final Set<String> availableCurrencies;

    @Autowired
    public OfferService(OfferRepository repository, GoodRepository goodRepository,
            MerchantRepository merchantRepository, OfferMapper mapper) {
        this.repository = repository;
        this.goodRepository = goodRepository;
        this.merchantRepository = merchantRepository;
        this.mapper = mapper;
        this.availableCurrencies = ConcurrentHashMap.newKeySet();
        this.availableCurrencies
                .addAll(Currency.getAvailableCurrencies().stream().map(currency -> currency.getCurrencyCode())
                        .collect(Collectors.toSet()));
    }

    /**
     * retrieves all The Offers in the DB
     *
     * @return a list with all the offers in the DB
     */
    public List<OfferDto> findAll() {
        List<OfferDto> offers = new ArrayList<>();
        repository.findAll().forEach(offer -> offers.add(mapper.map(offer)));
        return offers;
    }

    /**
     * Creates a new Offer from the data on the dto. Values for Id and Status will be overridden by a generated Id and ACTIVE status.
     *
     * @param dto the object with Offer data to persist
     * @return the created Offer
     * @throws com.github.adanielssr.merchant.offers.business.exceptions.MissingArgumentsException if dto is null or any of the following is not present on the dto Object: currencyCode, newPrice, description, merchantOwnerId, relatedGoodId
     * @throws InvalidParameterException                                                           if newPrice on the dto Object is less or equal than zero or currencyCode is not on the list of available Currencies in the JVM
     * @throws MerchantNotFoundException                                                           if there's no Merchant in the DB with id equal to merchantOwnerId
     * @throws GoodNotFoundException                                                               if there's no Good in the DB with id equal to relatedGoodId
     */
    public OfferDto createOffer(OfferDto dto) throws MerchantNotFoundException, GoodNotFoundException {
        validateOffer(dto);

        //Define business defaults
        dto.setId(null);
        dto.setStatus(OfferStatus.ACTIVE.name());

        Offer offer = mapper.map(dto);

        offer.setOwner(Optional.ofNullable(merchantRepository.findOne(dto.getMerchantOwnerId()))
                .orElseThrow(() -> new MerchantNotFoundException(dto.getMerchantOwnerId())));

        offer.setRelatedGood(Optional.ofNullable(goodRepository.findOne(dto.getRelatedGoodId()))
                .orElseThrow(() -> new GoodNotFoundException(dto.getRelatedGoodId())));

        Offer newOffer = repository.save(offer);

        return mapper.map(newOffer);
    }

    private void validateOffer(OfferDto dto) {
        ValidationUtils.assertNotNull(dto, "offer");

        ValidationUtils.assertNotNull(dto.getCurrencyCode(), "currencyCode");
        ValidationUtils.assertNotNull(dto.getNewPrice(), "newPrice");

        ValidationUtils.assertNotNull(dto.getDescription(), "description");
        ValidationUtils.assertNotNull(dto.getMerchantOwnerId(), "merchantOwnerId");
        ValidationUtils.assertNotNull(dto.getRelatedGoodId(), "relatedGoodId");

        if (dto.getNewPrice() <= 0.0D) {
            throw new InvalidParameterException("newPrice", dto.getNewPrice());
        }
        if (!this.availableCurrencies.contains(dto.getCurrencyCode())) {
            throw new InvalidParameterException("currencyCode", dto.getCurrencyCode());
        }
    }
}
