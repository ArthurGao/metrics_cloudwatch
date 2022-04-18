package com.parrotanalytics.api.response.log;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.nonmanaged.RecentUserLog;
import com.parrotanalytics.apicore.model.response.APIResponse;

public class RecentModuleResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 465148399939033606L;

    @JsonProperty("logs")
    private List<RecentUserLog> logs = new ArrayList<RecentUserLog>();

    public List<RecentUserLog> getLogs()
    {
        return logs;
    }

    public void setLogs(List<RecentUserLog> logs)
    {
        this.logs = logs;
    }

}
