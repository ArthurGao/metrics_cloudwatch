package com.parrotanalytics.api.response.workflow;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
public class WorkflowResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -7899606774512474270L;

    @JsonProperty("workflows")
    @SerializedName("workflows")
    private List<Workflow> workflows;

    public List<Workflow> getWorkflows()
    {
        return workflows;
    }

    public void setWorkflows(List<Workflow> workflows)
    {
        this.workflows = workflows;
    }

}
