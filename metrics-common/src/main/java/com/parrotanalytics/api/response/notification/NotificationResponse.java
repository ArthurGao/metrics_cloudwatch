package com.parrotanalytics.api.response.notification;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class NotificationResponse extends APIResponse
{

    private static final long serialVersionUID = -1551754593785717765L;

    @JsonProperty("notifications")
    private List<CoreNotificationPayload> notifications;

    public List<CoreNotificationPayload> getNotifications()
    {
        return notifications;
    }

    public void setNotifications(List<CoreNotificationPayload> notifications)
    {
        this.notifications = notifications;
    }

}
