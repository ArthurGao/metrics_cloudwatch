package com.parrotanalytics.api.response.subscriptions;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonInclude(Include.NON_NULL)
public class ModuleResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("subscription_id")
    private Integer subscriptionId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product")
    private ProductResponse productOffering;

    @JsonProperty("configs")
    private List<ProductSpecResponse> productConfigs;

    @JsonProperty("subscription_start")
    private String subscriptionStart;

    public ModuleResponse()
    {

    }

    public ModuleResponse(Integer accountId, Integer subscriptionId, ProductResponse productOffering,
            String productName, List<ProductSpecResponse> productConfigs)
    {
        super();
        this.accountId = accountId;
        this.subscriptionId = subscriptionId;
        this.productOffering = productOffering;
        this.productName = productName;
        this.productConfigs = productConfigs;
    }

    public Integer getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Integer accountId)
    {
        this.accountId = accountId;
    }

    public ProductResponse getProductOffering()
    {
        return productOffering;
    }

    public void setProductOffering(ProductResponse productOffering)
    {
        this.productOffering = productOffering;
    }

    public List<ProductSpecResponse> getProductConfigs()
    {
        return productConfigs;
    }

    public void setProductConfigs(List<ProductSpecResponse> productOfferingSpec)
    {
        this.productConfigs = productOfferingSpec;
    }

    public void addProductConfig(ProductSpecResponse config)
    {
        if (this.productConfigs == null)
        {
            this.productConfigs = new ArrayList<ProductSpecResponse>();
        }
        this.productConfigs.add(config);
    }

    public Integer getSubscriptionId()
    {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId)
    {
        this.subscriptionId = subscriptionId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getSubscriptionStart()
    {
        return subscriptionStart;
    }

    public void setSubscriptionStart(String subscriptionStart)
    {
        this.subscriptionStart = subscriptionStart;
    }

}
