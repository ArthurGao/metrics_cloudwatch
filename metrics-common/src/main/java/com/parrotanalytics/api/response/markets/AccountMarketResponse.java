package com.parrotanalytics.api.response.markets;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.CountryPlatform;
import com.parrotanalytics.api.apidb_model.nonmanaged.AccessibleTier;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonInclude(Include.NON_NULL)
public class AccountMarketResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String iso;

    private String iso3;

    private String displayName;

    private String region;

    private String subRegion;

    private Boolean homeMarket;

    private Boolean preferredMarket;

    private short status;

    @JsonProperty("subscription_start")
    private String subscriptionStart;

    private Long population;

    private Long internetUsers;

    private Double internetPenetration;

    private Double piracyRate;

    private Short active;

    @JsonProperty("accessible_tier")
    private AccessibleTier accessibleTier;

    @JsonProperty("vod_platforms")
    private List<CountryPlatform> vodPlatforms;

    @JsonProperty("is_subscribed")
    private Boolean isSubscribed;

    @JsonProperty("is_monitor")
    private Boolean isMonitor;

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public AccountMarketResponse withId(Integer id)
    {
        this.id = id;
        return this;
    }

    /**
     * @return the iso
     */
    public String getIso()
    {
        return iso;
    }

    /**
     * @param iso
     *            the iso to set
     */
    public AccountMarketResponse withIso(String iso)
    {
        this.iso = iso;
        return this;
    }

    /**
     * @return the iso3
     */
    public String getIso3()
    {
        return iso3;
    }

    /**
     * @param iso3
     *            the iso3 to set
     */
    public AccountMarketResponse withIso3(String iso3)
    {
        this.iso3 = iso3;
        return this;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public AccountMarketResponse withDisplayName(String displayName)
    {
        this.displayName = displayName;
        return this;
    }

    public String getRegion()
    {
        return region;
    }

    public AccountMarketResponse withRegion(String region)
    {
        this.region = region;
        return this;
    }

    public AccountMarketResponse withAccessibleTier(AccessibleTier accessibleTier)
    {
        this.accessibleTier = accessibleTier;
        return this;
    }

    public String getSubRegion()
    {
        return subRegion;
    }

    public AccountMarketResponse withSubRegion(String subRegion)
    {
        this.subRegion = subRegion;
        return this;
    }

    public Boolean getHomeMarket()
    {
        return homeMarket;
    }

    /**
     * @return the status
     */
    public short getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public AccountMarketResponse withStatus(short status)
    {
        this.status = status;
        return this;
    }

    /**
     * @return the homeMarket
     */
    public Boolean isHomeMarket()
    {
        return homeMarket;
    }

    /**
     * @param homeMarket
     *            the homeMarket to set
     */
    public AccountMarketResponse withHomeMarket(Boolean homeMarket)
    {
        this.homeMarket = homeMarket;
        return this;
    }

    /**
     * @return the preferredMarket
     */
    public Boolean getPreferredMarket()
    {
        return preferredMarket;
    }

    /**
     * @param preferredMarket
     *            the preferredMarket to set
     */
    public AccountMarketResponse withPreferredMarket(Boolean preferredMarket)
    {
        this.preferredMarket = preferredMarket;
        return this;
    }

    public String getSubscriptionStart()
    {
        return subscriptionStart;
    }

    public void withSubscriptionStart(String subscriptionStart)
    {
        this.subscriptionStart = subscriptionStart;
    }

    public Long getPopulation()
    {
        return population;
    }

    public AccountMarketResponse withPopulation(Long population)
    {
        this.population = population;
        return this;

    }

    public Long getInternetUsers()
    {
        return internetUsers;
    }

    public AccountMarketResponse withInternetUsers(Long internetUsers)
    {
        this.internetUsers = internetUsers;
        return this;

    }

    public Double getInternetPenetration()
    {
        return internetPenetration;
    }

    public AccountMarketResponse withInternetPenetration(Double internetPenetration)
    {
        this.internetPenetration = internetPenetration;
        return this;

    }

    public Double getPiracyRate()
    {
        return piracyRate;
    }

    public AccountMarketResponse withPiracyRate(Double piracyRate)
    {
        this.piracyRate = piracyRate;
        return this;

    }

    public Short getActive()
    {
        return active;
    }

    public AccountMarketResponse withActive(Short active)
    {
        this.active = active;
        return this;

    }

    public List<CountryPlatform> getVodPlatforms()
    {
        return vodPlatforms;
    }

    public void setVodPlatforms(List<CountryPlatform> vodPlatforms)
    {
        this.vodPlatforms = vodPlatforms;
    }

    public AccessibleTier getAccessibleTier()
    {
        return accessibleTier;
    }

    public void setAccessibleTier(AccessibleTier accessibleTier)
    {
        this.accessibleTier = accessibleTier;
    }

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

}
