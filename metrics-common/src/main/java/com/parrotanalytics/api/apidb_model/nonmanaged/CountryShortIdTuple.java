package com.parrotanalytics.api.apidb_model.nonmanaged;

public class CountryShortIdTuple
{
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((shortId == null) ? 0 : shortId.hashCode());
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
        CountryShortIdTuple other = (CountryShortIdTuple) obj;
        if (country == null)
        {
            if (other.country != null)
                return false;
        }
        else if (!country.equals(other.country))
            return false;
        if (shortId == null)
        {
            if (other.shortId != null)
                return false;
        }
        else if (!shortId.equals(other.shortId))
            return false;
        return true;
    }

    public String country;

    public Long shortId;

    public CountryShortIdTuple(String country, Long shortId)
    {
        this.country = country;
        this.shortId = shortId;
    }
}
