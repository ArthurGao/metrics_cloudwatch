package com.parrotanalytics.api.commons.model;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.netty.util.internal.StringUtil;

@JsonPropertyOrder(
{
        "index", "id", "heading", "description", "image", "encodedImage", "module", "component", "badger"
})
public class ReportInsight
{

    @JsonProperty("badger")
    private Map<String, Object> badger;

    @JsonProperty("component")
    private String component;

    @JsonProperty("description")
    private String description;

    @JsonProperty("heading")
    private String heading;

    @JsonProperty("id")
    private String id;

    @JsonProperty("index")
    private Integer index;

    @JsonProperty("module")
    private String module;

    @JsonProperty("image")
    private String image;

    @JsonProperty("encodedImage")
    private String encodedImage;
    
    @JsonProperty("layout")
    private String layout;

    public ReportInsight()
    {

    }

    public Map<String, Object> getBadger()
    {
        return badger;
    }

    public String getComponent()
    {
        return component;
    }

    public String getDescription()
    {
        return description;
    }

    public String getHeading()
    {
        return heading;
    }

    public String getId()
    {
        return id;
    }

    public Integer getIndex()
    {
        return index;
    }

    public String getModule()
    {
        return module;
    }

    public void setBadger(Map<String, Object> badger)
    {
        this.badger = badger;
    }

    public void setComponent(String component)
    {
        this.component = component;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setHeading(String heading)
    {
        this.heading = heading;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getEncodedImage()
    {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage)
    {
        this.encodedImage = encodedImage;
    }

    public ReportInsight merge(ReportInsight other)
    {
        if (!StringUtil.isNullOrEmpty(other.heading))
        {
            this.setHeading(other.heading);
        }
        if (!StringUtil.isNullOrEmpty(other.description))
        {
            this.setDescription(other.description);
        }
        if (!StringUtil.isNullOrEmpty(other.component))
        {
            this.setComponent(other.component);
        }
        if (!StringUtil.isNullOrEmpty(other.module))
        {
            this.setModule(other.module);
        }
        if (MapUtils.isEmpty(other.badger))
        {
            this.setBadger(other.badger);
        }
        if (!StringUtil.isNullOrEmpty(other.encodedImage))
        {
            this.setEncodedImage(other.encodedImage);
        }
        return this;
    }

    public String getLayout()
    {
        return layout;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

}
