package com.parrotanalytics.api.request.titles;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import com.parrotanalytics.api.commons.constants.Entity;
import lombok.Data;
import org.apache.commons.collections.MapUtils;

import com.parrotanalytics.apicore.model.request.APIRequest;
import com.parrotanalytics.apicore.model.request.model.QueryItems;

/**
 * HTTP GET/POST request to internal API request mapping bean.
 *
 * @author Minh Vu
 */
@Data
public class TitlesRequest extends APIRequest
{

    /**
     * list of items
     */
    private QueryItems queryItems;

    @JsonProperty("filters")
    private DataQueryFilter filters;

    @JsonProperty("entity")
    private String entity;

    /**
     * @return the queryItems
     */
    public QueryItems getQueryItems()
    {
        return queryItems;
    }

    /**
     * @param queryItems the queryItems to set
     */
    public void setQueryItems(QueryItems queryItems)
    {
        this.queryItems = queryItems;
    }

    public Map<String, String> getSimpleFilters()
    {
        if (getFilters() != null)
        {
            return getFilters().getSimpleFilters();
        }
        return null;
    }

    /**
     * whether query has filters
     *
     * @return
     */
    public boolean hasSimpleFilters()
    {
        return MapUtils.isNotEmpty(getFilters().getSimpleFilters());
    }

    public void setSimpleFilters(HashMap<String, String> simpleFilters)
    {
        if (getFilters() == null)
        {
            setFilters(new DataQueryFilter());
        }
        getFilters().setSimpleFilters(simpleFilters);
    }

    public Entity getEntityEnum()
    {
        return Entity.fromValue(this.entity);
    }
}
