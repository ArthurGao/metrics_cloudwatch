package com.parrotanalytics.api.commons;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class NoScientificNotationSerializer extends JsonSerializer<Object>
{

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException
    {
        if (value instanceof Double)
        {
            BigDecimal d = BigDecimal.valueOf(((Double) value).doubleValue());
            gen.writeString(d.toPlainString());
        }
        else if (value instanceof Map)
        {
            Map<String, Object> mapValues = (Map<String, Object>) value;
            gen.writeStartObject();
            for (Map.Entry<String, Object> entry : mapValues.entrySet())
            {
                String key = entry.getKey();
                BigDecimal val = BigDecimal.valueOf(((Double) entry.getValue()).doubleValue());
                gen.writeNumberField(key, val);
            }
            gen.writeEndObject();
        }

    }

}
