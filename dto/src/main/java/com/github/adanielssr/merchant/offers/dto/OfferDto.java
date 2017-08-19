package com.github.adanielssr.merchant.offers.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @JsonPropertyDescription("OfferDto Id")
    private Long id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty
    private LocalDateTime createdAt;

}
