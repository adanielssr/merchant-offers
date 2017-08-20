package com.github.adanielssr.merchant.offers.business.exceptions;

import java.text.MessageFormat;

/**
 * An exception to mark the fact that a resource couldn't be found
 */
public class GoodNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 8742254268610815112L;

    public GoodNotFoundException(Long goodId) {
        super(MessageFormat.format("Good with Id: {0} does not exist!", goodId));
    }
}
