package com.parrotanalytics.api.services;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.utils.RestCaller;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;

@Service
public class HubspotService
{
    protected static final Logger logger = LogManager.getLogger(HubspotService.class);

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    public ResponseEntity<String> updateCancelStatus2HubspotCustomer(String emailAddress, String plan,
            String feedbackReason)
    {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/json");

        Map<String, List<String>> queryParams = new HashMap<String, List<String>>();

        String hubspotKeyAPI = getHubspotAPIKey();

        if (StringUtils.isNotEmpty(hubspotKeyAPI))
            queryParams.put("hapikey", Arrays.asList(hubspotKeyAPI));

        JSONArray properties = new JSONArray();

        properties.put(new JSONObject().put("property", "plan_status").put("value", "cancelled"));
        if (StringUtils.isNotEmpty(feedbackReason))
            properties.put(new JSONObject().put("property", "monitor_cancel_reason").put("value", feedbackReason));

        JSONObject payloadObject = new JSONObject();
        payloadObject.put("properties", properties);

        StringWriter out = new StringWriter();
        payloadObject.write(out);

        String requestPayload = out.toString();

        ResponseEntity<String> result;
        try
        {
            result = RestCaller.executeCall(
                    String.format("https://api.hubapi.com/contacts/v1/contact/email/%s/profile", emailAddress),
                    HttpMethod.POST, queryParams, requestPayload, requestHeaders);
        }
        catch (APIException e)
        {
            logger.debug("Failed to call Hubspot API endpoint. Exception {}", e);
            return null;
        }
        // success call return NO_CONTENT 204
        return result;
    }

    private String getHubspotAPIKey()
    {
        return apiConfig.readProperty(APIConfig.HUBSPOT_API_KEY);
    }

}
