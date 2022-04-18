package com.parrotanalytics.api.response.report;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.parrotanalytics.api.commons.model.ReportInsight;
import com.parrotanalytics.apicore.model.response.APIResponse;

@JsonPropertyOrder(
{
        "idReport", "name", "description", "favorite", "idUser", "createdDate", "updatedDate", "reportComponents"
})
public class ReportResponse extends APIResponse
{

    private static final long serialVersionUID = 507689771967850474L;

    @JsonProperty("createdDate")
    private Date createdDate;

    @JsonProperty("description")
    private String description;

    @JsonProperty("favorite")
    private Boolean favorite;

    @JsonProperty("report_id")
    private Integer idReport;

    @JsonProperty("insights")
    private List<ReportInsight> insights;

    @JsonProperty("name")
    private String name;

    @JsonProperty("updatedDate")
    private Date updatedDate;

    @JsonProperty("owner_id")
    private Integer userId;

    /** The users. */
    @JsonProperty("users")
    private JsonNode users;

    @JsonProperty("exportUrl")
    private String exportUrl;

    @JsonProperty("image")
    private String image;

    public ReportResponse()
    {
    }

    public ReportResponse(Integer idReport, String name, String description, Boolean favorite, Integer userId,
            JsonNode users, Date createdDate, Date updatedDate, List<ReportInsight> insights)
    {
        super();
        this.idReport = idReport;
        this.name = name;
        this.description = description;
        this.favorite = favorite;
        this.userId = userId;
        this.users = users;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.insights = insights;
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
        ReportResponse other = (ReportResponse) obj;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (idReport == null)
        {
            if (other.idReport != null)
                return false;
        }
        else if (!idReport.equals(other.idReport))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (insights == null)
        {
            if (other.insights != null)
                return false;
        }
        else if (!insights.equals(other.insights))
            return false;
        if (updatedDate == null)
        {
            if (other.updatedDate != null)
                return false;
        }
        else if (!updatedDate.equals(other.updatedDate))
            return false;
        return true;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public String getDescription()
    {
        return description;
    }

    public Boolean getFavorite()
    {
        return favorite;
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

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public JsonNode getUsers()
    {
        return users;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((idReport == null) ? 0 : idReport.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((insights == null) ? 0 : insights.hashCode());
        result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
        return result;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setFavorite(Boolean favorite)
    {
        this.favorite = favorite;
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

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public void setUsers(JsonNode users)
    {
        this.users = users;
    }

    public String getExportUrl()
    {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl)
    {
        this.exportUrl = exportUrl;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

}
