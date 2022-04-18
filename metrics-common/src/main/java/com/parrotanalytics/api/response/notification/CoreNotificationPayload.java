package com.parrotanalytics.api.response.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

@JsonPropertyOrder(
{
        "id_notification", "id_user", "notification_type", "notification_category", "resource_id", "header", "message",
        "actionable", "actions", "notify_count", "new_notification", "avatar_alias", "user_name"
})
@JsonInclude(Include.NON_NULL)
public class CoreNotificationPayload extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1551754593785717765L;

    /**
     * 
     */

    @JsonProperty("id_notification")
    private Integer idNotifcation;

    @JsonProperty("id_user")
    private Integer idUser;

    @JsonProperty("notification_type")
    private String notificationType;

    @JsonProperty("notification_category")
    private String notificationCategory;

    @JsonProperty("resource_id")
    private int resourceId;

    @JsonProperty("header")
    private String header;

    @JsonProperty("message")
    private String message;

    @JsonProperty("actionable")
    private Integer actionable;

    @JsonProperty("actions")
    private String actions;

    @JsonProperty("notify_count")
    private int notifyCount;

    @JsonProperty("new_notification")
    private Boolean newNotification;

    @JsonProperty("avatar_alias")
    private String avatarAlias;

    @JsonProperty("user_name")
    private String userName;

    private String notificationRefId;

    private String notificationRefType;

    @JsonProperty("created_time")
    private String createdTime;

    @JsonProperty("updated_time")
    private String updatedTime;

    public Integer getIdNotifcation()
    {
        return idNotifcation;
    }

    public void setIdNotifcation(Integer idNotifcation)
    {
        this.idNotifcation = idNotifcation;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public String getNotificationType()
    {
        return notificationType;
    }

    public void setType(String notificationType)
    {
        this.notificationType = notificationType;
    }

    public String getNotificationCategory()
    {
        return notificationCategory;
    }

    public void setCategory(String notificationCategory)
    {
        this.notificationCategory = notificationCategory;
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

    public Integer getActionable()
    {
        return actionable;
    }

    public void setActionable(Integer actionable)
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

    public int getNotifyCount()
    {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount)
    {
        this.notifyCount = notifyCount;
    }

    public Boolean getNewNotification()
    {
        return newNotification;
    }

    public void setNewNotification(Boolean newNotification)
    {
        this.newNotification = newNotification;
    }

    public String getAvatarAlias()
    {
        return avatarAlias;
    }

    public void setAvatarAlias(String avatarAlias)
    {
        this.avatarAlias = avatarAlias;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getNotificationRefId()
    {
        return notificationRefId;
    }

    public void setRefId(String notificationRefId)
    {
        this.notificationRefId = notificationRefId;
    }

    public String getNotificationRefType()
    {
        return notificationRefType;
    }

    public void setRefType(String notificationRefType)
    {
        this.notificationRefType = notificationRefType;
    }

    public String getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(String createdTime)
    {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime()
    {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime)
    {
        this.updatedTime = updatedTime;
    }

}
