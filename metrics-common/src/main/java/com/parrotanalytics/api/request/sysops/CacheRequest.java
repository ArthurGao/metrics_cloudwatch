package com.parrotanalytics.api.request.sysops;

import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * Request body for system operation
 * 
 * @author minhvu
 *
 */
public class CacheRequest extends APIRequest
{

    private String cacheType;

    public CacheRequest()
    {
        super();
    }

    public String getCacheType()
    {
        return cacheType;
    }

    public void setCacheType(String cacheType)
    {
        this.cacheType = cacheType;
    }

}
