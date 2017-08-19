package com.github.adanielssr.merchant.offers.business.exceptions;

/**
 * An exception to mark the fact that a resource couldn't be found
 */
public class ResourceNotFoundException extends Exception {

    private static final long serialVersionUID = 7103524036174508930L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
