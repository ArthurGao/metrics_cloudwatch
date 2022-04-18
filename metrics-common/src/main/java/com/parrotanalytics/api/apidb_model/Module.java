package com.parrotanalytics.api.apidb_model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Subscribed Modules to be returned with authentication response
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonInclude(Include.NON_NULL)
public class Module
{
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private Integer name;

    @JsonProperty("config")
    private Map<String, Object> config;

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public Integer getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(Integer name)
    {
        this.name = name;
    }

    /**
     * @return the config
     */
    public Map<String, Object> getConfig()
    {
        return config;
    }

    /**
     * @param config
     *            the config to set
     */
    public void setConfig(Map<String, Object> config)
    {
        this.config = config;
    }

}
