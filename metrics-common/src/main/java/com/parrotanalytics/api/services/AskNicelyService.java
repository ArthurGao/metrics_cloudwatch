package com.parrotanalytics.api.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.api.request.customersuccess.AskNicelyUserRequest;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.utils.RestCaller;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;

/**
 * AskNicely communication deserves to be refactored into a separate Service
 * class
 *
 * @author minhvu
 */

@Service
public class AskNicelyService
{
    private static final Logger logger = LogManager.getLogger(AskNicelyService.class);

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    private static String host = "https://parrotanalytics.asknice.ly/api/v1";

    // this is stupid to hard code api key
    private static String apiKey = apiConfig.readProperty(APIConfig.ASKNICELY_API_KEY);

    public ResponseEntity<String> addPerson(InternalUser user)
    {
        ResponseEntity<String> result = null;
        if (!user.getEmailAddress().contains("@parrotanalytics.com"))
        {
            ObjectMapper mapper = new ObjectMapper();

            try
            {
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String emailAddress = user.getEmailAddress();

                String department = StringUtils.isEmpty(user.getDepartment()) ? "Unknown" : user.getDepartment();

                String title = StringUtils.isEmpty(user.getTitle()) ? "Unknown" : user.getTitle();

                String accountName = user.getAccount().getAccountName();

                Integer customerId = user.getAccount().getCustomer().getIdCustomer();

                AskNicelyUserRequest askNicelyUserRequest = new AskNicelyUserRequest();
                askNicelyUserRequest.setFirstname(firstName);
                askNicelyUserRequest.setLastname(lastName);
                askNicelyUserRequest.setName(firstName + " " + lastName);
                askNicelyUserRequest.setEmail(emailAddress);
                askNicelyUserRequest.setTitle(title);
                askNicelyUserRequest.setDepartment(department);
                askNicelyUserRequest.setAccount(accountName);
                askNicelyUserRequest.setSegment(customerId == 18 ? "Consulting only" : "DP Subscription");

                String userrequest = mapper.writeValueAsString(askNicelyUserRequest);

                String request = "{\"people\": [" + userrequest + "]}";

                String url = host + "/person/add";

                Map<String, String> requestHeaders = new HashMap<>();

                requestHeaders.put("Content-Type", "application/json");
                requestHeaders.put("X-apikey", apiKey);

                result = RestCaller.executeCall(url, HttpMethod.POST, null, request, requestHeaders);
            }
            catch (Exception e)
            {
                logger.error("failed to invoke AskNicely /person/add endpoint", e);
            }
        }
        return result;
    }

    public boolean isEnabled()
    {
        return StringUtils.isNotEmpty(System.getProperty("asknicely.enabled"));
    }

    /**
     * This will set a person to in-active, the person will not be deleted from
     * AskNicely, add a person to re-activate a contact
     *
     * @param emailAddress
     * @return
     */
    public ResponseEntity<String> removeUser(String emailAddress)
    {
        String url = String.format(host + "/person/remove/%s/email", emailAddress);

        Map<String, String> requestHeaders = new HashMap<String, String>();

        requestHeaders.put("Content-Type", "application/json");
        requestHeaders.put("X-apikey", apiKey);

        ResponseEntity<String> result = null;
        try
        {
            result = RestCaller.executeCall(url, HttpMethod.GET, null, null, requestHeaders);
        }
        catch (APIException e)
        {
            logger.error("failed to invoke AskNicely /person/remove endpoint.Exception {}", e);

        }
        return result;
    }

}
