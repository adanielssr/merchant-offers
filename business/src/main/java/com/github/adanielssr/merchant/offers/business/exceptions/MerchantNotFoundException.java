package com.github.adanielssr.merchant.offers.business.exceptions;

import java.text.MessageFormat;

/**
 * An exception to mark the fact that a resource couldn't be found
 */
public class MerchantNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 8742254268610815112L;

    public MerchantNotFoundException(Long merchantId) {
        super(MessageFormat.format("Merchant with Id: {0} does not exist!", merchantId));
    }
}
