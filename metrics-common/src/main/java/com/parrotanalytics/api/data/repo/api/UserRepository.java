package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.Role;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data repository for user information.
 *
 * @author Sanjeev Sharma
 * @author minhvu
 * @author Jackson
 * <p>
 * Read this: DO NOT USE CACHEABLE for InternalUser since it will throw
 * an exception after successfully created a new user : An attempt was
 * made to traverse a relationship using indirection that had a null
 * Session. This often occurs when an entity with an uninstantiated LAZY
 * relationship is serialized and that relationship is traversed after
 * serialization. To avoid this issue, instantiate the LAZY relationship
 * prior to serialization. The reason is cached Internal User object has
 * circular reference of UserRole i.e.
 * InternalUser->UserRole->InternalUser hence failed to initialize
 * List<UserRole> roles field.
 * @since v1
 */

@Repository
public interface UserRepository extends CrudRepository<InternalUser, Integer>
{

    @Cacheable(cacheNames = APICacheConstants.CACHE_USERS, key = "'emailid:' + #p0")
    @Query("SELECT u FROM InternalUser u " + "WHERE u.emailAddress = :emailAddress")
    public InternalUser loadUserByEmail(@Param("emailAddress") String emailAddress);

    /* causing stranger behavior to return incorrect useer information */
    //@Cacheable(cacheNames = APICacheConstants.CACHE_USERS, key = "'userid:' + #p0")
    @Query("SELECT u FROM InternalUser u WHERE u.idUser = :idUser "
            + "AND u.status = 1 AND u.account.status = 1 AND u.account.customer.status = 1")
    public InternalUser loadActiveUserByID(@Param("idUser") Integer idUser);

    @Query("SELECT u FROM InternalUser u WHERE u.idUser = :idUser")
    public InternalUser loadUserByID(@Param("idUser") Integer idUser);

    @Query("SELECT u FROM InternalUser u JOIN Account a ON a.idAccount = u.account.idAccount "
            + "WHERE a.apiId = :apiKey AND u.emailAddress LIKE 'apiuser%' AND a.status = 1")
    public InternalUser loadAPIUser(@Param("apiKey") String apiKey);

    @Cacheable(cacheNames = APICacheConstants.CACHE_USERS, key = "'users-account:' + #account.idAccount")
    @Query("SELECT u FROM InternalUser u WHERE u.account = :account AND u.status = 1")
    public List<InternalUser> findActiveAccountUsers(@Param("account") Account account);

    // Caching Disabled : Not Performance Critical
    @Query("SELECT u FROM InternalUser u WHERE u.account = :account")
    public List<InternalUser> findAccountUsers(@Param("account") Account account);

    // Caching Disabled : Not Performance Critical
    @Query("SELECT u FROM InternalUser u")
    public List<InternalUser> findAllUsers();

    @Query(value = "SELECT idUser from subscription.user WHERE emailAddress = ?", nativeQuery = true)
    public Integer findUserIdByEmailAddress(String emailAddress);

    /**
     * Roles Methods
     */

    @Query("SELECT r FROM Role r " + "WHERE r.roleName = :roleName ")
    public Role loadRoleByRoleName(@Param("roleName") String roleName);

    @Query("SELECT r.roleName FROM Role r")
    public List<String> loadAllRoleNames();

}
