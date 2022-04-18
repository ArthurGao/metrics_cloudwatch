package com.parrotanalytics.api.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.parrotanalytics.api.apidb_model.nonmanaged.MetadataESFilter;
import com.parrotanalytics.api.commons.gson.RuntimeTypeAdapterFactory;
import com.parrotanalytics.apicore.model.catalogapi.metadata.BaseData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.MovieData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.MovieItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TalentData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TalentItem;
import com.parrotanalytics.commons.constants.ParrotConstants;
import com.parrotanalytics.enrichment.elasticsearch.ElasticSearchIndex;
import com.parrotanalytics.enrichment.elasticsearch.IndexType;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.ClearScroll;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchScroll;
import io.searchbox.params.Parameters;

/**
 * @author Minh Vu
 */
@Service
public class CatalogESService
{
    private static final String SCROLL_ID = "_scroll_id";

    protected static final Logger logger = LogManager.getLogger(CatalogESService.class);

    public static final int ES_WINDOW_LIMIT = 1000;

    public static final String SCROLL = "1m";

    @Autowired
    private JestClient jestClient;

    private static String[] esTVFullCatalogSources = new String[] { "title",

            "parrot_id",

            "short_id",

            "display_name",

            "catalog_state",

            "data.tag",

            "data.type",

            "data.main_genre",

            "data.sub_genre",

            "data.release_year",

            "data.start_date",

            "data.end_date",

            "data.total_seasons",

            "data.total_episodes",

            "data.also_known_as",

            "data.long_description",

            "data.short_description",

            "data.original_country",

            "data.original_language",

            "data.original_network",

            "data.genre_tags",

            "data.external_mappings.imdb_id",

            "data.tv_series_type"

    };

    private static String[] esTalentFullCatalogSources = new String[] { "title",

            "parrot_id",

            "short_id",

            "display_name",

            "catalog_state",

            "data.main_genre",

            "data.tag",

            "data.type",

            "data.sub_genre",

            "data.release_year",

            "data.start_date",

            "data.end_date",

            "data.also_known_as",

            "data.long_description",

            "data.short_description",

            "data.original_country",

            "data.original_language",

            "data.original_network",

            "data.genre_tags",

            "data.external_mappings.imdb_id"

    };

    private static String[] esMovieFullCatalogSources = new String[] {

            "title",

            "parrot_id",

            "short_id",

            "display_name",

            "catalog_state",

            "data.tag",

            "data.type",

            "data.main_genre",

            "data.sub_genre",

            "data.release_year",

            "data.start_date",

            "data.end_date",

            "data.also_known_as",

            "data.long_description",

            "data.short_description",

            "data.original_country",

            "data.original_language",

            //"data.original_network",

            "data.genre_tags",

            "data.external_mappings.imdb_id"

    };

    public CatalogESService()
    {
    }

    /**
     * @param index
     * @param type
     * @param query
     * @return
     * @throws IOException
     */
    public String search(ElasticSearchIndex index, IndexType type, String query) throws IOException
    {
        Search.Builder searchBuilder = new Search.Builder(query).addIndex(index.toString()).addType(type.toString());
        SearchResult result = jestClient.execute(searchBuilder.build());
        return result.getJsonString();
    }

    public List<CatalogItem> searchCatalogItemsByQueryTerms(String contentType, List<String> parrotIds,
            List<Long> shortIds, List<String> catalogStates, List<String> titles)
    {
        return searchCatalogItemsByQueryTerms(getESIndex(contentType), parrotIds, shortIds, catalogStates, titles);
    }

    public List<CatalogItem> searchCatalogItemsByQueryTerms(ElasticSearchIndex esIndex, List<String> parrotIds,
            List<Long> shortIds, List<String> catalogStates, List<String> titles)
    {
        String[] esSources = getESSources(esIndex, false);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(ES_WINDOW_LIMIT).trackTotalHits(true);
        searchSourceBuilder = searchSourceBuilder.fetchSource(esSources, null);

        boolean matchAllQuery = true;

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (CollectionUtils.isNotEmpty(catalogStates))
        {
            boolQuery.must().add(QueryBuilders.termsQuery("catalog_state", catalogStates));
            matchAllQuery = false;
        }

        if (CollectionUtils.isNotEmpty(parrotIds))
        {
            boolQuery.must().add(QueryBuilders.termsQuery("parrot_id", parrotIds));
            matchAllQuery = false;

        }
        if (CollectionUtils.isNotEmpty(shortIds))
        {
            boolQuery.must().add(QueryBuilders.termsQuery("short_id.long", shortIds));
            matchAllQuery = false;

        }

        if (CollectionUtils.isNotEmpty(titles))
        {
            boolQuery.must().add(QueryBuilders.termsQuery("title", titles));
            matchAllQuery = false;
        }

        if (matchAllQuery)
        {
            boolQuery.must().add(QueryBuilders.matchAllQuery());
        }

        searchSourceBuilder = searchSourceBuilder.query(boolQuery);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(esIndex.toString())
                .setParameter(Parameters.SIZE, ES_WINDOW_LIMIT).setParameter(Parameters.SCROLL, SCROLL).build();

        JsonArray jsonHits = scrollAndFetch(search);

        return convertJsonSourcesToCatalogItem(jsonHits, esIndex);
    }

    protected String[] getESSources(ElasticSearchIndex esIndex, boolean fullCatalog)
    {
        switch (esIndex)
        {
        case SERIES_METADATA:
            return fullCatalog ? esTVFullCatalogSources : esTVFullCatalogSources;
        case TALENT_METADATA:
            return fullCatalog ? esTalentFullCatalogSources : esTalentFullCatalogSources;
        case MOVIE_METADATA:
            return fullCatalog ? esMovieFullCatalogSources : esMovieFullCatalogSources;
        }
        return fullCatalog ? esTVFullCatalogSources : null;
    }

    public ElasticSearchIndex getESIndex(String contentType)
    {
        ElasticSearchIndex esIndex = null;
        if (contentType.equalsIgnoreCase(ParrotConstants.TALENT))
        {
            esIndex = ElasticSearchIndex.TALENT_METADATA;
        }
        else if (contentType.equalsIgnoreCase(ParrotConstants.MOVIE))
        {
            esIndex = ElasticSearchIndex.MOVIE_METADATA;
        }
        else
        {
            esIndex = ElasticSearchIndex.SERIES_METADATA;
        }
        return esIndex;
    }

    public Class<? extends CatalogItem> getCatalogClass(ElasticSearchIndex esIndex)
    {
        switch (esIndex)
        {
        case SERIES_METADATA:
            return TVItem.class;
        case TALENT_METADATA:
            return TalentItem.class;
        case MOVIE_METADATA:
            return MovieItem.class;
        }
        return TVItem.class;
    }

    public List<CatalogItem> searchCatalogItems(ElasticSearchIndex esIndex, List<String> catalogStates)
    {
        String[] esSources = getESSources(esIndex, true);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(ES_WINDOW_LIMIT).trackTotalHits(true);
        searchSourceBuilder = searchSourceBuilder.fetchSource(esSources, null);

        QueryBuilder qb = CollectionUtils.isNotEmpty(catalogStates) ?
                QueryBuilders.termsQuery("catalog_state", catalogStates) :
                QueryBuilders.matchAllQuery();

        searchSourceBuilder = searchSourceBuilder.query(qb);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(esIndex.toString())
                .setParameter(Parameters.SCROLL, SCROLL).build();

        JsonArray jsonHits = scrollAndFetch(search);

        return convertJsonSourcesToCatalogItem(jsonHits, esIndex);

    }

    protected List<CatalogItem> convertJsonSourcesToCatalogItem(JsonArray jsonHits, ElasticSearchIndex esIndex)
    {
        JsonArray jsonMetadatas = new JsonArray();
        jsonHits.forEach(jsonHit -> {
            jsonMetadatas.add(((JsonObject) jsonHit).getAsJsonObject("_source"));
        });
        Class<? extends CatalogItem> clazz = getCatalogClass(esIndex);
        GsonBuilder gsonBuilder = new GsonBuilder();
        RuntimeTypeAdapterFactory<BaseData> baseDataTypeAdapterFactory;
        switch (esIndex)
        {
        case SERIES_METADATA:
        default:
            baseDataTypeAdapterFactory = RuntimeTypeAdapterFactory.of(BaseData.class, "type")
                    .registerSubtype(TVData.class, StringUtils.capitalize(ParrotConstants.SHOW))
                    .registerSubtype(TVData.class, StringUtils.capitalize(ParrotConstants.MOVIE))
                    .registerSubtype(TVData.class, ParrotConstants.TV)
                    .registerSubtype(TVData.class, ParrotConstants.SHOW)
                    .registerSubtype(TVData.class, ParrotConstants.MOVIE).registerSubtype(TVData.class, "Sport");
            break;
        case TALENT_METADATA:
            baseDataTypeAdapterFactory = RuntimeTypeAdapterFactory.of(BaseData.class, "type")
                    .registerSubtype(TalentData.class, StringUtils.capitalize(ParrotConstants.TALENT))
                    .registerSubtype(TalentData.class, ParrotConstants.TALENT);
            break;
        case MOVIE_METADATA:
            baseDataTypeAdapterFactory = RuntimeTypeAdapterFactory.of(BaseData.class, "type")
                    .registerSubtype(MovieData.class, StringUtils.capitalize(ParrotConstants.MOVIE))
                    .registerSubtype(MovieData.class, ParrotConstants.MOVIE);
            break;
        }

        gsonBuilder.registerTypeAdapterFactory(baseDataTypeAdapterFactory);
        Gson gson = gsonBuilder.create();
        List<CatalogItem> catalogItems = new ArrayList<>();
        jsonMetadatas.forEach(jsonMetadata -> {
            try
            {
                catalogItems.add(gson.fromJson(jsonMetadata, clazz));
            }
            catch (Exception e)
            {
                logger.error("Failed to deserialized {}: {}", clazz, e.getMessage());
            }
        });

        return catalogItems;
    }

    protected JsonArray scrollAndFetch(Search search)
    {
        JsonArray jsonHits = new JsonArray();

        try
        {
            ClearScroll.Builder clearScroll = new ClearScroll.Builder();

            JestResult result = jestClient.execute(search);

            if (!result.isSucceeded())
            {
                logger.error("ES search result failed:{}", result.getErrorMessage());
                return new JsonArray();
            }
            int totalHits = result.getJsonObject().getAsJsonObject("hits").getAsJsonObject("total")
                    .getAsJsonPrimitive("value").getAsInt();
            jsonHits.addAll(result.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits"));

            String scrollId = result.getJsonObject().getAsJsonPrimitive(SCROLL_ID).getAsString();
            clearScroll = clearScroll.addScrollId(scrollId);

            if (jsonHits.size() < totalHits)
            {
                int i = 0;
                while (StringUtils.isNotEmpty(scrollId))
                {
                    logger.info("Scrolling {} items: #{}....", ES_WINDOW_LIMIT, ++i);
                    SearchScroll scroll = new SearchScroll.Builder(scrollId, SCROLL).build();
                    result = jestClient.execute(scroll);
                    if (result.isSucceeded()
                            && result.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits").size() > 0)
                    {
                        scrollId = result.getJsonObject().getAsJsonPrimitive(SCROLL_ID).getAsString();
                        clearScroll = clearScroll.addScrollId(scrollId);
                        jsonHits.addAll(result.getJsonObject().getAsJsonObject("hits").getAsJsonArray("hits"));
                    }
                    else
                    {
                        scrollId = null;
                    }

                }
            }
            jestClient.execute(clearScroll.build());

        }
        catch (IOException ex)
        {
            logger.error("Error executing ES query", ex);
        }
        logger.info("number of ES records {}", jsonHits.size());

        return jsonHits;
    }

    public CatalogItem searchMetadata(String parrotID, String contentType)
    {
        ElasticSearchIndex esIndex = getESIndex(contentType);
        if (esIndex == null)
        {
            return null;
        }
        List<CatalogItem> receivedItems = null;
        receivedItems = searchCatalogItemsByQueryTerms(esIndex, Arrays.asList(parrotID), null, null, null);
        if (CollectionUtils.size(receivedItems) == 1)
        {
            return receivedItems.get(0);
        }
        // there are about 800 movies got into series_metadata so we need a
        // patch up here
        if (contentType.equalsIgnoreCase(ParrotConstants.SHOW))
        {
            receivedItems = searchCatalogItemsByQueryTerms(ElasticSearchIndex.MOVIE_METADATA, Arrays.asList(parrotID),
                    null, null, null);
            if (CollectionUtils.size(receivedItems) == 1)
            {
                return receivedItems.get(0);
            }
        }
        return null;
    }

    public CatalogItem searchMetadata(Long shortId, String contentType)
    {
        ElasticSearchIndex esIndex = getESIndex(contentType);
        if (esIndex == null)
        {
            return null;
        }
        List<CatalogItem> receivedItems = null;
        receivedItems = searchCatalogItemsByQueryTerms(esIndex, null, Arrays.asList(shortId), null, null);
        if (CollectionUtils.size(receivedItems) == 1)
        {
            return receivedItems.get(0);
        }
        // there are about 800 movies got into series_metadata so we need a
        // patch up here
        if (contentType.equalsIgnoreCase(ParrotConstants.SHOW))
        {
            receivedItems = searchCatalogItemsByQueryTerms(ElasticSearchIndex.MOVIE_METADATA, null,
                    Arrays.asList(shortId), null, null);
            if (CollectionUtils.size(receivedItems) == 1)
            {
                return receivedItems.get(0);
            }
        }
        return null;
    }

    public List<CatalogItem> searchCatalogItemsByFilter(ElasticSearchIndex esIndex, MetadataESFilter filter,
            boolean fullCatalog)
    {
        String[] esSources = getESSources(esIndex, true);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(ES_WINDOW_LIMIT).trackTotalHits(true);
        searchSourceBuilder = searchSourceBuilder.fetchSource(esSources, null);

        QueryBuilder query = filter.buildQuery();

        logger.debug("Search catalog ES Query {}", query.toString());

        searchSourceBuilder = searchSourceBuilder.query(query);

        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(esIndex.toString())
                .setParameter(Parameters.SIZE, ES_WINDOW_LIMIT).setParameter(Parameters.SCROLL, SCROLL).build();

        JsonArray jsonHits = scrollAndFetch(search);

        return convertJsonSourcesToCatalogItem(jsonHits, esIndex);
    }

    public List<Long> searchShortIdsByFilter(ElasticSearchIndex esIndex, MetadataESFilter filter,
        List<Long> contentShortIDs)
    {
        List<CatalogItem> items = searchCatalogItemsByFilter(esIndex, filter, false);
        List<Long> filteredShortIds = items.stream().map(item -> item.getShortID()).collect(Collectors.toList());
        return contentShortIDs.stream().filter(filteredShortIds::contains).collect(Collectors.toList());
    }

}
