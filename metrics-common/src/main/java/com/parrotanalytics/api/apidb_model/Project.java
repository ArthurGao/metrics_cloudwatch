package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;

/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "project", schema = "portal")
@NamedQuery(name = "Project.findAll", query = "SELECT u FROM Project u")
public class Project implements Serializable
{
    private static final long serialVersionUID = -4712836932135762569L;

    @Id
    private Integer idProject;

    private String projectName;

    private Integer estimatedHours;

    private String status;

    private String sponsor;

    private String link;

    private String feedback;

    private Integer sponsored;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date estimatedEndDate;

    @Temporal(TemporalType.DATE)
    private Date deliveredDate;

    // bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "idAccount")
    private Account account;

    // bi-directional many-to-one association to User
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @JoinFetch
    private List<ProjectTimeSheet> projectTimeSheets;

    /**
     * Instantiates a new user.
     */
    public Project()
    {
    }

    public Integer getIdProject()
    {
        return idProject;
    }

    public void setIdProject(Integer idProject)
    {
        this.idProject = idProject;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getSponsor()
    {
        return sponsor;
    }

    public void setSponsor(String sponsor)
    {
        this.sponsor = sponsor;
    }

    public Integer getEstimatedHours()
    {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours)
    {
        this.estimatedHours = estimatedHours;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEstimatedEndDate()
    {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate)
    {
        this.estimatedEndDate = estimatedEndDate;
    }

    public Date getDeliveredDate()
    {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate)
    {
        this.deliveredDate = deliveredDate;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus()
    {
        return this.status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus(String status)
    {
        if (null != status)
        {
            this.status = status;
        }
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public Account getAccount()
    {
        return this.account;
    }

    /**
     * Sets the account.
     *
     * @param account
     *            the new account
     */
    public void setAccount(Account account)
    {
        if (null != account)
        {
            this.account = account;
        }
    }

    public List<ProjectTimeSheet> getProjectTimeSheets()
    {
        return projectTimeSheets;
    }

    public void setProjectTimeSheets(List<ProjectTimeSheet> projectTimeSheets)
    {
        this.projectTimeSheets = projectTimeSheets;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getFeedback()
    {
        return feedback;
    }

    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }

    public Integer getSponsored()
    {
        return sponsored;
    }

    public void setSponsored(Integer sponsored)
    {
        this.sponsored = sponsored;
    }

}