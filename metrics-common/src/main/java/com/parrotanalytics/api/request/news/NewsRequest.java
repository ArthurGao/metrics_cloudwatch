package com.parrotanalytics.api.request.news;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * 
 * @author sanjeev
 *
 */
public class NewsRequest extends APIRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3484947365025028929L;

    @JsonProperty("content_type")
    private String content_type;

    @JsonProperty("content")
    private String content;

    private List<String> listContentIDs;

    @JsonProperty("title")
    private String title;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("fulltext")
    private boolean fulltext;

    @JsonProperty("news_title")
    private String news_title;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("demand")
    private boolean demand;

    @JsonProperty("search_days")
    private int searchDays;

    public String getContent_type()
    {
        return content_type;
    }

    public String getContent()
    {
        return content;
    }

    public List<String> getListContentIDs()
    {
        return listContentIDs;
    }

    public void setListContentIDs(List<String> listContentIDs)
    {
        this.listContentIDs = listContentIDs;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPlatform()
    {
        return platform;
    }

    public boolean isFulltext()
    {
        return fulltext;
    }

    public String getNews_title()
    {
        return news_title;
    }

    public String getTag()
    {
        return tag;
    }

    public boolean isDemand()
    {
        return demand;
    }

    public int getSearchDays()
    {
        return searchDays;
    }

}
