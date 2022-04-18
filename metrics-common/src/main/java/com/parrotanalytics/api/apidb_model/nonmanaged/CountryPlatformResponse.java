package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.io.Serializable;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(
{
        "country", "iso", "vod_platform", "display_name", "vod_type", "website", "alternate_website"
})
@JsonInclude(Include.NON_NULL)
public class CountryPlatformResponse implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4109485419154483060L;

    @JsonProperty("country")
    private String country;

    @Id
    @JsonProperty("iso")
    private String iso;

    @Id
    @JsonProperty("platform_id")
    private String platformID;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("platform_type")
    private String platformType;

    @JsonProperty("website")
    private String website;

    @JsonProperty("alternate_website")
    private String alternateWebsite;

    @JsonIgnore
    private int customer;

    @JsonIgnore
    private int active;

    public CountryPlatformResponse()
    {

    }

    public CountryPlatformResponse(String country, String iso, String platformID, String platform, String displayName,
            String platformType, String website, String alternateWebsite, int customer, int active)
    {
        super();
        this.country = country;
        this.iso = iso;
        this.platformID = platformID;
        this.platform = platform;
        this.displayName = displayName;
        this.platformType = platformType;
        this.website = website;
        this.alternateWebsite = alternateWebsite;
        this.customer = customer;
        this.active = active;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getIso()
    {
        return iso;
    }

    public void setIso(String iso)
    {
        this.iso = iso;
    }

    public String getPlatformID()
    {
        return platformID;
    }

    public void setPlatformID(String platformID)
    {
        this.platformID = platformID;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getPlatformType()
    {
        return platformType;
    }

    public void setPlatformType(String platformType)
    {
        this.platformType = platformType;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getAlternateWebsite()
    {
        return alternateWebsite;
    }

    public void setAlternateWebsite(String alternateWebsite)
    {
        this.alternateWebsite = alternateWebsite;
    }

    public int getCustomer()
    {
        return customer;
    }

    public void setCustomer(int customer)
    {
        this.customer = customer;
    }

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

}
