package com.parrotanalytics.api.util;

import java.nio.charset.Charset;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.commons.exceptions.service.ParrotServiceException;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class HelpdeskAPICaller
{

    /**
     * fetches the catalog items for given parrot id(s)
     * 
     * @param parrotIDs
     * @return
     * @return
     * @throws ParrotServiceException
     */
    public static ResponseEntity<String> call(HttpMethod httpMethod, String uri, String requestPayload)
            throws APIException
    {

        String apiHost = "https://helpdeskapi.parrotanalytics.com/demandportal/";
        apiHost = "http://localhost:9090/demandportal";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.add("Origin", "1a4a97b7-37ae-4577-91ba-5282dc3bca96");
        HttpEntity<String> entity = new HttpEntity<String>(requestPayload, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(apiHost + uri, httpMethod, entity, String.class);
        ResponseEntity<String> apiResponse = new ResponseEntity<String>(response.getBody(), response.getStatusCode());

        return apiResponse;
    }

}
