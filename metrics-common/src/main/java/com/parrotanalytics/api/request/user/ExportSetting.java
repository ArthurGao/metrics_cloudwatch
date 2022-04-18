package com.parrotanalytics.api.request.user;

import java.util.List;

import com.parrotanalytics.api.apidb_model.UserSetting;
import com.parrotanalytics.apicore.model.request.ISetting;

/**
 * model class used for storing JSONified settings value for users 'export'
 * settings in {@link UserSetting} table
 * 
 * @author Sanjeev Sharma
 *
 */
public class ExportSetting implements ISetting
{
    private String ratingType;;

    private List<String> markets;

    private List<String> parrotIDs;

    private String dateQuery;

    protected String dateFrom;

    protected String dateTo;

    /**
     * @return the ratingType
     */
    public String getRatingType()
    {
        return ratingType;
    }

    /**
     * @param ratingType
     *            the ratingType to set
     */
    public void setRatingType(String ratingType)
    {
        this.ratingType = ratingType;
    }

    /**
     * @return the markets
     */
    public List<String> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets
     *            the markets to set
     */
    public void setMarkets(List<String> markets)
    {
        this.markets = markets;
    }

    /**
     * @return the parrotIDs
     */
    public List<String> getParrotIDs()
    {
        return parrotIDs;
    }

    /**
     * @param parrotIDs
     *            the parrotIDs to set
     */
    public void setParrotIDs(List<String> parrotIDs)
    {
        this.parrotIDs = parrotIDs;
    }

    /**
     * @return the dateRange
     */
    public String getDateQuery()
    {
        return dateQuery;
    }

    /**
     * @param dateQuery
     *            the dateQuery to set
     */
    public void setDateQuery(String dateRange)
    {
        this.dateQuery = dateRange;
    }

    /**
     * @return the dateFrom
     */
    public String getDateFrom()
    {
        return dateFrom;
    }

    /**
     * @param dateFrom
     *            the dateFrom to set
     */
    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public String getDateTo()
    {
        return dateTo;
    }

    /**
     * @param dateTo
     *            the dateTo to set
     */
    public void setDateTo(String dateTo)
    {
        this.dateTo = dateTo;
    }

}
