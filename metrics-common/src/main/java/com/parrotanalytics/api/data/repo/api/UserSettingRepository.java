package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.UserSetting;

/**
 * Data repository for user settings information.
 * 
 * @author jackson
 * @since v1
 */
@Repository
public interface UserSettingRepository extends CrudRepository<UserSetting, Integer>
{

    @Query("SELECT u FROM UserSetting u WHERE u.idUser =:idUser")
    public List<UserSetting> loadUserSettings(@Param("idUser") Integer idUser);

    @Query("SELECT u FROM UserSetting u WHERE u.idUser =:idUser AND u.settingName =:settingName")
    public UserSetting loadUserSettingByName(@Param("idUser") Integer idUser, @Param("settingName") String settingName);
    
    @Query("SELECT u FROM UserSetting u WHERE u.idUser =:idUser AND u.settingType = :settingType AND u.settingName =:settingName")
    public UserSetting loadUserSettingByTypeAndName(@Param("idUser") Integer idUser, @Param("settingType") String settingType, @Param("settingName") String settingName);

    @Query("SELECT u FROM UserSetting u WHERE u.settingName =:settingName")
    public UserSetting loadAllSettingByName(@Param("settingName") String settingName);

    @Query("SELECT u FROM UserSetting u WHERE u.idUser =:idUser AND u.settingType = :settingType")
    public List<UserSetting> loadUserSettingsByType(@Param("idUser") Integer idUser,
            @Param("settingType") String settingType);

    @Query("SELECT u FROM UserSetting u WHERE u.settingType IN :settingTypes order by u.lastUsedOn desc")
    public List<UserSetting> loadAllSettingsByTypes(@Param("settingTypes") List<String> settingTypes);

    @Query("SELECT u FROM UserSetting u WHERE u.idUser =:idUser AND u.settingType IN :settingTypes")
    public List<UserSetting> loadUserSettingsByTypes(@Param("idUser") Integer idUser,
            @Param("settingTypes") List<String> settingType);

}
