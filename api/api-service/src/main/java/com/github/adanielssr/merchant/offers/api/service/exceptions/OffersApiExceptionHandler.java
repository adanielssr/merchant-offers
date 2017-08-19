package com.github.adanielssr.merchant.offers.api.service.exceptions;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.github.adanielssr.merchant.offers.business.exceptions.InvalidParameterException;
import com.github.adanielssr.merchant.offers.business.exceptions.MissingArgumentsException;
import com.github.adanielssr.merchant.offers.business.exceptions.ResourceNotFoundException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OffersApiExceptionHandler {

    @ExceptionHandler({ MissingServletRequestParameterException.class, MissingArgumentsException.class,
                              InvalidParameterException.class })
    public void badRequest(HttpServletResponse response, Exception exception) throws IOException {
        buildResponse(response, exception, HttpServletResponse.SC_BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound(HttpServletResponse response, Exception exception) throws IOException {
        buildResponse(response, exception, HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler({ DataAccessException.class })
    public void dataAccessConflict(HttpServletResponse response, Exception exception) throws IOException {
        buildResponse(response, exception, HttpServletResponse.SC_CONFLICT);
    }

    private void buildResponse(HttpServletResponse response, Exception exception, int status) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.sendError(status, exception.getMessage());
    }
}
