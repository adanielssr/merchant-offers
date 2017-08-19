package com.github.adanielssr.merchant.offers.business.exceptions;

import java.text.MessageFormat;

/**
 * An exception to state that a required argument is missing
 */
public class MissingArgumentsException extends IllegalArgumentException {

    private static final long serialVersionUID = 7875396043869940931L;

    public MissingArgumentsException(String argument) {
        super(MessageFormat.format("{0} cannot be null/empty!", argument));
    }
}
