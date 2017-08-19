package com.github.adanielssr.merchant.offers.business.exceptions;

import java.text.MessageFormat;

/**
 * An exception to state that a required argument is missing
 */
public class InvalidParameterException extends IllegalArgumentException {

    private static final long serialVersionUID = 261156649044135057L;

    public InvalidParameterException(String argumentName, Object argument) {
        super(MessageFormat.format("Parameter {0} cannot be {1}!", argumentName, argument));
    }
}
