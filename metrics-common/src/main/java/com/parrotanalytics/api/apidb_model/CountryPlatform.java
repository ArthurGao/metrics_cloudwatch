package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.JoinFetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "country_platforms", schema = "subscription")
@NamedQuery(name = "CountryPlatform.findAll", query = "SELECT cp FROM CountryPlatform cp")
public class CountryPlatform implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4109485419154483060L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country")
    private String country;

    @Column(name = "iso")
    private String iso;

    @Column(name = "platform_id")
    private String platform_id;

    @JsonIgnore
    private int active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "platform_id", insertable = false, updatable = false)
    private TVPlatform platform;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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

    public String getPlatform_id()
    {
        return platform_id;
    }

    public void setPlatform_id(String platform_id)
    {
        this.platform_id = platform_id;
    }

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

    public TVPlatform getPlatform()
    {
        return platform;
    }

    public void setPlatform(TVPlatform platform)
    {
        this.platform = platform;
    }

}
