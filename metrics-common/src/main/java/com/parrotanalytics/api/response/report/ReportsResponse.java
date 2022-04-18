package com.parrotanalytics.api.response.report;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

public class ReportsResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -7672813505066857986L;

    @JsonProperty("reports")
    private List<ReportResponse> reports = new ArrayList<ReportResponse>();

    public ReportsResponse()
    {
    }

    public ReportsResponse(List<ReportResponse> reports)
    {
        super();
        this.reports = reports;
    }

    public List<ReportResponse> getReports()
    {
        return reports;
    }

    public void setReports(List<ReportResponse> reports)
    {
        this.reports = reports;
    }

}
