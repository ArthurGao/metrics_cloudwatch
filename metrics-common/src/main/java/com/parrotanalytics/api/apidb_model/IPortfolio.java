package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Interface IPortfolio defines the properties for a portfolio item.
 * 
 * @author Chris
 */
public interface IPortfolio extends Serializable
{

    /**
     * Gets the id portfolio.
     *
     * @return the id portfolio
     */
    public Integer getIdPortfolio();

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName();

    public Date getCreatedOn();

    public Date getUpdatedOn();
    
    public Integer getIdUser();
}
