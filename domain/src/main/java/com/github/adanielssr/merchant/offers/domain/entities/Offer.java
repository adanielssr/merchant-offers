package com.github.adanielssr.merchant.offers.domain.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "OFFER")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double newPrice;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "relatedgoodid", referencedColumnName = "id")
    private Good relatedGood;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerid", referencedColumnName = "id")
    private Merchant owner;
}
