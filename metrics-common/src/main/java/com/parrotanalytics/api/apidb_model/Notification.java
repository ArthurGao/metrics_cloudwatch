package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notification", schema = "portal")
public class Notification implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4598489612565103442L;

    public static String GENERAL_STRING = "general";
    public static String ACCOUNT_STRING = "account";
    public static String INSIGHT_STRING = "insight";

    @Id
    @Column
    private Integer idNotification;

    @Column
    private Integer idReceiveUser;

    @Column
    private Integer idCreatedUser;

    @Column(name = "type")
    private String type;

    @Column(name = "category")
    private String category;

    @Column(name = "resource_id")
    private int resourceId;

    @Column
    private String header;

    @Column
    private String message;

    @Column
    private int actionable;

    @Column
    private String actions;

    @Column(name = "ref_id")
    private String refId;

    @Column(name = "ref_type")
    private String refType;

    @Column(name = "notify_count")
    private int notifyCount;

    /** The created on. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    /** The created on. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    public Notification()
    {
        super();
    }

    public Integer getIdNotification()
    {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification)
    {
        this.idNotification = idNotification;
    }

    public Integer getIdReceiveUser()
    {
        return idReceiveUser;
    }

    public void setIdReceiveUser(Integer idReceiveUser)
    {
        this.idReceiveUser = idReceiveUser;
    }

    public Integer getIdCreatedUser()
    {
        return idCreatedUser;
    }

    public void setIdCreatedUser(Integer idCreatedUser)
    {
        this.idCreatedUser = idCreatedUser;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String notificationType)
    {
        this.type = notificationType;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String notificationCategory)
    {
        this.category = notificationCategory;
    }

    public int getResourceId()
    {
        return resourceId;
    }

    public void setResourceId(int resourceId)
    {
        this.resourceId = resourceId;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getActionable()
    {
        return actionable;
    }

    public void setActionable(int actionable)
    {
        this.actionable = actionable;
    }

    public String getActions()
    {
        return actions;
    }

    public void setActions(String actions)
    {
        this.actions = actions;
    }

    public String getRefId()
    {
        return refId;
    }

    public void setRefId(String notificationRefId)
    {
        this.refId = notificationRefId;
    }

    public String getRefType()
    {
        return refType;
    }

    public void setRefType(String notificationRefType)
    {
        this.refType = notificationRefType;
    }

    public int getNotifyCount()
    {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount)
    {
        this.notifyCount = notifyCount;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

}