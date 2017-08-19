package com.github.adanielssr.merchant.offers.domain;

import com.github.adanielssr.merchant.offers.domain.entities.Offer;
import com.github.adanielssr.merchant.offers.domain.repositories.OfferRepository;

import org.junit.Assert;
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

    @Autowired
    private OfferRepository offerRepository;

    @Test(expected = DataIntegrityViolationException.class)
    public void createEmptyOffer() {
        offerRepository.save(new Offer());
    }

    @Test
    public void createOffer() {
        offerRepository.deleteAll();

        Offer entity = new Offer();
        entity.setName("offername");

        entity = offerRepository.save(entity);

        Assert.assertNotNull(entity.getCreatedAt());
    }

    @Test
    public void createDuplicatedOffer() {
        offerRepository.deleteAll();

        Offer offer1 = new Offer();
        offer1.setName("offerName");
        offer1 = offerRepository.save(offer1);

        Offer offer2 = new Offer();
        offer2.setName("offerName");
        offer2 = offerRepository.save(offer2);

        Assert.assertTrue(offer1.getCreatedAt().isBefore(offer2.getCreatedAt()));
    }
}
