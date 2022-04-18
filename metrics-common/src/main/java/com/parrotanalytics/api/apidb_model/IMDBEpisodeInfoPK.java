package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 
 * @author minhvu
 *
 */
public class IMDBEpisodeInfoPK implements Serializable
{

    private static final long serialVersionUID = 4525540613911377719L;

    @Id
    @Column(name = "short_id")
    private long short_id;

    @Id
    @Column(name = "series_id")
    private String series_id;

    @Id
    @Column(name = "episode_id")
    private String episode_id;

    @Id
    @Column(name = "country")
    private String county;

    @Id
    @Column(name = "release_type")
    private String release_type;

    public IMDBEpisodeInfoPK()
    {

    }

    public String getEpisode_id()
    {
        return episode_id;
    }

    public void setEpisode_id(String episode_id)
    {
        this.episode_id = episode_id;
    }

    public long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(long short_id)
    {
        this.short_id = short_id;
    }

    public String getCounty()
    {
        return county;
    }

    public void setCounty(String county)
    {
        this.county = county;
    }

    public String getRelease_type()
    {
        return release_type;
    }

    public void setRelease_type(String release_type)
    {
        this.release_type = release_type;
    }
}
