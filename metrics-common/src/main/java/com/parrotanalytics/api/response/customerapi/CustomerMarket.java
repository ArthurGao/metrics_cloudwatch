package com.parrotanalytics.api.response.customerapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.nonmanaged.AccessibleTier;
import com.parrotanalytics.apicore.model.response.APIResponse;

@JsonPropertyOrder(
{
        "id", "country_iso", "country_name", "region", "sub_region"
})
@JsonInclude(Include.NON_NULL)
public class CustomerMarket extends APIResponse
{
    private static final long serialVersionUID = 320597600085796355L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("country_iso")
    private String country_iso;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("region")
    private String region;

    @JsonProperty("sub_region")
    private String subRegion;

    @JsonProperty("accessible_tier")
    private AccessibleTier accessibleTier;

    @JsonProperty("is_subscribed")
    private Boolean isSubscribed;

    @JsonProperty("is_monitor")
    private Boolean isMonitor;

    public Boolean getIsSubscribed()
    {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed)
    {
        this.isSubscribed = isSubscribed;
    }

    public Boolean getIsMonitor()
    {
        return isMonitor;
    }

    public void setIsMonitor(Boolean isMonitor)
    {
        this.isMonitor = isMonitor;
    }

    public String getCountry_iso()
    {
        return country_iso;
    }

    public CustomerMarket withCountry_iso(String country_iso)
    {
        this.country_iso = country_iso;
        return this;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public CustomerMarket withAccessibleTier(AccessibleTier accessibleTier)
    {
        this.accessibleTier = accessibleTier;
        return this;
    }

    public CustomerMarket withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public CustomerMarket withCountryName(String countryName)
    {
        this.countryName = countryName;
        return this;
    }

    public String getRegion()
    {
        return region;
    }

    public CustomerMarket withRegion(String region)
    {
        this.region = region;
        return this;
    }

    public String getSubRegion()
    {
        return subRegion;
    }

    public CustomerMarket withSubRegion(String subRegion)
    {
        this.subRegion = subRegion;
        return this;
    }

    public AccessibleTier getAccessibleTier()
    {
        return accessibleTier;
    }

    public void setAccessibleTier(AccessibleTier accessibleTier)
    {
        this.accessibleTier = accessibleTier;
    }

    public CustomerMarket withIsMonitor(Boolean isMonitor)
    {
        this.isMonitor = isMonitor;
        return this;
    }

    public CustomerMarket withIsSubscribed(Boolean isSubscribed)
    {
        this.isSubscribed = isSubscribed;
        return this;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

}
