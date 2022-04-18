package com.parrotanalytics.api.data.repo.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parrotanalytics.api.apidb_model.Notification;

/**
 * Data repository for user account information.
 * 
 * @author jackson
 * @since v1
 */
@Repository
@RepositoryRestResource(exported = false)
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Integer>
{

    @Query("SELECT n FROM Notification n WHERE n.idReceiveUser = :userId AND n.notifyCount >= :notifyCount")
    public List<Notification> loadNotificationsByUser(@Param("userId") Integer userId,
            @Param("notifyCount") Integer notifyCount, Pageable page);

    @Query("SELECT n FROM Notification n WHERE n.idReceiveUser = :userId AND n.type = :type")
    public List<Notification> loadNotificationsByUserByType(@Param("userId") Integer userId, @Param("type") String type,
            Pageable page);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO portal.notification (idCreatedUser, idReceiveUser, type, category, resource_id, header, message, actionable, actions, ref_id, ref_type, notify_count, created_time, updated_time) "
            + "SELECT '3', u.idUser, 'general', 'learning', ?1 AS 'resource_id', NULL, NULL, TRUE, 'OKCANCEL', NULL, NULL, ?2 AS 'unread', ?3, NULL "
            + "FROM subscription.user u JOIN subscription.account a ON u.idAccount = a.idAccount "
            + "WHERE u.idUser IN (SELECT idUser FROM subscription.user WHERE status = 1 AND idUser NOT IN (SELECT idReceiveUser FROM portal.notification WHERE resource_id = ?1))", nativeQuery = true)
    public int publishResourceNotification(@Param("resourceId") Integer resourceId, @Param("notifyCount") Integer notifyCount,
            @Param("createdTime") Date createdTime);

}
