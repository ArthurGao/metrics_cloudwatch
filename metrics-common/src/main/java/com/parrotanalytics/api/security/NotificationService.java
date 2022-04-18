package com.parrotanalytics.api.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.apidb_model.Notification;
import com.parrotanalytics.api.data.repo.api.NotificationRepository;

/**
 * The Class UserService loads users from the backing store.
 * 
 * @author Chris
 * @author Jackson
 */
@Service
public class NotificationService
{

    @Autowired
    private NotificationRepository notificationRepo;

    public void createNotification(Integer createdUser, Integer receiveUser, String type, boolean isActionable,
            String message, String notificationRefId, String notificationRefType)
    {

        // Notification notification = populateNotification(createdUser, type,
        // isActionable, message, receiveUser,
        // notificationRefId, notificationRefType);
        // notificationRepo.save(notification);
    }

    public void createNotificationsForMultipleUsers(Integer createdUser, List<Integer> receiveUsers, String type,
            boolean isActionable, String message, String notificationRefId, String notificationRefType)
    {

        // List<Notification> notifications = new ArrayList<>();
        //
        // for (Integer receiveUser : receiveUsers)
        // {
        // Notification notification = populateNotification(createdUser, type,
        // isActionable, message, receiveUser,
        // notificationRefId, notificationRefType);
        // notifications.add(notification);
        // }
        //
        // notificationRepo.save(notifications);
    }

    public Notification populateNotification(Integer createdUser, String type, boolean isActionable, String message,
            Integer receiveUser, String notificationRefId, String notificationRefType)
    {
        Notification notification = new Notification();
        // notification.setIdCreatedUser(createdUser);
        // notification.setIdReceiveUser(receiveUser);
        // notification.setNotificationType(type);
        // notification.setActionable(isActionable == true ? 1 : 0);
        // notification.setMessage(message);
        // notification.setCreatedTime(new Date());
        // notification.setStatus(1);
        // notification.setNotificationRefId(notificationRefId);
        // notification.setNotificationRefType(notificationRefType);
        return notification;
    }
}