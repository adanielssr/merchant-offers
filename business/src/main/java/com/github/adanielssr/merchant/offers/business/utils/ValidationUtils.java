package com.github.adanielssr.merchant.offers.business.utils;

import com.github.adanielssr.merchant.offers.business.exceptions.MissingArgumentsException;

public interface ValidationUtils {

    static void assertNotNull(Object object, String argument) {
        if (object == null) {
            throw new MissingArgumentsException(argument);
        }
    }

}
