package com.parrotanalytics.api.request.report;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.api.commons.constants.ReportQueryType;
import com.parrotanalytics.api.commons.model.ReportInsight;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * Request body for report management
 * 
 * @author minhvu
 *
 */
public class ReportRequest extends APIRequest
{
    @JsonProperty("report_id")
    private Integer idReport;

    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;

    @JsonProperty("insights")
    private List<ReportInsight> insights;

    @JsonProperty("favorite")
    private Boolean favorite;

    @JsonProperty("action")
    private String action;

    @JsonIgnore()
    private boolean latest;

    @JsonProperty()
    private boolean shared = false;

    private OperationType operationType;

    private ReportQueryType queryType;

    private MultipartFile insightImage;

    @JsonProperty("id") // insight id for upload insight image
    private String insightId;

    @JsonProperty("encodedImage") // base64 encode image
    private String encodedImage;

    /** The users. */
    private List<Integer> users;

    public ReportRequest(Integer idReport, String name, String description, List<ReportInsight> insights)
    {
        super();
        this.description = description;
        this.idReport = idReport;
        this.insights = insights;
        this.name = name;
        this.favorite = false;
        this.latest = false;
    }

    public ReportRequest()
    {
        super();
    }

    public ReportRequest(OperationType operationType)
    {
        super();
        this.operationType = operationType;

    }

    public String getDescription()
    {
        return description;
    }

    public Integer getIdReport()
    {
        return idReport;
    }

    public List<ReportInsight> getInsights()
    {
        return insights;
    }

    public String getName()
    {
        return name;
    }

    public OperationType getOperationType()
    {
        return operationType;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setIdReport(Integer idReport)
    {
        this.idReport = idReport;
    }

    public void setInsights(List<ReportInsight> insights)
    {
        this.insights = insights;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
    }

    public ReportQueryType getQueryType()
    {
        return queryType;
    }

    public void setQueryType(ReportQueryType queryType)
    {
        this.queryType = queryType;
    }

    public Boolean getFavorite()
    {
        return favorite;
    }

    public void setFavorite(Boolean favorite)
    {
        this.favorite = favorite;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public MultipartFile getInsightImage()
    {
        return insightImage;
    }

    public void setInsightImage(MultipartFile insightImage)
    {
        this.insightImage = insightImage;
    }

    public String getInsightId()
    {
        return insightId;
    }

    public void setInsightId(String insightId)
    {
        this.insightId = insightId;
    }

    public String getEncodedImage()
    {
        return encodedImage;
    }

    public void setImage(String encodedImage)
    {
        this.encodedImage = encodedImage;
    }

    public List<Integer> getUsers()
    {
        return users;
    }

    public void setUsers(List<Integer> users)
    {
        this.users = users;
    }

    public boolean isLatest()
    {
        return latest;
    }

    public void setLatest(boolean latest)
    {
        this.latest = latest;
    }

    public boolean isShared()
    {
        return shared;
    }

    public void setShared(boolean shared)
    {
        this.shared = shared;
    }

}
