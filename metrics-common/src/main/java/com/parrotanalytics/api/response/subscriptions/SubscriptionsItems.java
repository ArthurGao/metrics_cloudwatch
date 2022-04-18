package com.parrotanalytics.api.response.subscriptions;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.parrotanalytics.api.response.markets.AccountMarketResponse;
import com.parrotanalytics.api.response.project.ProjectResponse;
import com.parrotanalytics.api.response.titles.SubscriptionTitleResponse;
import com.parrotanalytics.api.response.user.UserResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonInclude(Include.NON_NULL)
public class SubscriptionsItems
{
    private List<SubscriptionTitleResponse> titles;

    private List<AccountMarketResponse> markets;

    private List<ModuleResponse> modules;

    private Map<String, SubscriptionsSummaryResponse> summary;

    private List<UserResponse> users;

    private List<ProjectResponse> projects;

    /**
     * @return the titles
     */
    public List<SubscriptionTitleResponse> getTitles()
    {
        return titles;
    }

    /**
     * @param titles
     *            the titles to set
     */
    public void setTitles(List<SubscriptionTitleResponse> titles)
    {
        this.titles = titles;
    }

    /**
     * @return the markets
     */
    public List<AccountMarketResponse> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets
     *            the markets to set
     */
    public void setMarkets(List<AccountMarketResponse> markets)
    {
        this.markets = markets;
    }

    public List<ModuleResponse> getModules()
    {
        return modules;
    }

    public void setModules(List<ModuleResponse> modules)
    {
        this.modules = modules;
    }

    public List<UserResponse> getUsers()
    {
        return users;
    }

    public void setUsers(List<UserResponse> users)
    {
        this.users = users;
    }

    public List<ProjectResponse> getProjects()
    {
        return projects;
    }

    public void setProjects(List<ProjectResponse> projects)
    {
        this.projects = projects;
    }

    public Map<String, SubscriptionsSummaryResponse> getSummary()
    {
        return summary;
    }

    public void setSummary(Map<String, SubscriptionsSummaryResponse> summary)
    {
        this.summary = summary;
    }

}
