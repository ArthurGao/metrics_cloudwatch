package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parrotanalytics.api.apidb_model.Label;
import com.parrotanalytics.api.data.repo.api.custom.LabelRepositoryCustom;

@Repository
public interface LabelRepository extends CrudRepository<Label, Integer>, LabelRepositoryCustom
{

    @Query("SELECT l from Label l WHERE l.idAccount=:idAccount")
    List<Label> findLabelByAccount(@Param("idAccount") Integer idAccount);

    @Query("SELECT l from Label l WHERE l.shortId IN :shortIds")
    List<Label> findLabelsByTitles(@Param("shortIds") List<Long> shortIds);

    @Query("SELECT l from Label l WHERE l.label IN :labels")
    List<Label> findTitlesWithLabels(@Param("labels") List<String> labels);

    @Modifying
    @Transactional
    @Query("DELETE FROM Label l WHERE l.label=:label AND l.idAccount=:idAccount AND l.shortId=:shortId")
    void deleteOneLabel(@Param("label") String label, @Param("idAccount") Integer idAccount,
            @Param("shortId") Long shortId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Label l WHERE l.idAccount =:idAccount AND l.shortId IN :shortIds AND l.label=:label")
    void deleteAllByShortIdsAndLabel(@Param("idAccount") Integer idAccount, @Param("shortIds") List<Long> shortIds, @Param("label") String label);
    
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Label l WHERE l.idAccount =:idAccount AND l.label =:label")
    void deleteAllByLabel(@Param("idAccount") Integer idAccount, @Param("label") String label);

    @Override
    <S extends Label> S save(S entity);

    @Override
    void deleteById(Integer id);

    @Override
    void delete(Label entity);

}
