package com.github.adanielssr.merchant.offers.api.boot;

import java.io.IOException;
import java.util.List;

import com.github.adanielssr.merchant.offers.api.service.ServiceConfiguration;
import com.github.adanielssr.merchant.offers.business.config.BusinessConfiguration;
import com.github.adanielssr.merchant.offers.domain.DomainConfiguration;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, ServiceConfiguration.class, BusinessConfiguration.class,
        DomainConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
                                DbUnitTestExecutionListener.class })
@EnableAutoConfiguration
public class OffersControllerIT {

    public static final String OFFERS_PATH = "/offers";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DatabaseSetup("/db/FilledRepository.xml")
    @DirtiesContext
    public void testFindAllTags() throws IOException {
        List allTags = this.restTemplate.getForObject(OFFERS_PATH, List.class);

        Assert.assertNotNull(allTags);

        Assert.assertEquals(3, allTags.size());
    }

    @Test
    @DatabaseSetup("/db/EmptyRepository.xml")
    @DirtiesContext
    public void testFindAllTags_Empty() throws IOException {
        List allTags = this.restTemplate.getForObject(OFFERS_PATH, List.class);

        Assert.assertNotNull(allTags);

        Assert.assertEquals(0, allTags.size());
    }

}
