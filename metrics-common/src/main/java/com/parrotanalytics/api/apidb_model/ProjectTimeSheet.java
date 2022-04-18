package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;

/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name = "projecttimesheet", schema = "portal")
@NamedQuery(name = "ProjectTimeSheet.findAll", query = "SELECT u FROM ProjectTimeSheet u")
public class ProjectTimeSheet implements Serializable
{
    private static final long serialVersionUID = 8482778270434900446L;

    @Id
    private Integer idTimeSheet;

    private Integer idUser;

    private String task;

    private Double hours;

    private String notes;

    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    // bi-directional many-to-one association to Account
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFetch
    @JoinColumn(name = "idProject")
    private Project project;

    private Integer billable;

    public Integer getIdTimeSheet()
    {
        return idTimeSheet;
    }

    public void setIdTimeSheet(Integer idTimeSheet)
    {
        this.idTimeSheet = idTimeSheet;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }

    public Double getHours()
    {
        return hours;
    }

    public void setHours(Double hours)
    {
        this.hours = hours;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public Date getAddedDate()
    {
        return addedDate;
    }

    public void setAddedDate(Date addedDate)
    {
        this.addedDate = addedDate;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public Integer getBillable()
    {
        return billable;
    }

    public void setBillable(Integer billable)
    {
        this.billable = billable;
    }

}