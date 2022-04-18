package com.parrotanalytics.api.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.response.customer.CustomerResponse;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class UserAccountResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1641875363149829648L;

    @JsonProperty("account_id")
    protected Integer idAccount;

    @JsonProperty("account_name")
    protected String accountName;

    @JsonProperty("account_type")
    protected String accountType;

    @JsonProperty("api_id")
    protected String apiId;

    @JsonProperty("customer")
    protected CustomerResponse customer;

    @JsonProperty("division")
    protected String division;

    @JsonProperty("primary_contact_name")
    protected String primaryContactName;

    @JsonProperty("primary_contact_number")
    protected String primaryContactNumber;

    @JsonProperty("primary_contact_email")
    protected String primaryContactEmail;

    @JsonProperty("home_market")
    protected Integer homeMarket;

    @JsonProperty("status")
    protected Integer status;

    @JsonProperty("access_level")
    private String accessLevel;

    @JsonProperty("access_level_percent")
    private Integer accessLevelPercent;

    @JsonProperty("subscription_start")
    protected String subscription_start;

    @JsonProperty("historical_data_offset")
    protected Integer historical_data_offset;

    @JsonProperty("account_level")
    protected String accountLevel;

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String getApiId()
    {
        return apiId;
    }

    public void setApiId(String apiId)
    {
        this.apiId = apiId;
    }

    public String getDivision()
    {
        return division;
    }

    public void setDivision(String division)
    {
        this.division = division;
    }

    public String getPrimaryContactName()
    {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName)
    {
        this.primaryContactName = primaryContactName;
    }

    public String getPrimaryContactNumber()
    {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber)
    {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getPrimaryContactEmail()
    {
        return primaryContactEmail;
    }

    public void setPrimaryContactEmail(String primaryContactEmail)
    {
        this.primaryContactEmail = primaryContactEmail;
    }

    public Integer getHomeMarket()
    {
        return homeMarket;
    }

    public void setHomeMarket(Integer homeMarket)
    {
        this.homeMarket = homeMarket;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getAccessLevel()
    {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel)
    {
        this.accessLevel = accessLevel;
    }

    public Integer getAccessLevelPercent()
    {
        return accessLevelPercent;
    }

    public void setAccessLevelPercent(Integer accessLevelPercent)
    {
        this.accessLevelPercent = accessLevelPercent;
    }

    public String getSubscription_start()
    {
        return subscription_start;
    }

    public void setSubscription_start(String subscription_start)
    {
        this.subscription_start = subscription_start;
    }

    public Integer getHistorical_data_offset()
    {
        return historical_data_offset;
    }

    public void setHistorical_data_offset(Integer historical_data_offset)
    {
        this.historical_data_offset = historical_data_offset;
    }

    public CustomerResponse getCustomer()
    {
        return customer;
    }

    public void setCustomer(CustomerResponse customer)
    {
        this.customer = customer;
    }

    public String getAccountLevel()
    {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel)
    {
        this.accountLevel = accountLevel;
    }

}
