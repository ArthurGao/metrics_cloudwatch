package com.parrotanalytics.metrics.service.services;


import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.LiveAffinityIndex;
import com.parrotanalytics.api.apidb_model.LiveDatasourceMetrics;
import com.parrotanalytics.api.apidb_model.comparators.DisplayNameSort;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.model.DataPoint;
import com.parrotanalytics.api.data.repo.api.ContextMetricRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.response.customerapi.CustomerTitle;
import com.parrotanalytics.api.services.DemandService;
import com.parrotanalytics.api.services.UserService;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TVItem;
import com.parrotanalytics.metrics.commons.OverallDemandRequest;
import com.parrotanalytics.metrics.commons.OverallDemandResponse;
import com.parrotanalytics.metrics.commons.OverallDemandResponse.DemandData;
import com.parrotanalytics.metrics.commons.OverallDemandResponse.DemandData.Builder;
import com.parrotanalytics.metrics.commons.OverallDemandServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class OverallDemandService extends OverallDemandServiceGrpc.OverallDemandServiceImplBase {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private DemandService demandService;

    @Autowired
    protected SubscriptionsRepository subscriptionsRepo;

    @Autowired
    protected ContextMetricRepository contextMetricRepo;

    @Autowired
    private MetadataCache metadataCache;

    @Autowired
    private UserService userService;

    @Override
    public void getOverallDemand(OverallDemandRequest request, StreamObserver<OverallDemandResponse> responseObserver) {

        Integer page = request.getPage();
        String emailAddress = request.getMetricType();
        Account account = null;
        OverallDemandResponse.Builder responseBuilder = OverallDemandResponse.newBuilder();
        try {
            account = userService.loadAppUserByEmail(emailAddress).getAccount();
            List<CatalogItem> accountTitles = subscriptionsRepo.getAccountTitles(account, Entity.TV_SERIES);

            Builder dataBuilder;
            for(CatalogItem catalogItem : accountTitles){
                dataBuilder = DemandData.newBuilder();
             //   dataBuilder.setOverallRank((int) catalogItem.getDemand().getUs());
                dataBuilder.setParrotId(catalogItem.getParrotID());
                dataBuilder.setMarket(catalogItem.getCountry() == null ? "Test" : catalogItem
                    .getCountry());
                dataBuilder.setLabel(catalogItem.getDisplayName());
                dataBuilder.setOverallRank(200);
                responseBuilder.addData(dataBuilder.build());
            }

        } catch (APIException e) {
        }
        //Builder dataBuilder = DemandData.newBuilder();
        //dataBuilder.setOverallRank(100);
        OverallDemandResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public List<CustomerTitle> asCustomerTitles(List<CatalogItem> items,
        List<String> metadataFields) {
        List<Long> shortIDs = items.stream().map(item -> item.getShortID())
            .collect(Collectors.toList());
        Map<Long, List<LiveAffinityIndex>> mapShortID2AffinityIndices = new HashMap<>();
        boolean hasAffinityField = metadataFields.contains("affinity");
        boolean hasImdbRating = metadataFields.contains("imdb_rating");
        boolean hasGenreTags = metadataFields.contains("genre_tags");
        boolean hasOriginalCountry = metadataFields.contains("original_country");
        boolean hasStartDate = metadataFields.contains("start_date");
        boolean hasEndDate = metadataFields.contains("end_date");
        boolean hasOriginalLanguage = metadataFields.contains("original_language");
        boolean hasOriginalNetwork = metadataFields.contains("original_network");
        boolean hasReleaseType = metadataFields.contains("release_type");
        boolean hasShortDescription = metadataFields.contains("short_description");
        boolean hasLongDescription = metadataFields.contains("long_description");
        boolean hasMinimumDemandDate = metadataFields.contains("minimum_demand_date");
        boolean hasReleaseYear = metadataFields.contains("release_year");
        //TODO :  contextMetricController.getLiveAffinityIndices is removed
      /*  if (hasAffinityField) {
            // prevent to call this expensive affinity call if not needed
            List<LiveAffinityIndex> liveAffinityIndexList = contextMetricController.getLiveAffinityIndices(
                true,
                shortIDs);
            liveAffinityIndexList.sort(Comparator.comparingInt(LiveAffinityIndex::getRank));
            mapShortID2AffinityIndices = liveAffinityIndexList.stream()
                .collect(Collectors.groupingBy(l -> l.getShort_id_from()));
        }

       */
        Map<Long, List<LiveDatasourceMetrics>> mapShortID2ImdbRatings = new HashMap<>();
        if (hasImdbRating) {
            List<LiveDatasourceMetrics> datasourceMetricsList = contextMetricRepo
                .getLiveDataSourceMetricsByMetrics(shortIDs, Arrays.asList("imdb"),
                    Arrays.asList("rating"));
            mapShortID2ImdbRatings = datasourceMetricsList.stream()
                .collect(Collectors.groupingBy(l -> l.getShort_id()));
        }
        Map<Long, List<LiveAffinityIndex>> finalMapShortID2AffinityIndices = mapShortID2AffinityIndices;
        Map<Long, List<LiveDatasourceMetrics>> finalMapShortID2ImdbRatings = mapShortID2ImdbRatings;
        return items.stream().map(item -> {
            if (!(item instanceof TVItem)) {
                return null;
            }
            TVItem tvItem = (TVItem) item;
            CustomerTitle customerTitle = new CustomerTitle().withParrotId(item.getParrotID())
                .withImdbId(item.getImdbId()).withTitle(item.getDisplayName());
            if (CollectionUtils.isEmpty(metadataFields)) {
                String genres = CollectionUtils.isNotEmpty(item.getGenres()) ? item.getGenres().stream()
                    .filter(s -> !StringUtils.isEmpty(s) && !s.equals(tvItem.getSubGenre())).map(s -> s)
                    .collect(Collectors.joining(",")) : null;
                customerTitle = customerTitle.withGenres(genres).withSubgenres(tvItem.getSubGenre());
            } else {
                try {
                    if (hasAffinityField) {
                        if (finalMapShortID2AffinityIndices.containsKey(item.getShortID())) {
                            List<LiveAffinityIndex> affinityIndexList = finalMapShortID2AffinityIndices
                                .get(item.getShortID());

                            if (CollectionUtils.isNotEmpty(affinityIndexList)) {
                                List<DataPoint> dataPoints = affinityIndexList.stream().map(l -> {

                                    CatalogItem cI = metadataCache.resolveItem(l.getShort_id_to());
                                    DataPoint dataPoint = new DataPoint();
                                    dataPoint.setParrotId(
                                        cI != null ? cI.getParrotID() : Long.toString(l.getShort_id_to()));
                                    dataPoint.setLabel(
                                        cI != null ? cI.getDisplayName() : Long.toString(l.getShort_id_to()));
                                    return dataPoint;
                                }).filter(p -> p != null).collect(Collectors.toList());
                                customerTitle.setAffinity(dataPoints);
                            }
                        }
                    }
                    if (hasImdbRating) {
                        if (MapUtils.isNotEmpty(finalMapShortID2ImdbRatings)
                            && finalMapShortID2ImdbRatings.containsKey(item.getShortID())) {
                            List<LiveDatasourceMetrics> datasourceMetricsList = finalMapShortID2ImdbRatings
                                .get(item.getShortID());
                            if (CollectionUtils.size(datasourceMetricsList) == 1) {
                                if (datasourceMetricsList.get(0).getValue() > 0.0) {
                                    customerTitle.setImdb_rating(datasourceMetricsList.get(0).getValue());
                                }
                            }
                        }
                    }
                    if (hasGenreTags) {
                        if (!Objects.isNull(item.getData())
                            && !org.apache.commons.collections4.CollectionUtils
                            .isEmpty(item.getData().getGenreTags())) {
                            String genreTags = item.getData().getGenreTags().stream().map(genreTag -> genreTag)
                                .collect(Collectors.joining(APIConstants.DELIM_COMMA));
                            customerTitle.setGenre_tags(genreTags);
                        }
                    }
                    if (hasOriginalCountry && !MapUtils.isEmpty(tvItem.getOriginalCountry())) {
                        if (tvItem.getOriginalCountry().firstEntry() != null) {
                            String countryIso = tvItem.getOriginalCountry().firstEntry().getValue();
                            customerTitle.setOriginal_country(countryIso);
                        }
                    }
                    if (hasStartDate && StringUtils.isNotEmpty(item.getStartDate())) {
                        customerTitle.setStartDate(item.getStartDate());
                    }
                    if (hasEndDate && StringUtils.isNotEmpty(item.getEndDate())) {
                        customerTitle.setEndDate(item.getEndDate());
                    }
                    if (hasOriginalLanguage && !MapUtils.isEmpty(tvItem.getOriginalLanguage())) {
                        if (tvItem.getOriginalLanguage().firstEntry() != null) {
                            String originalLanguage = tvItem.getOriginalLanguage().firstEntry().getValue();
                            customerTitle.setOriginal_language(originalLanguage);
                        }
                    }
                    if (hasOriginalNetwork && !MapUtils.isEmpty(tvItem.getOriginalNetwork())) {
                        if (tvItem.getOriginalNetwork().firstEntry().getValue() != null) {
                            String originalNetwork = tvItem.getOriginalNetwork().firstEntry().getValue();
                            customerTitle.setOriginal_network(originalNetwork);
                        }
                    }
                    if (hasReleaseYear && !StringUtils.isEmpty(tvItem.getReleaseYear())) {
                        customerTitle.setReleaseYear(tvItem.getReleaseYear());
                    }
                    if (hasReleaseType && !StringUtils.isEmpty(item.getReleaseType())) {
                        customerTitle.setReleaseType(item.getReleaseType());
                    }

                    if (hasShortDescription || items.size() == 1) {
                        customerTitle.setShortDescription(item.getShortDescription());
                    }
                    if (hasLongDescription || items.size() == 1) {
                        customerTitle.setLongDescription(item.getLongDescription());
                    }
                    if (hasMinimumDemandDate) {
                        customerTitle.setMinimumDemandDate(item.getRatingsAvailableFrom());
                    }

                } catch (NullPointerException ex) {
                    //logger.error("Failed to obtain catalog item", ex);
                }
            }
            return customerTitle;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
