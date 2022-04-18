package com.parrotanalytics.api.data.repo.api.custom;

import com.parrotanalytics.api.apidb_model.nonmanaged.GenreEntityCount;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.commons.constants.Entity;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface CatalogGeneRepositoryCustom {

  @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
  List<GenreEntityCount> getGenreEntityCount(Entity entity);
}
