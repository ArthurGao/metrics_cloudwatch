package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.nonmanaged.AccessibleTier;
import com.parrotanalytics.apicore.config.APIConstants;

/**
 * The persistent class for the Countries database table.
 * 
 */
@Entity
@Table(name = "countries", schema = "subscription")
@NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
public class Country implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private Integer idCountries;

    private String iso;

    private String name;

    private String displayName;

    private String iso3;

    private short numCode;

    private int phoneCode;

    private String region;

    private String sub_region;

    private long population;

    private long internet_users;

    private double internet_penetration;

    private double piracy_rate;

    private short active;

    @Enumerated(EnumType.STRING)
    @Column(name = "accessibleTier")
    @JsonProperty("accessibleTier")
    private AccessibleTier accessibleTier;

    public Country(Integer idCountry, String iso, String name, String iso3)
    {
        this.idCountries = idCountry;
        this.iso = iso;
        this.name = name;
        this.iso3 = iso3;
    }

    /**
     * Instantiates a new country.
     */
    public Country()
    {
    }

    /**
     * @return the idCountries
     */
    public Integer getIdCountries()
    {
        return idCountries;
    }

    /**
     * @param idCountries
     *            the idCountries to set
     */
    public void setIdCountries(Integer idCountries)
    {
        this.idCountries = idCountries;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName
     *            the new display name
     */
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * Gets the iso.
     *
     * @return the iso
     */
    public String getIso()
    {
        return this.iso;
    }

    /**
     * Sets the iso.
     *
     * @param iso
     *            the new iso
     */
    public void setIso(String iso)
    {
        this.iso = iso;
    }

    /**
     * Gets the iso3.
     *
     * @return the iso3
     */
    public String getIso3()
    {
        return this.iso3;
    }

    /**
     * Sets the iso3.
     *
     * @param iso3
     *            the new iso3
     */
    public void setIso3(String iso3)
    {
        this.iso3 = iso3;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the num code.
     *
     * @return the num code
     */
    public short getNumCode()
    {
        return this.numCode;
    }

    /**
     * Sets the num code.
     *
     * @param numCode
     *            the new num code
     */
    public void setNumCode(short numCode)
    {
        this.numCode = numCode;
    }

    /**
     * Gets the phone code.
     *
     * @return the phone code
     */
    public int getPhoneCode()
    {
        return this.phoneCode;
    }

    /**
     * Sets the phone code.
     *
     * @param phoneCode
     *            the new phone code
     */
    public void setPhoneCode(int phoneCode)
    {
        this.phoneCode = phoneCode;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getSub_region()
    {
        return sub_region;
    }

    public void setSub_region(String sub_region)
    {
        this.sub_region = sub_region;
    }

    public long getPopulation()
    {
        return population;
    }

    public void setPopulation(long population)
    {
        this.population = population;
    }

    public long getInternet_users()
    {
        return internet_users;
    }

    public void setInternet_users(long internet_users)
    {
        this.internet_users = internet_users;
    }

    public double getInternet_penetration()
    {
        return internet_penetration;
    }

    public void setInternet_penetration(double internet_penetration)
    {
        this.internet_penetration = internet_penetration;
    }

    public double getPiracy_rate()
    {
        return piracy_rate;
    }

    public void setPiracy_rate(double piracy_rate)
    {
        this.piracy_rate = piracy_rate;
    }

    public short getActive()
    {
        return active;
    }

    public void setActive(short active)
    {
        this.active = active;
    }

    public AccessibleTier getAccessibleTier()
    {
        return accessibleTier;
    }

    public void setAccessibleTier(AccessibleTier accessibleTier)
    {
        this.accessibleTier = accessibleTier;
    }

    @Transient()
    public static Country worlwide()
    {
        return new Country(-1, APIConstants.WORLDWIDE_CODE, "Worldwide", APIConstants.WORLDWIDE_CODE);
    }
}