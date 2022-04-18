package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Role;
import com.parrotanalytics.apicore.commons.constants.APICache;

/**
 * Data repository for user account information.
 * 
 * @author jackson
 * @since v1
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>
{

    @CacheEvict(APICache.CACHE_USERS)
    @Query("SELECT r FROM Role r WHERE r.status = 1")
    public List<Role> loadAllRoles();

    @Query("SELECT r FROM Role r " + "WHERE r.roleName = :roleName ")
    public Role loadRoleByRoleName(@Param("roleName") String roleName);

    @Query("SELECT r FROM Role r WHERE r.idRole =:idRole")
    public Role loadRoleById(@Param("idRole") Integer idRole);

}
