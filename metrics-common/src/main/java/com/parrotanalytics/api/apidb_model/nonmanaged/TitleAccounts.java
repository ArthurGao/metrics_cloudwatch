package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.math3.util.Precision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.datasourceint.dsdb.model.base.GroupedDataEntity;

/**
 * Bean for holding average ratings records from JPQL constructor based queries.
 * This should be used when {@link ExpressionsDaily} cannot be used for
 * mapping JPA/JPQL queries results directly.
 * 
 * 
 * @author Sanjeev Sharma
 *
 */
public class TitleAccounts extends GroupedDataEntity implements Serializable
{
    private static final long serialVersionUID = -3976869290812933554L;

    @JsonIgnore
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @JsonIgnore
    private static final int ROUNDING_DP = 2;

    private String title;

    private String date;

    private String country;

    @JsonProperty("pdr")
    private double PDR;

    @JsonProperty("pde")
    private double PDE;

    /**
     * constructor for portfolio titles average for date range by market
     * results.
     *
     * @param country
     *            the country
     * @param PDE
     *            the pde
     * @param PDR
     *            the pdr
     */
    public TitleAccounts(String country, Double PDE, Double PDR)
    {
        this.country = country;
        this.PDR = Precision.round(PDR, ROUNDING_DP);
        this.PDE = Precision.round(PDE, ROUNDING_DP);
    }

    /**
     * constructor for portfolio titles daily average by market results.
     *
     * @param country
     *            the country
     * @param date
     *            the date
     * @param PDE
     *            the pde
     * @param PDR
     *            the pdr
     */
    public TitleAccounts(String country, Date date, Double PDE, Double PDR)
    {
        this.country = country;
        this.date = dateFormat.format(date);
        this.PDR = Precision.round(PDR, ROUNDING_DP);
        this.PDE = Precision.round(PDE, ROUNDING_DP);
    }

    /**
     * Instantiates a new grouped rating.
     *
     * @param parrotID
     *            the parrot id
     * @param pa_content_title
     *            the pa_content_title
     * @param PDE
     *            the pde
     * @param PDR
     *            the pdr
     */
    public TitleAccounts(String parrotID, String pa_content_title, Double PDE, Double PDR)
    {
        this.cid = parrotID;
        this.title = pa_content_title;
        this.PDR = Precision.round(PDR, ROUNDING_DP);
        this.PDE = Precision.round(PDE, ROUNDING_DP);
    }

    /**
     * Instantiates a new grouped rating.
     *
     * @param country
     *            the country
     * @param parrotID
     *            the parrot id
     * @param title
     *            the title
     * @param PDE
     *            the pde
     * @param PDR
     *            the pdr
     */
    public TitleAccounts(String country, String parrotID, String title, Double PDE, Double PDR)
    {
        this.cid = parrotID;
        this.title = title;
        this.country = country;
        this.PDR = Precision.round(PDR, ROUNDING_DP);
        this.PDE = Precision.round(PDE, ROUNDING_DP);
    }

    /**
     * Instantiates a new portfolio stats, grouped by content ID.
     *
     * @param date
     *            the date
     * @param parrotID
     *            the pa_content_id
     * @param PDE
     *            the pde
     * @param PDR
     *            the pdr
     */
    public TitleAccounts(Date date, String parrotID, Double PDE, Double PDR)
    {
        this.date = dateFormat.format(date);
        this.cid = parrotID;
        this.PDR = Precision.round(PDR, ROUNDING_DP);
        this.PDE = Precision.round(PDE, ROUNDING_DP);
    }

    /**
     * Instantiates a new portfolio stats.
     *
     * @param date
     *            the date
     * @param PDE
     *            the pde
     * @param PDR
     *            the pdr
     */
    public TitleAccounts(Date date, Double PDE, Double PDR)
    {
        this(date, null, PDE, PDR);
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country
     *            the country to set
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * Gets the pa_content_id.
     *
     * @return the pa_content_id
     */
    public String getParrotID()
    {
        return cid;
    }

    /**
     * Gets the title.
     *
     * @return the pa_content_title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the pdr.
     *
     * @return the pdr
     */
    public double getPDR()
    {
        return PDR;
    }

    /**
     * Gets the pde.
     *
     * @return the pde
     */
    public double getPDE()
    {
        return PDE;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public String getDate()
    {
        return date;
    }
}
