package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "tvplatforms", schema = "subscription")
@NamedQuery(name = "TVPlatform.findAll", query = "SELECT p FROM TVPlatform p")

@JsonPropertyOrder(
{
        "platform_id", "platform", "display_name", "website", "alternate_website"
})
@JsonInclude(Include.NON_NULL)

public class TVPlatform implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4109485419154483060L;

    @Id
    @JsonProperty("platform_id")
    private String platform_id;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("display_name")
    private String display_name;

    @JsonProperty("platform_type")
    private String platform_type;

    @JsonProperty("website")
    private String website;

    @JsonProperty("alternate_website")
    private String alternate_website;

    @JsonIgnore
    private int customer;

    @JsonIgnore
    private int active;

    @OneToMany(mappedBy = "platform")
    private List<CountryPlatform> countryPlatforms;

    public String getPlatform_id()
    {
        return platform_id;
    }

    public void setPlatform_id(String platform_id)
    {
        this.platform_id = platform_id;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public String getPlatform_type()
    {
        return platform_type;
    }

    public void setPlatform_type(String platform_type)
    {
        this.platform_type = platform_type;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getAlternate_website()
    {
        return alternate_website;
    }

    public void setAlternate_website(String alternate_website)
    {
        this.alternate_website = alternate_website;
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
