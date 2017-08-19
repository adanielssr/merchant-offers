package com.github.adanielssr.merchant.offers.api.boot;

import com.github.adanielssr.merchant.offers.api.service.ServiceConfiguration;
import com.github.adanielssr.merchant.offers.business.config.BusinessConfiguration;
import com.github.adanielssr.merchant.offers.domain.DomainConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Object[] { Application.class, ServiceConfiguration.class, DomainConfiguration.class,
                BusinessConfiguration.class }, args);
    }
}
