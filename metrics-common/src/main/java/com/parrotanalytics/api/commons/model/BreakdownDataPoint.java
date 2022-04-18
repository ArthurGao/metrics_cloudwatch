package com.parrotanalytics.api.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.parrotanalytics.api.commons.NoScientificNotationSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
        "parrot_id", "label", "social", "video", "research", "piracy", "onboarded_on"
})
@JsonInclude(Include.NON_NULL)
public class BreakdownDataPoint extends DataPoint
{
    private static final long serialVersionUID = 1600745368465079109L;

    @JsonProperty("social")
    @JsonSerialize(using = NoScientificNotationSerializer.class)
    protected Double social;

    @JsonProperty("video")
    @JsonSerialize(using = NoScientificNotationSerializer.class)
    protected Double video;

    @JsonProperty("research")
    @JsonSerialize(using = NoScientificNotationSerializer.class)
    protected Double research;

    @JsonProperty("piracy")
    @JsonSerialize(using = NoScientificNotationSerializer.class)
    protected Double piracy;

    public Double getSocial()
    {
        return social;
    }

    public void setSocial(Double social)
    {
        if (social == 0)
        {
            return;
        }
        this.social = social;
    }

    public Double getVideo()
    {
        return video;
    }

    public void setVideo(Double video)
    {
        if (video == 0)
        {
            return;
        }
        this.video = video;
    }

    public Double getResearch()
    {
        return research;
    }

    public void setResearch(Double research)
    {
        if (research == 0)
        {
            return;
        }
        this.research = research;
    }

    public Double getPiracy()
    {
        return piracy;
    }

    public void setPiracy(Double piracy)
    {
        if (piracy == 0)
        {
            return;
        }
        this.piracy = piracy;
    }

}
