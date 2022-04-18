package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;

public class BreakdownWhitelistPK implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 6482394526502697251L;

    @Column(name = "parrot_id")
    private String parrot_id;

    @Column(name = "country")
    private String country;

    public BreakdownWhitelistPK()
    {
    }

    public String getParrot_id()
    {
        return parrot_id;
    }

    public void setParrot_id(String parrot_id)
    {
        this.parrot_id = parrot_id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((parrot_id == null) ? 0 : parrot_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BreakdownWhitelistPK other = (BreakdownWhitelistPK) obj;
        if (country == null)
        {
            if (other.country != null)
                return false;
        }
        else if (!country.equals(other.country))
            return false;
        if (parrot_id == null)
        {
            if (other.parrot_id != null)
                return false;
        }
        else if (!parrot_id.equals(other.parrot_id))
            return false;
        return true;
    }

}
