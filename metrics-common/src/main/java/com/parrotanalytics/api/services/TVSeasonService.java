package com.parrotanalytics.api.services;

import java.util.List;
import java.util.stream.Collectors;

import com.parrotanalytics.apicore.model.catalogapi.metadata.TVData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVItem;

@Service
public class TVSeasonService {

    protected static final Logger logger = LogManager.getLogger(TVSeasonService.class);

    @Autowired
    private MetadataCache metadataCache;

    public List<Long> filterShowsBySeasonNumber(String seasonNumberFilterValue, List<Long> contentShortIDs) {
        String[] values = seasonNumberFilterValue.split(",");
        int minNum = values.length >= 1 ? Integer.parseInt(values[0]) : 0;
        int maxNum = values.length == 2 ? Integer.parseInt(values[1]) : Integer.MAX_VALUE;

        return contentShortIDs.stream().map(shortID -> {
            CatalogItem item = null;
            try {
                item = metadataCache.resolveItem(shortID);
                if (item instanceof TVItem && ((TVItem) item).getData() instanceof TVData) {

                    TVItem tvItem = (TVItem) item;
                    Integer totalSeasonNum = Integer.parseInt(tvItem.getTotalSeasons());
                    return minNum <= totalSeasonNum && totalSeasonNum <= maxNum ? shortID : null;
                }
            } catch (NumberFormatException e) {
                logger.error("failed to parse total seasons field of {}", item.getShortID());
            }

            return null;
        }).filter(shortID -> shortID != null).collect(Collectors.toList());

    }

}
