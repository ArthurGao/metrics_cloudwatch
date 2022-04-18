package com.parrotanalytics.api.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.parrotanalytics.api.request.customersuccess.AmityRequest;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.utils.RestCaller;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class CustomerSuccessAPICaller
{

    public static String callAmity(String request, String url) throws APIException
    {

        Map<String, String> requestHeaders = new HashMap<String, String>();

        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("Authorization", AmityRequest.KEY);

        String result = RestCaller.restCall(url, HttpMethod.POST, null, request, requestHeaders);

        return result;
    }

    public static String callAskNicely(String request, String url) throws APIException
    {

        Map<String, String> requestHeaders = new HashMap<String, String>();

        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("X-apikey", "IoWOmK6oWYjb7F1xewsGDeoZFnWoQ1YsN6yvPt1DmlP");

        String result = RestCaller.restCall(url, HttpMethod.POST, null, request, requestHeaders);

        return result;
    }

}
