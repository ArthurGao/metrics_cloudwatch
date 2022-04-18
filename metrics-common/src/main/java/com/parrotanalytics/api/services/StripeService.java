package com.parrotanalytics.api.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.apicore.utils.APIHelper;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;
import com.parrotanalytics.datasourceint.constants.Environment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionCollection;

@Service
public class StripeService
{
    private static Logger logger = LoggerFactory.getLogger(StripeService.class);

    private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

    static
    {
        Stripe.apiKey = apiConfig.readProperty(APIHelper.isRunningEnvironment(Environment.PRODUCTION)
                ? APIConfig.STRIPE_LIVE_KEY : APIConfig.STRIPE_TEST_KEY);
    }

    public Plan getSubscribedPlanByCustomer(String emailAddress)
    {

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 1);
        params.put("customer", emailAddress);

        try
        {
            CustomerCollection customers = Customer.list(params);
            if (customers.getHasMore())
            {
                Customer customer = customers.getData().get(0);
                params = new HashMap<>();
                params.put("limit", 1);
                params.put("customer", customer.getId());

                SubscriptionCollection subscriptions = Subscription.list(params);

                if (subscriptions.getHasMore())
                {
                    Subscription subscription = subscriptions.getData().get(0);
                    if (subscription != null && subscription.getPlan() != null)
                    {
                        return subscription.getPlan();
                    }
                }

            }
        }
        catch (StripeException e)
        {
            logger.error("Failed to retrieve subscribed plan of customr {}. Exception {}", emailAddress, e);
        }
        return null;

    }

    public boolean isCustomerSubscribePlan(String emailAddress)
    {
        return getSubscribedPlanByCustomer(emailAddress) != null;
    }

}
