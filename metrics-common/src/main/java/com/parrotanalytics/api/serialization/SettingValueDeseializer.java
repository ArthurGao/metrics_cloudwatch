package com.parrotanalytics.api.serialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parrotanalytics.api.request.user.SettingsRequest;

/**
 * Custom JSON deserializer for users settings @see {@link SettingsRequest}
 * 
 * @author Sanjeev Sharma
 *
 */
public class SettingValueDeseializer extends com.fasterxml.jackson.databind.JsonDeserializer<SettingsRequest>
{
    @Override
    public SettingsRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException
    {
        @SuppressWarnings("unchecked")
        HashMap<String, Object> jsonMap = new ObjectMapper().readValue(jsonParser, HashMap.class);

        String settingType = readType(jsonMap);
        String settingName = readName(jsonMap);
        String renameTo = readRenameTo(jsonMap);
        Object settingValue = readValue(jsonMap, settingType);
        DateTime lastUsedOn = readLastUsedOn(jsonMap);

        SettingsRequest settingsRequest = new SettingsRequest(settingType, settingName, settingValue, lastUsedOn);
        settingsRequest.setRenameTo(renameTo);

        return settingsRequest;
    }

    private String readType(HashMap<String, Object> jsonMap)
    {
        return jsonMap.get("settingType") != null ? (String) jsonMap.get("settingType") : null;
    }

    private String readName(HashMap<String, Object> jsonMap)
    {
        return jsonMap.get("settingName") != null ? (String) jsonMap.get("settingName") : null;
    }

    private String readRenameTo(HashMap<String, Object> jsonMap)
    {
        return jsonMap.get("renameTo") != null ? (String) jsonMap.get("renameTo") : null;
    }

    @SuppressWarnings("unchecked")
    private Object readValue(HashMap<String, Object> jsonMap, String settingType)
    {
        Object settingValue = null;

        if (jsonMap.get("settingValue") != null && (jsonMap.get("settingValue") instanceof Map<?, ?>))
        {
            return (Map<String, Object>) jsonMap.get("settingValue");
        }
        else if (jsonMap.get("settingValue") != null && (jsonMap.get("settingValue") instanceof List<?>))
        {
            return (List<String>) jsonMap.get("settingValue");
        }
        else if (jsonMap.get("settingValue") != null && (jsonMap.get("settingValue") instanceof String))
        {
            return (String) jsonMap.get("settingValue");
        }

        return settingValue;
    }

    private DateTime readLastUsedOn(HashMap<String, Object> jsonMap)
    {
        DateTime lastUsedOn = null;

        try
        {
            if (jsonMap.get("lastUsedOn") != null)
            {
                lastUsedOn = new DateTime((String) jsonMap.get("lastUsedOn"), DateTimeZone.UTC);
            }
        }
        catch (Exception e)
        {
            lastUsedOn = null;
        }

        return lastUsedOn;
    }
}
