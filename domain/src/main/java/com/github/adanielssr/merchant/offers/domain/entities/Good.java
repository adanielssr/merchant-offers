package com.github.adanielssr.merchant.offers.domain.entities;

import java.util.Collection;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by arodrigues on 19/08/2017.
 */
@Entity
@Table(name = "GOOD")
@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Good {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GoodType type;

    @OneToMany(mappedBy = "relatedGood")
    private Collection<Offer> includedIn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerid", referencedColumnName = "id")
    private Merchant owner;
}
