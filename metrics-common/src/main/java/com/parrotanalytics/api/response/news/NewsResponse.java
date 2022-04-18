package com.parrotanalytics.api.response.news;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedNewsArticle;
import com.parrotanalytics.apicore.model.response.APIResponse;

public class NewsResponse extends APIResponse
{
    @JsonProperty("data")
    private List<ExtendedNewsArticle> data;

    public NewsResponse(List<ExtendedNewsArticle> data)
    {
        this.data = data;
    }

    public List<ExtendedNewsArticle> getData()
    {
        return data;
    }

    public void setData(List<ExtendedNewsArticle> data)
    {
        this.data = data;
    }
}
