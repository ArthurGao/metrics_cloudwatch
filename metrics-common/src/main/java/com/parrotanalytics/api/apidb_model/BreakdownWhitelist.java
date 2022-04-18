package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import com.rits.cloning.Cloner;

@Entity
@Table(name = "breakdown_whitelist", schema = "metrics")
@IdClass(value = BreakdownWhitelistPK.class)
public class BreakdownWhitelist extends RDSBaseEntity
{
    private static final long serialVersionUID = 8180481539050907025L;

    @Id
    @Column(name = "parrot_id")
    private String parrot_id;

    @Column(name = "short_id")
    private long short_id;

    @Id
    @Column(name = "country")
    private String country;

    @Column(name = "enabled")
    private Integer enabled;
    
    @Column(name="onboarded_on")
    private Date onboardedOn;

    public String getParrot_id()
    {
        return parrot_id;
    }

    public void setParrot_id(String parrot_id)
    {
        this.parrot_id = parrot_id;
    }

    public long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(long short_id)
    {
        this.short_id = short_id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Integer getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public BreakdownWhitelist clone()
    {
        return new Cloner().deepClone(this);
    }

    public Date getOnboardedOn()
    {
        return onboardedOn;
    }

    public void setOnboardedOn(Date onboardedOn)
    {
        this.onboardedOn = onboardedOn;
    }

}
