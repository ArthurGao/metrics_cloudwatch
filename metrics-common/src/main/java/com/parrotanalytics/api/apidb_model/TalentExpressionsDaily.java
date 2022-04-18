package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import com.rits.cloning.Cloner;

@Entity
@Table(name = TableConstants.TALENT_EXPRESSIONS_DAILY, schema = TableConstants.METRICS_SCHEMA)
@IdClass(value = ExpressionsDailyPK.class)
public class TalentExpressionsDaily extends RDSBaseEntity
{
	private static final long serialVersionUID = 8180481539050907025L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	@Id
	@Column(name = "country")
	private String country;

	@Id
	@Column(name = "short_id")
	private long short_id;

	@Column(name = "raw_de")
	private double raw_de;

	@Column(name = "real_de")
	private double real_de;

	@Column(name = "overall_rank")
	private Integer overall_rank;

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public long getShort_id()
	{
		return short_id;
	}

	public void setShort_id(long short_id)
	{
		this.short_id = short_id;
	}

	public double getRaw_de()
	{
		return raw_de;
	}

	public void setRaw_de(double raw_de)
	{
		this.raw_de = raw_de;
	}

	public double getReal_de()
	{
		return real_de;
	}

	public void setReal_de(double real_de)
	{
		this.real_de = real_de;
	}

	public Integer getRank()
	{
		return overall_rank;
	}

	public void setRank(Integer rank)
	{
		this.overall_rank = rank;
	}

	@Override
	public TalentExpressionsDaily clone()
	{
		return new Cloner().deepClone(this);
	}

}
