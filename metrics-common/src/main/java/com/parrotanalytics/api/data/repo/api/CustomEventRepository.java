package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parrotanalytics.api.apidb_model.CustomEvent;

@Repository
public interface CustomEventRepository extends CrudRepository<CustomEvent, Integer>
{

    @Query("SELECT c from CustomEvent c WHERE c.idAccount=:idAccount and c.shortId =:shortId")
    List<CustomEvent> findEventsForTitle(@Param("idAccount") Integer idAccount, @Param("shortId") Long shortId);
    

    @Modifying
    @Transactional
    @Query("DELETE FROM CustomEvent c WHERE c.idAccount=:idAccount AND c.eventName=:eventName AND c.shortId=:shortId")
    void deleteOneEvent(@Param("eventName") String eventName, @Param("idAccount") Integer idAccount,
            @Param("shortId") Long shortId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CustomEvent c WHERE c.idAccount =:idAccount AND c.shortId=:shortId")
    void deleteAllEventsForTitle(@Param("idAccount") Integer idAccount, @Param("shortId") Long shortId);

}
