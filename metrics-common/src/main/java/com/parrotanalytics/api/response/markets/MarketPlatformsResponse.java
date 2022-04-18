package com.parrotanalytics.api.response.markets;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedPlatformInfo;
import com.parrotanalytics.apicore.model.response.APIResponse;

@JsonInclude(Include.NON_NULL)
public class MarketPlatformsResponse extends APIResponse
{

    public MarketPlatformsResponse()
    {

    }

    private static final long serialVersionUID = 6633203241341689779L;

    @JsonProperty("country_platforms")
    private List<ExtendedPlatformInfo> countryPlatforms;

    public List<ExtendedPlatformInfo> getCountryPlatforms()
    {
        return countryPlatforms;
    }

    public void setCountryPlatforms(List<ExtendedPlatformInfo> countryPlatforms)
    {
        this.countryPlatforms = countryPlatforms;
    }
}
