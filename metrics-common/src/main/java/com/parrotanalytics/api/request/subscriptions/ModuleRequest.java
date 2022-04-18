package com.parrotanalytics.api.request.subscriptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.request.APIRequest;

public class ModuleRequest extends APIRequest
{
    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("subscription_id")
    private Integer subscriptionId;

    @JsonProperty("product")
    private Object project;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("configs")
    private List<ProductSpecRequest> productOfferingSpec;

    @JsonProperty("titles")
    private List<String> titles;

    private Integer id;

    private String description;

    public ModuleRequest(String parrotId)
    {

    }

    public ModuleRequest()
    {
    };

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Integer accountId)
    {
        this.accountId = accountId;
    }

    public Integer getSubscriptionId()
    {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId)
    {
        this.subscriptionId = subscriptionId;
    }

    public Object getProject()
    {
        return project;
    }

    public void setProject(Object project)
    {
        this.project = project;
    }

    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    public List<ProductSpecRequest> getProductOfferingSpec()
    {
        return productOfferingSpec;
    }

    public void setProductOfferingSpec(List<ProductSpecRequest> productOfferingSpec)
    {
        this.productOfferingSpec = productOfferingSpec;
    }

    public List<String> getTitles()
    {
        return titles;
    }

    public void setTitles(List<String> titles)
    {
        this.titles = titles;
    }

}
