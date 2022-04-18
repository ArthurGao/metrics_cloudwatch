package com.parrotanalytics.api.request.subscriptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class SubscriptionsRequest.
 *
 * @author Sanjeev Sharma
 */
public class SubscriptionsRequest extends APIRequest
{

    /** The request account id. */
    private String requestAccountId;

    /** The subscriptions type. */
    private String subscriptionsType;

    /** The subscription id. */
    private Integer subscriptionId;

    /** The subscription action. */
    private OperationType subscriptionAction;

    /** The market id. */
    private Integer marketId;

    /** The title id. */
    private Long titleId;

    /** The module id. */
    private Integer moduleId;

    /** The id. */
    private Integer id;

    /** The description. */
    private String description;

    /** The dashboard. */
    private boolean dashboard;

    /** The dashboard type. */
    private String dashboardType;

    /** The title ids. */
    private List<Long> titleIds;

    /** The title fields to return in response */
    private List<String> titleFields;

    /** The product module. */
    @JsonProperty("account_module")
    private ModuleRequest productModule;

    private boolean internalMode;

    /**
     * Instantiates a new subscriptions request.
     */
    public SubscriptionsRequest()
    {
    };

    /**
     * Instantiates a new subscriptions request.
     *
     * @param requestAccountId
     *            the request account id
     */
    public SubscriptionsRequest(String requestAccountId)
    {
        this.requestAccountId = requestAccountId;
    };

    /**
     * Gets the market id.
     *
     * @return the market id
     */
    public Integer getMarketId()
    {
        return marketId;
    }

    /**
     * Sets the market id.
     *
     * @param marketId
     *            the new market id
     */
    public void setMarketId(Integer marketId)
    {
        this.marketId = marketId;
    }

    /**
     * Gets the title id.
     *
     * @return the title id
     */
    public Long getTitleId()
    {
        return titleId;
    }

    /**
     * Sets the title id.
     *
     * @param titleId
     *            the new title id
     */
    public void setTitleId(Long titleId)
    {
        this.titleId = titleId;
    }

    /**
     * Gets the module id.
     *
     * @return the module id
     */
    public Integer getModuleId()
    {
        return moduleId;
    }

    /**
     * Sets the module id.
     *
     * @param moduleId
     *            the new module id
     */
    public void setModuleId(Integer moduleId)
    {
        this.moduleId = moduleId;
    }

    /**
     * Gets the subscription action.
     *
     * @return the subscription action
     */
    public OperationType getSubscriptionAction()
    {
        return subscriptionAction;
    }

    /**
     * Sets the subscription action.
     *
     * @param subscriptionAction
     *            the new subscription action
     */
    public void setSubscriptionAction(OperationType subscriptionAction)
    {
        this.subscriptionAction = subscriptionAction;
    }

    /**
     * Gets the subscription id.
     *
     * @return the subscription id
     */
    public Integer getSubscriptionId()
    {
        return subscriptionId;
    }

    /**
     * Sets the subscription id.
     *
     * @param subscriptionId
     *            the new subscription id
     */
    public void setSubscriptionId(Integer subscriptionId)
    {
        this.subscriptionId = subscriptionId;
    }

    /**
     * Gets the request account id.
     *
     * @return the requestAccountId
     */
    public String getRequestAccountId()
    {
        return requestAccountId;
    }

    /**
     * Sets the request account id.
     *
     * @param requestAccountId
     *            the requestAccountId to set
     */
    public void setRequestAccountId(String requestAccountId)
    {
        this.requestAccountId = requestAccountId;
    }

    /**
     * Gets the api user id.
     *
     * @return the apiUser
     */
    public String getApiUserId()
    {
        return apiUserId;
    }

    /**
     * Sets the api user id.
     *
     * @param apiUser
     *            the apiUser to set
     */
    public void setApiUserId(String apiUser)
    {
        this.apiUserId = apiUser;
    }

    /**
     * Gets the api account id.
     *
     * @return the accountId
     */
    public Integer getApiAccountId()
    {
        return apiAccountId;
    }

    /**
     * Sets the api account id.
     *
     * @param accountId
     *            the accountId to set
     */
    public void setApiAccountId(Integer accountId)
    {
        this.apiAccountId = accountId;
    }

    /**
     * Gets the subscriptions type.
     *
     * @return the subscriptionsType
     */
    public String getSubscriptionsType()
    {
        return subscriptionsType;
    }

    /**
     * Sets the subscriptions type.
     *
     * @param subscriptionsType
     *            the subscriptionsType to set
     */
    public void setSubscriptionsType(String subscriptionsType)
    {
        this.subscriptionsType = subscriptionsType;
    }

    public List<String> getTitleFields()
    {
        return titleFields;
    }

    public void setTitleFields(List<String> titleFields)
    {
        this.titleFields = titleFields;
    }

    /**
     * Gets the product module.
     *
     * @return the product module
     */
    public ModuleRequest getProductModule()
    {
        return productModule;
    }

    /**
     * Sets the product module.
     *
     * @param productModule
     *            the new product module
     */
    public void setProductModule(ModuleRequest productModule)
    {
        this.productModule = productModule;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Checks if is dashboard.
     *
     * @return true, if is dashboard
     */
    public boolean isDashboard()
    {
        return dashboard;
    }

    /**
     * Sets the dashboard.
     *
     * @param dashboard
     *            the new dashboard
     */
    public void setDashboard(boolean dashboard)
    {
        this.dashboard = dashboard;
    }

    /**
     * Gets the dashboard type.
     *
     * @return the dashboard type
     */
    public String getDashboardType()
    {
        return dashboardType;
    }

    /**
     * Sets the dashboard type.
     *
     * @param dashboardType
     *            the new dashboard type
     */
    public void setDashboardType(String dashboardType)
    {
        this.dashboardType = dashboardType;
    }

    /**
     * Gets the title ids.
     *
     * @return the title ids
     */
    public List<Long> getTitleIds()
    {
        return titleIds;
    }

    /**
     * Sets the title ids.
     *
     * @param titleIds
     *            the new title ids
     */
    public void setTitleIds(List<Long> titleIds)
    {
        this.titleIds = titleIds;
    }

    public boolean isInternalMode()
    {
        return internalMode;
    }

    public void setInternalMode(boolean internalMode)
    {
        this.internalMode = internalMode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((apiAccountId == null) ? 0 : apiAccountId.hashCode());
        result = prime * result + ((apiUserId == null) ? 0 : apiUserId.hashCode());
        result = prime * result + ((subscriptionsType == null) ? 0 : subscriptionsType.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubscriptionsRequest other = (SubscriptionsRequest) obj;
        if (apiAccountId == null)
        {
            if (other.apiAccountId != null)
                return false;
        }
        else if (!apiAccountId.equals(other.apiAccountId))
            return false;
        if (apiUserId == null)
        {
            if (other.apiUserId != null)
                return false;
        }
        else if (!apiUserId.equals(other.apiUserId))
            return false;
        if (subscriptionsType == null)
        {
            if (other.subscriptionsType != null)
                return false;
        }
        else if (!subscriptionsType.equals(other.subscriptionsType))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "SubscriptionsRequest [apiUser=" + apiUserId + ", accountId=" + apiAccountId + ", subscriptionsType="
                + subscriptionsType + "]";
    }

}
