package com.parrotanalytics.api.request.rawsignal;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.request.demand.DemandRequest;

public class RawSignalRequest extends DemandRequest
{

    private static final long serialVersionUID = -552591750291132546L;

    @JsonProperty("source")
    private String source;

    private List<String> sourceList;

    @Override
    public DemandRequest clone()
    {
        RawSignalRequest deepClone = (RawSignalRequest) super.clone();
        deepClone.source = this.source;
        deepClone.sourceList = this.sourceList;
        return deepClone;
    }

    public String getSource()
    {
        return this.source;
    }

    public List<String> getSourceList()
    {
        return this.sourceList;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public void setSourceList(List<String> sourceList)
    {
        this.sourceList = sourceList;
    }

}
