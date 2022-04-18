package com.parrotanalytics.api.response.resource;

import java.util.List;

import com.parrotanalytics.api.apidb_model.Resource;
import com.parrotanalytics.apicore.model.response.APIResponse;

public class ResourceResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 6742928559508625383L;
    private List<Resource> resources;

    public List<Resource> getResources()
    {
        return resources;
    }

    public void setResources(List<Resource> resources)
    {
        this.resources = resources;
    }

}
