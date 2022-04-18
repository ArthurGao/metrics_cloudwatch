package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the PortfolioItems database table.
 * 
 */
@Entity
@Table(name = "portfolioitems", schema = "portal")
@NamedQuery(name = "PortfolioItem.findAll", query = "SELECT p FROM PortfolioItem p")
@Cacheable(false)
public class PortfolioItem implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPortfolioItem;

    @JoinColumn(name = "idPortfolio")
    @JsonProperty("idPortfolio")
    private Integer idPortfolio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @JsonProperty("short_id")
    private long short_id;

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateAdded == null) ? 0 : dateAdded.hashCode());
        result = prime * result + ((idPortfolio == null) ? 0 : idPortfolio.hashCode());
        result = prime * result + ((idPortfolioItem == null) ? 0 : idPortfolioItem.hashCode());
        result = prime * result + (int) (short_id ^ (short_id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PortfolioItem other = (PortfolioItem) obj;
        if (dateAdded == null)
        {
            if (other.dateAdded != null)
                return false;
        }
        else if (!dateAdded.equals(other.dateAdded))
            return false;
        if (idPortfolio == null)
        {
            if (other.idPortfolio != null)
                return false;
        }
        else if (!idPortfolio.equals(other.idPortfolio))
            return false;
        if (idPortfolioItem == null)
        {
            if (other.idPortfolioItem != null)
                return false;
        }
        else if (!idPortfolioItem.equals(other.idPortfolioItem))
            return false;
        if (short_id != other.short_id)
            return false;
        return true;
    }

    @Transient
    private Portfolio portfolio;

    /**
     * Instantiates a new portfolio item.
     */
    public PortfolioItem()
    {
    }

    /**
     * Gets the id portfolio item.
     *
     * @return the id portfolio item
     */
    public Integer getIdPortfolioItem()
    {
        return idPortfolioItem;
    }

    /**
     * Sets the id portfolio item.
     *
     * @param idPortfolioItem
     *            the new id portfolio item
     */
    public PortfolioItem withIdPortfolioItem(Integer idPortfolioItem)
    {
        this.idPortfolioItem = idPortfolioItem;
        return this;
    }

    /**
     * Sets the portfolio.
     *
     * @param portfolio
     *            the new portfolio
     */
    public PortfolioItem withIdPortfolio(Integer idPortfolio)
    {
        this.idPortfolio = idPortfolio;
        return this;
    }

    /**
     * Gets the date added.
     *
     * @return the date added
     */
    public Date getDateAdded()
    {
        return this.dateAdded;
    }

    /**
     * Sets the date added.
     *
     * @param dateAdded
     *            the new date added
     */
    public PortfolioItem withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public long getShortId()
    {
        return short_id;
    }

    public PortfolioItem withShortId(long short_id)
    {
        this.short_id = short_id;
        return this;
    }

    public Integer getIdPortfolio()
    {
        return idPortfolio;
    }

    public void setIdPortfolio(Integer idPortfolio)
    {
        this.idPortfolio = idPortfolio;
    }

}