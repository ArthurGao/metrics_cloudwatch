package com.parrotanalytics.api.response.parrotratings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.commons.model.IDataPoint;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@XmlType(propOrder =
{
        "title", "parrot_id", "onboarded_on", "note", "datapoints"
})
@JsonPropertyOrder(
{
        "title", "parrot_id", "onboarded_on", "note", "datapoints"
})
@JsonInclude(Include.NON_NULL)
public class BaseDemandResponse implements Cloneable, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -5016348781950817503L;

    private String title;

    @JsonProperty("parrot_id")
    private String parrotId;

    @JsonProperty("onboarded_on")
    protected Date onboardedOn;

    @JsonProperty("note")
    protected String note;

    @JsonProperty("datapoints")
    private List<IDataPoint> datapoints = new ArrayList<IDataPoint>();

    /**
     * @return the id
     */

    /**
     * @return the parrotId
     */
    @XmlElement
    public String getParrotId()
    {
        return parrotId;
    }

    /**
     * @param parrotId
     *            the parrotId to set
     */
    @XmlElement
    public void setParrotId(String parrotId)
    {
        this.parrotId = parrotId;
    }

    /**
     * @return the titleName
     */
    @XmlElement
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the onboardedOn
     */
    public Date getOnboardedOn()
    {
        return onboardedOn;
    }

    /**
     * @param onboardedOn
     *            the onboardedOn to set
     */
    public void setOnboardedOn(Date onboardedOn)
    {
        this.onboardedOn = onboardedOn;
    }

    /**
     * @return the note
     */
    public String getNote()
    {
        return note;
    }

    /**
     * @param note
     *            the note to set
     */
    public void setNote(String note)
    {
        this.note = note;
    }

    /**
     * @return the datapoints
     */
    @XmlElementWrapper(name = "datapoints")
    @XmlElement(name = "datapoint")
    public List<IDataPoint> getDatapoints()
    {
        return datapoints;
    }

    /**
     * @param datapoints
     *            the datapoints to set
     */
    public void setDatapoints(List<IDataPoint> datapoints)
    {
        this.datapoints = datapoints;
    }

    /**
     * Adds an single datapoint to demandratings result
     * 
     * @param dP
     */
    public void addDatapoint(IDataPoint dP)
    {
        datapoints.add(dP);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((datapoints == null) ? 0 : datapoints.hashCode());
        result = prime * result + ((parrotId == null) ? 0 : parrotId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    public BaseDemandResponse clone()
    {
        BaseDemandResponse cloned = new BaseDemandResponse();
        cloned.setTitle(this.title);
        cloned.setParrotId(this.parrotId);
        cloned.setOnboardedOn(this.onboardedOn);
        cloned.setNote(this.note);

        return cloned;
    }
}
