package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the userRoles database table.
 * 
 */
@Entity
@Table(name = "passwordresetlog", schema = "portal")
@NamedQuery(name = "PasswordResetLog.findAll", query = "SELECT u FROM PasswordResetLog u")
public class PasswordResetLog implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private Integer idPasswordReset;

    // bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonBackReference
    private InternalUser user;

    private String resetKey;

    @Column(name = "generatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedDate;

    @Column(name = "actionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    /**
     * Instantiates a new user role.
     */
    public PasswordResetLog()
    {
    }

    public Integer getIdPasswordReset()
    {
        return idPasswordReset;
    }

    public void setIdPasswordReset(Integer idPasswordReset)
    {
        this.idPasswordReset = idPasswordReset;
    }

    public InternalUser getUser()
    {
        return user;
    }

    public void setUser(InternalUser user)
    {
        this.user = user;
    }

    public String getResetKey()
    {
        return resetKey;
    }

    public void setResetKey(String resetKey)
    {
        this.resetKey = resetKey;
    }

    public Date getGeneratedDate()
    {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate)
    {
        this.generatedDate = generatedDate;
    }

    public Date getActionDate()
    {
        return actionDate;
    }

    public void setActionDate(Date actionDate)
    {
        this.actionDate = actionDate;
    }

}