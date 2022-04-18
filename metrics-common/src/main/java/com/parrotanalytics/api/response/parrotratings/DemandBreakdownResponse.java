package com.parrotanalytics.api.response.parrotratings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

public class DemandBreakdownResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 4221161504862634973L;

    public DemandBreakdownResponse()
    {

    }

    public List<String> getParrotIds()
    {
        return parrotIds;
    }

    public void setParrotIds(List<String> parrotIds)
    {
        this.parrotIds = parrotIds;
    }

    public void addParrotIds(List<String> itemsToAdd)
    {
        if (parrotIds == null)
        {
            parrotIds = new ArrayList<String>();
        }
        parrotIds.addAll(itemsToAdd);
    }

    public List<String> getMarkets()
    {
        return markets;
    }

    public void setMarkets(List<String> markets)
    {
        this.markets = markets;
    }

    public Map<String, String> getOnboardedOn()
    {
        return onboardedOn;
    }

    public void setOnboardedOn(Map<String, String> onboardedOn)
    {
        this.onboardedOn = onboardedOn;
    }

    @JsonProperty("parrot_ids")
    private List<String> parrotIds;

    @JsonProperty("markets")
    private List<String> markets;

    @JsonProperty("onboarded_on")
    private Map<String, String> onboardedOn = new HashMap<String, String>();

}
