package com.github.adanielssr.merchant.offers.api.service.exceptions;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * Created by arodrigues on 20/08/2017.
 */
public class OffersApiExceptionHandlerTest {

    private static final String EXCEPTION_MESSAGE = "Error Message";

    private OffersApiExceptionHandler handler;

    @Before
    public void setup() {
        this.handler = new OffersApiExceptionHandler();
    }

    @Test
    public void testBadRequest() throws IOException {
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentTypeCaptor = ArgumentCaptor.forClass(String.class);

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        doNothing().when(response).sendError(statusCaptor.capture(), messageCaptor.capture());
        doNothing().when(response).setContentType(contentTypeCaptor.capture());

        Exception exception = new Exception(EXCEPTION_MESSAGE);

        handler.badRequest(response, exception);

        verify(response).sendError(anyInt(), anyString());
        verify(response).setContentType(anyString());

        assertEquals((Integer) HttpServletResponse.SC_BAD_REQUEST, statusCaptor.getValue());
        assertEquals(EXCEPTION_MESSAGE, messageCaptor.getValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, contentTypeCaptor.getValue());
    }

    @Test
    public void testNotFound() throws IOException {
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentTypeCaptor = ArgumentCaptor.forClass(String.class);

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Exception exception = new Exception(EXCEPTION_MESSAGE);

        doNothing().when(response).sendError(statusCaptor.capture(), messageCaptor.capture());
        doNothing().when(response).setContentType(contentTypeCaptor.capture());

        handler.notFound(response, exception);

        verify(response).sendError(anyInt(), anyString());
        verify(response).setContentType(anyString());

        assertEquals((Integer) HttpServletResponse.SC_NOT_FOUND, statusCaptor.getValue());
        assertEquals(EXCEPTION_MESSAGE, messageCaptor.getValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, contentTypeCaptor.getValue());
    }

    @Test
    public void testDataAccessConflict() throws IOException {
        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentTypeCaptor = ArgumentCaptor.forClass(String.class);

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Exception exception = new Exception(EXCEPTION_MESSAGE);

        doNothing().when(response).sendError(statusCaptor.capture(), messageCaptor.capture());
        doNothing().when(response).setContentType(contentTypeCaptor.capture());

        handler.dataAccessConflict(response, exception);

        verify(response).sendError(anyInt(), anyString());
        verify(response).setContentType(anyString());

        assertEquals((Integer) HttpServletResponse.SC_CONFLICT, statusCaptor.getValue());
        assertEquals(EXCEPTION_MESSAGE, messageCaptor.getValue());
        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, contentTypeCaptor.getValue());
    }
}
