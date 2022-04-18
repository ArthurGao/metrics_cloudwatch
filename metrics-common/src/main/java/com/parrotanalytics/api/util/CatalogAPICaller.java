package com.parrotanalytics.api.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.parrotanalytics.api.commons.gson.RuntimeTypeAdapterFactory;
import com.parrotanalytics.apicore.commons.constants.Service;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.BaseData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.MovieData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.SeasonItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVData;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVItem;
import com.parrotanalytics.apicore.utils.RestCaller;
import com.parrotanalytics.apicore.utils.ServiceCaller;
import com.parrotanalytics.commons.exceptions.service.ParrotServiceException;

/**
 * @author Sanjeev Sharma
 */
public class CatalogAPICaller
{
    public static String VERSION = "/v1";

    private static Type catalogListType = new TypeToken<List<TVItem>>()
    {
    }.getType();

    private static Type seasonListType = new TypeToken<List<SeasonItem>>()
    {
    }.getType();

    public static String rawMetadataPayload(List<String> parrotIDs) throws APIException
    {
        String metadataJSON;

        if (CollectionUtils.isEmpty(parrotIDs))
        {
            Map<String, List<String>> queryParams = new HashMap<String, List<String>>();
            queryParams.put("internal", Arrays.asList("true"));
            metadataJSON = RestCaller.restCall(catalogAPIURL() + VERSION + "/catalog/tv?internal=true", HttpMethod.GET,
                    queryParams, null, null);
        }
        else
        {
            String queryIDs = String.join(",", parrotIDs);

            Map<String, List<String>> queryParams = new HashMap<String, List<String>>();
            queryParams.put("query", Arrays.asList(queryIDs));
            queryParams.put("internal", Arrays.asList("true"));

            metadataJSON = RestCaller.restCall(catalogAPIURL() + VERSION + "/metadata/tv?internal=true", HttpMethod.GET,
                    queryParams, null, null);
        }

        return metadataJSON;
    }

    public static List<SeasonItem> fetchSeasonItems(String parrotID, String seasonNum) throws APIException
    {
        List<SeasonItem> seasonItems = null;

        String metadataJSON = null;

        if (!StringUtils.isEmpty(parrotID))
        {
            Map<String, List<String>> queryParams = new HashMap<String, List<String>>();
            queryParams.put("query", Arrays.asList(parrotID));
            queryParams.put("details", Arrays.asList("true"));
            metadataJSON = RestCaller.restCall(catalogAPIURL() + VERSION + "/season", HttpMethod.GET, queryParams, null,
                    null);
        }

        if (!StringUtils.isEmpty(metadataJSON))
        {
            JsonObject rootJsonObject = new Gson().fromJson(metadataJSON, JsonObject.class);
            seasonItems = new Gson().fromJson(rootJsonObject.get("seasons"), seasonListType);
        }

        return seasonItems;
    }

    public static String catalogAPIURL()
    {
        return ServiceCaller.serviceURL(Service.CATALOG_API.value());
    }
}
