package com.parrotanalytics.api.commons;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringToIntegerDeserialzer extends JsonDeserializer<Integer>

{
    @Override
    public Integer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        String value = jp.getText();

        return Integer.valueOf(value);
    }
}
