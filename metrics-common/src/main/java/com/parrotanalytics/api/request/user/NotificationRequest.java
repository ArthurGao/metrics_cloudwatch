package com.parrotanalytics.api.request.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * REST API {@link Endpoint} create user request.
 * 
 * @author Jackson Lee
 *
 */
public class NotificationRequest extends APIRequest
{
    protected OperationType operationType;

    private List<Integer> notificationIdList;

    private Integer notifyCount;

    private Integer notificationId;

    @JsonProperty("id_resource")
    private Integer idResource;

    private String type;

    private String category;

    public OperationType getOperationType()
    {
        return operationType;
    }

    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
    }

    public List<Integer> getNotificationIdList()
    {
        return notificationIdList;
    }

    public void setNotificationIdList(List<Integer> notificationIdList)
    {
        this.notificationIdList = notificationIdList;
    }

    public Integer getIdResource()
    {
        return idResource;
    }

    public void setIdResource(Integer idResource)
    {
        this.idResource = idResource;
    }

    public int getNotifyCount()
    {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount)
    {
        this.notifyCount = notifyCount;
    }

    public boolean isRead()
    {
        return (notifyCount == 0) ? true : false;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public Integer getNotificationId()
    {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId)
    {
        this.notificationId = notificationId;
    }

}
