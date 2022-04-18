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

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@Entity
@IdClass(value = ExpressionsQuarterlyPK.class)
@Table(name = TableConstants.TALENT_EXPRESSIONS_COMPUTED_QUARTERLY, schema = TableConstants.METRICS_SCHEMA)
public class TalentExpressionsQuarterly extends RDSBaseEntity
{
	private static final long serialVersionUID = -7053799042525427155L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	@Id
	@Column(name = "range_key")
	private String range_key;

	@Id
	@Column(name = "country")
	private String country;

	@Id
	@Column(name = "short_id")
	private long short_id;

	@Column(name = "raw_de")
	private double raw_de;

	@Column(name = "peak_raw_de")
	private double peak_raw_de;

	@Column(name = "real_de")
	private double real_de;

	@Column(name = "peak_real_de")
	private double peak_real_de;

	@Column(name = "overall_rank")
	private Integer overall_rank;

	@Column(name = "rank_by_peak")
	private Integer rank_by_peak;

	@Column(name = "best_rank")
	private Integer best_rank;

	@Column(name = "num_days")
	private int num_days;

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getRange_key()
	{
		return range_key;
	}

	public void setRange_key(String range_key)
	{
		this.range_key = range_key;
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

	public double getPeak_raw_de()
	{
		return peak_raw_de;
	}

	public void setPeak_raw_de(double peak_raw_de)
	{
		this.peak_raw_de = peak_raw_de;
	}

	public double getReal_de()
	{
		return real_de;
	}

	public void setReal_de(double real_de)
	{
		this.real_de = real_de;
	}

	public double getPeak_real_de()
	{
		return peak_real_de;
	}

	public void setPeak_real_de(double peak_real_de)
	{
		this.peak_real_de = peak_real_de;
	}

	public Integer getOverall_rank()
	{
		return overall_rank;
	}

	public void setOverall_rank(Integer overall_rank)
	{
		this.overall_rank = overall_rank;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TalentExpressionsQuarterly clone()
	{
		return new Cloner().deepClone(this);
	}

	public Integer getBest_rank()
	{
		return best_rank;
	}

	public void setBest_rank(Integer best_rank)
	{
		this.best_rank = best_rank;
	}

	public Integer getRank_by_peak()
	{
		return rank_by_peak;
	}

	public void setRank_by_peak(Integer rank_by_peak)
	{
		this.rank_by_peak = rank_by_peak;
	}

	public int getNum_days()
	{
		return num_days;
	}

	public void setNum_days(int num_days)
	{
		this.num_days = num_days;
	}
}
