package com.parrotanalytics.api.request.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * Request body for label management
 * 
 * @author minhvu
 *
 */
public class WorkflowRequest extends APIRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2403955564907587028L;

    @JsonProperty("category")
    private String category;

    @JsonProperty("dashboard_id")
    private String dashboardId;

    public WorkflowRequest()
    {
        super();
    }

    public WorkflowRequest(String dashboardId)
    {
        super();
        this.dashboardId = dashboardId;
    }

    public WorkflowRequest(String category, String dashboardId)
    {
        super();
        this.category = category;
        this.dashboardId = dashboardId;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDashboardId()
    {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId)
    {
        this.dashboardId = dashboardId;
    }

}
