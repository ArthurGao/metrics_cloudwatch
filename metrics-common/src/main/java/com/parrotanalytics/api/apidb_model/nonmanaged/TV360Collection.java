package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.request.demand.DataQuery;

/**
 * @author Minh Vu
 * 
 *         Collection is simply a class that defines set of filters from catalog titles
 * 
 *         For example: <br/>
 * 
 *         <b> { type: "platform", "name": "Netflix", query: { "filters": { "network" : "Netflix" } } } </b>
 */
public class TV360Collection implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7908647605890173077L;

    private Integer id;

    /*
     * type can be preset portfolio, platform
     */
    @JsonProperty("type")
    private CollectionType type;

    @JsonProperty("name")
    private String name;

    @Transient
    @JsonIgnore
    private List<Long> shortIDs;

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((query == null) ? 0 : query.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TV360Collection other = (TV360Collection) obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (query == null)
        {
            if (other.query != null)
                return false;
        }
        else if (!query.equals(other.query))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @JsonProperty("query")
    private DataQuery query;

    public DataQuery getQuery()
    {
        return query;
    }

    public void setQuery(DataQuery dataQuery)
    {
        this.query = dataQuery;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public CollectionType getType()
    {
        return type;
    }

    public void setType(CollectionType type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Long> getShortIDs()
    {
        return shortIDs;
    }

    public void setShortIDs(List<Long> shortIDs)
    {
        this.shortIDs = shortIDs;
    }

}