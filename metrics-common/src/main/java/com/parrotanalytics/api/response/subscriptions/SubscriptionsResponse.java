package com.parrotanalytics.api.response.subscriptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.response.titles.SubscriptionTitleResponse;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
        "warning", "accountid", "accountname", "customerid", "customername", "pageinfo", "subscriptions",
        "available_markets"
})
@JsonInclude(Include.NON_NULL)
public class SubscriptionsResponse extends APIResponse
{
    private static final long serialVersionUID = 3942013483179937950L;

    @JsonProperty("accountid")
    private String accountId;

    @JsonProperty("accountname")
    private String accountName;

    @JsonProperty("customerid")
    private String customerId;

    @JsonProperty("customername")
    private String customerName;

    @JsonProperty("available_markets")
    private List<Country> availableMarkets;

    private SubscriptionsItems subscriptions;

    @JsonProperty("subscription_type")
    private String subscriptionType;

    @JsonProperty("subscription_ref_id")
    private Integer subscriptionRefId;

    /**
     * @return the accountId
     */
    public String getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId
     *            the accountId to set
     */
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return the accountName
     */
    public String getAccountName()
    {
        return accountName;
    }

    /**
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName()
    {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    /**
     * @return the subscriptions
     */
    public SubscriptionsItems getSubscriptions()
    {
        return subscriptions;
    }

    /**
     * @param subscriptions
     *            the subscriptions to set
     */
    public void setSubscriptions(SubscriptionsItems subscriptions)
    {
        this.subscriptions = subscriptions;
    }

    public List<Country> getAvailableMarkets()
    {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<Country> availableMarkets)
    {
        this.availableMarkets = availableMarkets;
    }

    public String getSubscriptionType()
    {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType)
    {
        this.subscriptionType = subscriptionType;
    }

    public Integer getSubscriptionRefId()
    {
        return subscriptionRefId;
    }

    public void setSubscriptionRefId(Integer subscriptionRefId)
    {
        this.subscriptionRefId = subscriptionRefId;
    }

    /**
     * 
     * @param title
     */
    public void addTitle(SubscriptionTitleResponse title)
    {
        if (this.subscriptions == null)
        {
            this.subscriptions = new SubscriptionsItems();
        }
        this.subscriptions.getTitles().add(title);
    }

}
