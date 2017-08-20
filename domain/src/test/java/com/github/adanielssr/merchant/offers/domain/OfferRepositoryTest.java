package com.github.adanielssr.merchant.offers.domain;

import java.util.Currency;

import com.github.adanielssr.merchant.offers.domain.entities.Good;
import com.github.adanielssr.merchant.offers.domain.entities.GoodType;
import com.github.adanielssr.merchant.offers.domain.entities.Merchant;
import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.domain.entities.OfferStatus;
import com.github.adanielssr.merchant.offers.domain.repositories.GoodRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.MerchantRepository;
import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DomainConfiguration.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
                                TransactionalTestExecutionListener.class })
@DirtiesContext
public class OfferRepositoryTest {

    private static final String MERCHANT_NAME = "Merchant A";

    private static final String PRODUCT_NAME = "Product A";

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private GoodRepository goodRepository;

    private Merchant merchant;

    private Good good;

    @Before
    public void setup() {
        goodRepository.deleteAll();
        merchantRepository.deleteAll();
        offerRepository.deleteAll();

        merchant = merchantRepository.save(Merchant.builder().name(MERCHANT_NAME).build());
        good = goodRepository.save(Good.builder().name(PRODUCT_NAME).owner(merchant).type(GoodType.PRODUCT)
                .description("product description").build());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    @DirtiesContext
    public void createEmptyOffer() {
        offerRepository.save(new Offer());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void createOfferWithNullPrice() {
        offerRepository
                .save(Offer.builder().description("Offer of Product A").currency(getEur()).status(OfferStatus.ACTIVE)
                        .owner(merchant).relatedGood(good).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void createOfferWithNullCurrency() {
        offerRepository.save(Offer.builder().description("Offer of Product A").newPrice(1.0D).status(OfferStatus.ACTIVE)
                .owner(merchant).relatedGood(good).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void createOfferWithNullStatus() {
        offerRepository.save(Offer.builder().description("Offer of Product A").newPrice(1.0D).currency(getEur())
                .owner(merchant).relatedGood(good).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void createOfferWithNullMerchant() {
        offerRepository.save(Offer.builder().description("Offer of Product A").newPrice(1.0D).currency(getEur())
                .status(OfferStatus.ACTIVE).relatedGood(good).build());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @DirtiesContext
    public void createOfferWithNullGood() {
        offerRepository.save(Offer.builder().description("Offer of Product A").newPrice(1.0D).currency(getEur())
                .status(OfferStatus.ACTIVE).owner(merchant).build());
    }

    @Test
    @DirtiesContext
    public void createSuccessfullyAnOffer() {
        Offer entity = new Offer();
        entity.setDescription("Offer description");
        entity.setNewPrice(1.0D);
        entity.setCurrency(getEur());
        entity.setStatus(OfferStatus.ACTIVE);
        entity.setOwner(merchant);
        entity.setRelatedGood(good);

        Offer newEntity = offerRepository.save(entity);
        Assert.assertNotNull(newEntity);
        Assert.assertNotNull(newEntity.getId());
    }

    private String getEur() {
        return Currency.getInstance("EUR").getCurrencyCode();
    }
}
