//package com.parrotanalytics.api.data.repo.api;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.parrotanalytics.api.apidb_model.InternalUser;
//import com.parrotanalytics.api.apidb_model.UserRole;
//import com.parrotanalytics.api.data.repo.api.custom.SubscriptionsRepositoryCustom;
//
///***
// * Extension of{
// * 
// * @link PagingAndSortingRepository}&implementation
// *       of*{@link SubscriptionsRepositoryCustom}to provide all the required
// *       data about*licensed markets&content titles to any of the REST API
// *       controllers.**@author Sanjeev Sharma*@since v1
// **/
//@Repository
//public interface UserRoleRepository extends CrudRepository<UserRole, String>
//{
//    @Query("SELECT s from UserRole s WHERE s.idUser = :idUser AND s.idRole = :idRole")
//    UserRole findRole(@Param("idUser") InternalUser user, @Param("idRole") String idRole);
//
//    @Query("SELECT s from UserRole s WHERE s.idUser = :idUser")
//    List<UserRole> findRoles(@Param("idUser") Integer userId);
//
//}
