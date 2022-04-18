package com.parrotanalytics.api.response.titles;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
        "pageinfo", "titles"
})
public class TitlesResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -4423087599867047409L;

    public TitlesResponse()
    {
        super(false, false);
    }

    public TitlesResponse(boolean errorEnabled, boolean warningEnabled)
    {
        super(errorEnabled, warningEnabled);
    }

    @JsonProperty("titles")
    private List<SubscriptionTitleResponse> titles;

    /**
     * @return the titles
     */
    public List<SubscriptionTitleResponse> getTitles()
    {
        return titles;
    }

    /**
     * @param titles
     *            the titles to set
     */
    public void setTitles(List<SubscriptionTitleResponse> titles)
    {
        this.titles = titles;
    }

}
