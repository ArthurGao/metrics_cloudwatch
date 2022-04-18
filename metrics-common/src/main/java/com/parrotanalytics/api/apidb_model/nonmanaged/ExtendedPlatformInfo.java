package com.parrotanalytics.api.apidb_model.nonmanaged;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SqlResultSetMapping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parrotanalytics.api.apidb_model.ExtendedPlatformPK;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;

@Entity
@SqlResultSetMapping(name = "ExtendedPlatformInfo", classes =
{
        @ConstructorResult(targetClass = ExtendedPlatformInfo.class, columns =
        {
                @ColumnResult(name = "platform_id", type = String.class),

                @ColumnResult(name = "platform_name", type = String.class),

                @ColumnResult(name = "justwatch_name", type = String.class),

                @ColumnResult(name = "platform_type", type = String.class),

                @ColumnResult(name = "home_market", type = String.class),

                @ColumnResult(name = "availability_market", type = String.class),

                @ColumnResult(name = "originals_count", type = Integer.class),

                @ColumnResult(name = "asset_url", type = String.class),

                @ColumnResult(name = "tv360_active", type = Integer.class)
        })
})
@IdClass(value = ExtendedPlatformPK.class)
public class ExtendedPlatformInfo implements NonManagedEntity
{

    /**
     * 
     */
    private static final long serialVersionUID = 6223261031605113655L;

    @Id
    @Column(name = "platform_id")
    private String platform_id;

    @Id
    @Column(name = "platform_name")
    private String platform_name;

    @JsonIgnore
    @Column(name = "justwatch_name")
    private String justwatch_name;

    @Column(name = "home_market")
    private String home_market;

    @Id
    @Column(name = "availability_market")
    private String availability_market;

    @Column(name = "originals_count")
    private Integer originals_count;

    @Column(name = "asset_url")
    private String asset_url;

    @Column(name = "tv360_active")
    private Integer tv360_active;

    @Column(name = "news_content")
    private String news_content;

    @Column(name = "platform_type")
    private String platform_type;

    public ExtendedPlatformInfo()
    {

    }

    public ExtendedPlatformInfo(String platform_id, String platform_name, String platform_type, String justwatch_name,
            String home_market, String availability_market, Integer originals_count, String asset_url,
            Integer tv360_active, String news_content)
    {
        super();
        this.platform_id = platform_id;
        this.platform_name = platform_name;
        this.platform_type = platform_type;
        this.justwatch_name = justwatch_name;
        this.home_market = home_market;
        this.availability_market = availability_market;
        this.originals_count = originals_count;
        this.asset_url = asset_url;
        this.tv360_active = tv360_active;
        this.news_content = news_content;
    }

    public String getPlatform_id()
    {
        return platform_id;
    }

    public void setPlatform_id(String platform_id)
    {
        this.platform_id = platform_id;
    }

    public String getPlatform_name()
    {
        return platform_name;
    }

    public void setPlatform_name(String platform_name)
    {
        this.platform_name = platform_name;
    }

    public String getJustwatch_name()
    {
        return justwatch_name;
    }

    public void setJustwatch_name(String justwatch_name)
    {
        this.justwatch_name = justwatch_name;
    }

    public String getHome_market()
    {
        return home_market;
    }

    public void setHome_market(String home_market)
    {
        this.home_market = home_market;
    }

    public String getAvailability_market()
    {
        return availability_market;
    }

    public void setAvailability_market(String availability_market)
    {
        this.availability_market = availability_market;
    }

    public Integer getOriginals_count()
    {
        return originals_count;
    }

    public void setOriginals_count(Integer originals_count)
    {
        this.originals_count = originals_count;
    }

    public String getAsset_url()
    {
        return asset_url;
    }

    public void setAsset_url(String asset_url)
    {
        this.asset_url = asset_url;
    }

    public Integer getTv360_active()
    {
        return tv360_active;
    }

    public void setTv360_active(Integer tv360_active)
    {
        this.tv360_active = tv360_active;
    }

    public String getNews_content()
    {
        return news_content;
    }

    public void setNews_content(String news_content)
    {
        this.news_content = news_content;
    }

    public String getPlatform_type()
    {
        return platform_type;
    }

    public void setPlatform_type(String platform_type)
    {
        this.platform_type = platform_type;
    }

}
