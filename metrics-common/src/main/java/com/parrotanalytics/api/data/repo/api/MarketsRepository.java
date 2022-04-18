package com.parrotanalytics.api.data.repo.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Country;
import com.parrotanalytics.api.apidb_model.CountryPlatform;
import com.parrotanalytics.api.data.repo.api.custom.MarketsRepositoryCustom;

/**
 * Extension of {@link PagingAndSortingRepository} & implementation of
 * {@link MarketsRepositoryCustom} to provide all the required data about
 * licensed markets to any of the REST API controllers.
 * 
 * @author Sanjeev Sharma
 * @since v1
 *
 */
@Repository
public interface MarketsRepository extends PagingAndSortingRepository<Country, Integer>, MarketsRepositoryCustom
{
    /**
     * 
     */
    List<Country> findAll();

    @Query("SELECT c FROM Country c WHERE c.iso IN :marketISOs")
    List<Country> findAccountMarketsByISO(@Param("marketISOs") List<String> marketISOs);

    @Query("SELECT c FROM Country c WHERE c.idCountries IN :marketIDs")
    List<Country> findAccountMarkets(@Param("marketIDs") List<Integer> marketIDs);

    @Query("SELECT c FROM Country c WHERE c.idCountries = :marketID")
    Country findAccountMarket(@Param("marketID") Integer marketID);

    @Query("SELECT c FROM Country c WHERE c.idCountries = :marketID")
    Country findAccountMarketByID(@Param("marketID") Integer marketID);

    @Query("SELECT cp FROM CountryPlatform cp WHERE cp.iso = :marketISO")
    List<CountryPlatform> findVODPlatforms(@Param("marketISO") String marketISO);

    default Map<String, Long> getAllIsoCountryPopulationMap()
    {
        // get country to population map
        Map<String, Country> mapIso2Country = getAllIsoCountryMap();
    
        Map<String, Long> mapIso2CountryPopultation = mapIso2Country.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().getPopulation()));
        return mapIso2CountryPopultation;
    }

}
