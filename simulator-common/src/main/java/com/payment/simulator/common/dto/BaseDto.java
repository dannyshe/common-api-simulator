package com.payment.simulator.common.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class BaseDto implements Serializable {
    private static final ObjectMapper MAPPER;

    static {
        final DateFormat iso8601 = new ISO8601DateFormat();
        iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
        MAPPER = new ObjectMapper().registerModule(new Jdk8Module()).setDateFormat(iso8601);
    }

    @Override
    public String toString() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (final JsonProcessingException ioe) {
            return ioe.getLocalizedMessage();
        }
    }
}
