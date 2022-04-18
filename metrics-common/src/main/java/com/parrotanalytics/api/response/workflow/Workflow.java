package com.parrotanalytics.api.response.workflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Object to hold Workflow Information
 * 
 * @author Sanjeev Sharma
 *
 */
public class Workflow
{

    @JsonProperty("category")
    @SerializedName("category")
    private String category;

    @JsonProperty("workflow_name")
    @SerializedName("workflow_name")
    private String workflowName;

    @JsonProperty("dashboard_id")
    @SerializedName("dashboard_id")
    private String dashboardId;

    @JsonProperty("embedded_url")
    @SerializedName("embedded_url")
    private String embeddedURL;

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getWorkflowName()
    {
        return workflowName;
    }

    public void setWorkflowName(String workflowName)
    {
        this.workflowName = workflowName;
    }

    public String getDashboardId()
    {
        return dashboardId;
    }

    public void setDashboardId(String dashboardId)
    {
        this.dashboardId = dashboardId;
    }

    public String getEmbeddedURL()
    {
        return embeddedURL;
    }

    public void setEmbeddedURL(String embeddedURL)
    {
        this.embeddedURL = embeddedURL;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((workflowName == null) ? 0 : workflowName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Workflow other = (Workflow) obj;
        if (category == null)
        {
            if (other.category != null)
                return false;
        }
        else if (!category.equals(other.category))
            return false;
        if (workflowName == null)
        {
            if (other.workflowName != null)
                return false;
        }
        else if (!workflowName.equals(other.workflowName))
            return false;
        return true;
    }

}
