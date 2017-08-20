package com.github.adanielssr.merchant.offers.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto implements Serializable {

    private static final long serialVersionUID = 2818526241400302283L;

    @JsonProperty
    @JsonPropertyDescription("Offer Id")
    private Long id;

    @JsonProperty
    @JsonPropertyDescription("Offer friendly description")
    private String description;

    @JsonProperty
    @JsonPropertyDescription("New price for the Good within this offer")
    private Double newPrice;

    @JsonProperty
    @JsonPropertyDescription("Currency of the price of this offer")
    private String currencyCode;

    @JsonProperty
    @JsonPropertyDescription("Offer status [ACTIVE,EXPIRED]")
    private String status;

    @JsonProperty
    @JsonPropertyDescription("Id of the Good that this offer represents")
    private Long relatedGoodId;

    @JsonProperty
    @JsonPropertyDescription("Id of the merchant that are publishing this offer")
    private Long merchantOwnerId;

}
