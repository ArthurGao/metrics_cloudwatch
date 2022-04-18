package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import javax.persistence.Column;

public class ExtendedPlatformPK implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7203659105479420349L;

    @Column(name = "platform_id")
    private String platform_id;

    @Column(name = "platform_name")
    private String platform_name;

    @Column(name = "availability_market")
    private String availability_market;

    public ExtendedPlatformPK()
    {

    }

    public ExtendedPlatformPK(String platform_id, String platform_name, String availability_market)
    {
        super();
        this.platform_id = platform_id;
        this.platform_name = platform_name;
        this.availability_market = availability_market;
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

    public String getAvailability_market()
    {
        return availability_market;
    }

    public void setAvailability_market(String availability_market)
    {
        this.availability_market = availability_market;
    }

}
