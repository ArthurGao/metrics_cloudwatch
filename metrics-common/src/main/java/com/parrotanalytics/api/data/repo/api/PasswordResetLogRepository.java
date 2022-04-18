package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.PasswordResetLog;

/**
 * Data repository for user account information.
 * 
 * @author jackson
 * @since v1
 */
@Repository
public interface PasswordResetLogRepository extends CrudRepository<PasswordResetLog, Integer>
{
    @Query("SELECT u FROM PasswordResetLog u " + "WHERE u.resetKey = :resetKey")
    public PasswordResetLog loadPasswordResetLogByKey(@Param("resetKey") String resetKey);

    @Query("SELECT u FROM PasswordResetLog u " + "WHERE u.user = :user")
    public List<PasswordResetLog> loadPasswordResetLogsByUser(@Param("user") InternalUser user);
}
