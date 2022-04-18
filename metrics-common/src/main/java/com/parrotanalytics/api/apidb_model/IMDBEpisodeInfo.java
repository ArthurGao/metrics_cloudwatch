package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;

@Table(name = "imdb_episode_info", schema = "metrics")
@Entity
@IdClass(value = IMDBEpisodeInfoPK.class)
public class IMDBEpisodeInfo extends RDSBaseEntity
{

    private static final long serialVersionUID = -969620961252876594L;

    @Id
    @Column(name = "short_id")
    private long short_id;

    @Id
    @Column(name = "series_id")
    private String series_id;

    @Id
    @Column(name = "episode_id")
    private String episode_id;

    @Column(name = "season_num")
    private int season_num;

    @Column(name = "episode_num")
    private int episode_num;

    @Column(name = "release_date")
    private String release_date;

    @Id
    @Column(name = "country")
    private String county;

    @Column(name = "country_iso")
    private String country_iso;

    @Id
    @Column(name = "release_type")
    private String release_type;

    @Column(name = "episode_name")
    private String episode_name;

    @Column(name = "imdb_rating")
    private double imdb_rating;

    @Column(name = "num_votes")
    private int num_votes;

    public IMDBEpisodeInfo()
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

    public int getEpisode_num()
    {
        return episode_num;
    }

    public void setEpisode_num(int episode_num)
    {
        this.episode_num = episode_num;
    }

    public String getEpisode_name()
    {
        return episode_name;
    }

    public void setEpisode_name(String episode_name)
    {
        this.episode_name = episode_name;
    }

    public double getImdb_rating()
    {
        return imdb_rating;
    }

    public void setImdb_rating(double imdb_rating)
    {
        this.imdb_rating = imdb_rating;
    }

    public int getNum_votes()
    {
        return num_votes;
    }

    public void setNum_votes(int num_votes)
    {
        this.num_votes = num_votes;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public int getSeason_num()
    {
        return season_num;
    }

    public void setSeason_num(int season_num)
    {
        this.season_num = season_num;
    }

    public long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(long short_id)
    {
        this.short_id = short_id;
    }

    public String getCountry_iso()
    {
        return country_iso;
    }

    public void setCountry_iso(String country_iso)
    {
        this.country_iso = country_iso;
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
