package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tv_catalog_proxy database table.
 * 
 */
@Entity
@Table(name = "tv_catalog_proxy")
@NamedQuery(name = "TvCatalogProxy.findAll", query = "SELECT t FROM TvCatalogProxy t")
public class TvCatalogProxy implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pa_content_id")
    private String paContentId;

    private String friendlyName;

    private String name;

    /**
     * Instantiates a new tv catalog proxy.
     */
    public TvCatalogProxy()
    {
    }

    /**
     * Gets the pa content id.
     *
     * @return the pa content id
     */
    public String getPaContentId()
    {
        return this.paContentId;
    }

    /**
     * Sets the pa content id.
     *
     * @param paContentId
     *            the new pa content id
     */
    public void setPaContentId(String paContentId)
    {
        this.paContentId = paContentId;
    }

    /**
     * Gets the friendly name.
     *
     * @return the friendly name
     */
    public String getFriendlyName()
    {
        return this.friendlyName;
    }

    /**
     * Sets the friendly name.
     *
     * @param friendlyName
     *            the new friendly name
     */
    public void setFriendlyName(String friendlyName)
    {
        this.friendlyName = friendlyName;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

}