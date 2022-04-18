package com.parrotanalytics.api.apidb_model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parrotanalytics.datasourceint.dsdb.model.base.RDSBaseEntity;
import com.rits.cloning.Cloner;

/**
 * The persistent class for the Report database table.
 * 
 */
@Entity
@Table(name = "report", schema = "portal")
@Cacheable(false)
@NamedQuery(name = "Report.findAll", query = "SELECT a FROM Report a")
public class Report extends RDSBaseEntity
{

    @Column(name = "createdDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "description")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReport")
    private Integer idReport;

    @Column(name = "idUser")
    private Integer idUser;

    @Column(name = "name")
    private String name;

    @Column(name = "insights")
    private String insights;

    @Column(name = "updatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "favorite")
    private String favorite;

    @Column(name = "users")
    private String users;

    public Report()
    {

    }

    public Report(String name, String description, Integer idUser, String insights, Date createdDate, Date updatedDate)
    {
        super();
        this.createdDate = createdDate;
        this.name = name;
        this.description = description;
        this.idUser = idUser;
        this.insights = insights;
        this.updatedDate = updatedDate;
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
        Report other = (Report) obj;
        if (createdDate == null)
        {
            if (other.createdDate != null)
                return false;
        }
        else if (!createdDate.equals(other.createdDate))
            return false;
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
        if (idUser == null)
        {
            if (other.idUser != null)
                return false;
        }
        else if (!idUser.equals(other.idUser))
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

    public Integer getIdReport()
    {
        return idReport;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public String getName()
    {
        return name;
    }

    public String getInsights()
    {
        return insights;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((idReport == null) ? 0 : idReport.hashCode());
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
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

    public void setIdReport(Integer idReport)
    {
        this.idReport = idReport;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setInsights(String insights)
    {
        this.insights = insights;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public String getFavorite()
    {
        return favorite;
    }

    public void setFavorite(String favorite)
    {
        this.favorite = favorite;
    }

    public Report withUpdatedOn(Date date)
    {
        this.updatedDate = date;
        return this;
    }

    public String getUsers()
    {
        return users;
    }

    public void setUsers(String users)
    {
        this.users = users;
    }

    @Override
    public Report clone()
    {
        return new Cloner().deepClone(this);
    }

    public Report withCreatedOn(Date date)
    {
        this.createdDate = date;
        return this;
    }

}
