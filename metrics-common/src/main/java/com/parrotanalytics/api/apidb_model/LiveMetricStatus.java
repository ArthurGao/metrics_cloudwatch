package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

@Entity
@Table(name = TableConstants.LIVE_METRICS_STATUS, schema = TableConstants.METRICS_SCHEMA)
public class LiveMetricStatus extends RDSBaseEntity
{

    private static final long serialVersionUID = 4299979354890974868L;

    @Column(name = "date")
    private Date date;

    @Id
    @Column(name = "short_id")
    private Long short_id;

    @Column(name = "adoption")
    private int adoption;

    @Column(name = "travelability")
    private int travelability;

    @Column(name = "season")
    private int season;

    @Column(name = "momentum")
    private int momentum;

    @Column(name = "longevity")
    private int longevity;

    @Column(name = "franchisability")
    private int franchisability;

    @Column(name = "fandom")
    private int fandom;

    @Column(name = "engagement")
    private int engagement;

    @Column(name = "availability")
    private int availability;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(Long short_id)
    {
        this.short_id = short_id;
    }

    public int getAdoption()
    {
        return adoption;
    }

    public void setAdoption(int adoption)
    {
        this.adoption = adoption;
    }

    public int getTravelability()
    {
        return travelability;
    }

    public void setTravelability(int travelability)
    {
        this.travelability = travelability;
    }

    public int getSeason()
    {
        return season;
    }

    public void setSeason(int season)
    {
        this.season = season;
    }

    public int getMomentum()
    {
        return momentum;
    }

    public void setMomentum(int momentum)
    {
        this.momentum = momentum;
    }

    public int getLongevity()
    {
        return longevity;
    }

    public void setLongevity(int longevity)
    {
        this.longevity = longevity;
    }

    public int getFranchisability()
    {
        return franchisability;
    }

    public void setFranchisability(int franchisability)
    {
        this.franchisability = franchisability;
    }

    public int getFandom()
    {
        return fandom;
    }

    public void setFandom(int fandom)
    {
        this.fandom = fandom;
    }

    public int getEngagement()
    {
        return engagement;
    }

    public void setEngagement(int engagement)
    {
        this.engagement = engagement;
    }

    public int getAvailability()
    {
        return availability;
    }

    public void setAvailability(int availability)
    {
        this.availability = availability;
    }
}
