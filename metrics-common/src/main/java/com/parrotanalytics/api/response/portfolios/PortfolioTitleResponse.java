package com.parrotanalytics.api.response.portfolios;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class PortfolioTitleResponse is the part of the title response required
 * to return a Portfolio item.
 * 
 * @author Chris
 */
@JsonPropertyOrder(
{
        "title", "parrot_id"
})
public class PortfolioTitleResponse
{
    @JsonProperty("parrot_id")
    private String parrotId;

    private String title;

    public PortfolioTitleResponse(String parrotId, String title)
    {
        this.parrotId = parrotId;
        this.title = title;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the parrotId
     */
    public String getParrotId()
    {
        return parrotId;
    }

    /**
     * @param parrotId
     *            the parrotId to set
     */
    public void setParrotId(String parrotId)
    {
        this.parrotId = parrotId;
    }
}
