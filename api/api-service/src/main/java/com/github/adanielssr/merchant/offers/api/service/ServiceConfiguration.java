package com.github.adanielssr.merchant.offers.api.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.github.adanielssr.merchant.offers.api.service.controller",
        "com.github.adanielssr.merchant.offers.api.service.exceptions" })
public class ServiceConfiguration {
}
