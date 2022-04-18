package com.parrotanalytics.api.request.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * Request body for export report function
 * 
 * @author minhvu
 *
 */
public class ExportReportRequest extends APIRequest
{
    @JsonProperty("report_id")
    private Integer idReport;

    @JsonProperty("format")
    private String format;

    public ExportReportRequest()
    {
        super();
    }

    public ExportReportRequest(Integer idReport, String format)
    {
        this.idReport = idReport;
        this.format = format;
    }

    public Integer getIdReport()
    {
        return idReport;
    }

    public void setIdReport(Integer idReport)
    {
        this.idReport = idReport;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

}
