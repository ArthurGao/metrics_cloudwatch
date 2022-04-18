package com.parrotanalytics.api.request.season;

import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * 
 * @author Minh Vu
 *
 */
public class SeasonRequest extends APIRequest
{
    private String parrotID;

    private String seasonNum;

    public SeasonRequest()
    {

    }

    public SeasonRequest(String parrotID, String seasonNum)
    {
        this.parrotID = parrotID;
        this.seasonNum = seasonNum;
    }

    public String getParrotID()
    {
        return parrotID;
    }

    public void setParrotID(String parrotID)
    {
        this.parrotID = parrotID;
    }

    public String getSeasonNum()
    {
        return seasonNum;
    }

    public void setSeasonNum(String seasonNum)
    {
        this.seasonNum = seasonNum;
    }
}
