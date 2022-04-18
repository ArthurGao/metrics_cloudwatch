package com.parrotanalytics.api.apidb_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * This is the new portal.country_platforms table which is used for TV360 Platform
 * see CountryPlatform class which is entity class for OLD /vodplatforms v2 Portal
 */
@Entity
@Table(name = "country_platforms", schema = "portal")
public class CountryPlatforms implements Serializable
{

    private static final long serialVersionUID = -1428191939218696501L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @Column(name = "country")
    @JsonProperty("country")
    private String country;

    @Column(name = "customer_ready")
    @JsonProperty("customer_ready")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean customer_ready;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CountryPlatforms that = (CountryPlatforms) o;
        return Objects.equals(id, that.id) && Objects.equals(country, that.country) && Objects.equals(iso, that.iso)
                && Objects.equals(platform_id, that.platform_id) && Objects.equals(platform, that.platform);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, country, iso, platform_id, platform);
    }

    @Column(name = "iso")
    @JsonProperty("iso")
    private String iso;

    @Column(name = "platform_id")
    @JsonProperty("platform_id")
    private String platform_id;

    @Column(name = "platform")
    @JsonProperty("platform")
    private String platform;

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

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Boolean getCustomer_ready()
    {
        return customer_ready;
    }

    public void setCustomer_ready(Boolean customer_ready)
    {
        this.customer_ready = customer_ready;
    }
}
