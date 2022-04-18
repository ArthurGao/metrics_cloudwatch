package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import com.rits.cloning.Cloner;

@Entity
@Table(name = "breakdown_daily_four_bucket", schema = "metrics")
@IdClass(value = BreakdownDailyPK.class)
public class BreakdownDaily extends RDSBaseEntity
{
    private static final long serialVersionUID = 8180481539050907025L;

    @Id
    @Column(name = "country")
    private String country;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "de_piracy")
    private double de_piracy;

    @Column(name = "de_research")
    private double de_research;

    @Column(name = "de_social")
    private double de_social;

    @Column(name = "de_video")
    private double de_video;

    @Id
    @Column(name = "short_id")
    private long short_id;

    @Override
    public BreakdownDaily clone()
    {
        return new Cloner().deepClone(this);
    }

    public String getCountry()
    {
        return country;
    }

    public Date getDate()
    {
        return date;
    }

    public double getDe_piracy()
    {
        return de_piracy;
    }

    public double getDe_research()
    {
        return de_research;
    }

    public double getDe_social()
    {
        return de_social;
    }

    public double getDe_video()
    {
        return de_video;
    }

    public long getShort_id()
    {
        return short_id;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setDe_piracy(double de_piracy)
    {
        this.de_piracy = de_piracy;
    }

    public void setDe_research(double de_research)
    {
        this.de_research = de_research;
    }

    public void setDe_social(double de_social)
    {
        this.de_social = de_social;
    }

    public void setDe_video(double de_video)
    {
        this.de_video = de_video;
    }

    public void setShort_id(long short_id)
    {
        this.short_id = short_id;
    }

}
