package com.parrotanalytics.api.services;

import com.parrotanalytics.api.apidb_model.nonmanaged.GenreEntityCount;
import com.parrotanalytics.api.apidb_model.nonmanaged.InGenreRank;
import com.parrotanalytics.api.data.repo.api.CatalogGeneRepository;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.request.ingenreperformance.InGenrePerformanceDataQuery;
import com.parrotanalytics.api.request.ingenreperformance.InGenrePerformanceRequest;
import com.parrotanalytics.api.response.ingenreperformance.InGenrePerformance;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InGenrePerformanceService {

  @Autowired
  private DemandRepository demandDataRepo;

  @Autowired
  private CatalogGeneRepository catalogGeneRepo;

  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

  static {
    decimalFormat.setRoundingMode(RoundingMode.FLOOR);
  }

  /**
   * retrieve in genre ranks and compute percentile
   * @param apiRequest
   * @param dataQuery
   * @return
   */
  public List<InGenrePerformance> getInGenrePerformances(InGenrePerformanceRequest apiRequest,
      InGenrePerformanceDataQuery dataQuery) {
    List<InGenreRank> inGenreRanks = demandDataRepo.getInGenreRank(
        apiRequest.getDateRangeList(), dataQuery.getMarketStr(), dataQuery.getShortIDsList()
            .get(0), dataQuery.getEntityEnum());
    Map<String, Integer> genreEntityCounts = catalogGeneRepo.getGenreEntityCount(
            dataQuery.getEntityEnum()).stream()
        .collect(Collectors.toMap(GenreEntityCount::getGenre, GenreEntityCount::getCount));

    List<InGenrePerformance> inGenrePerformances = inGenreRanks.stream()
        .map(inGenreRank -> calculateGenrePerformance(inGenreRank, genreEntityCounts))
        .collect(Collectors.toList());

    // sort result
    String order = apiRequest.getOrder();
    Comparator<InGenrePerformance> orderComparator = Comparator.comparing(
        InGenrePerformance::getPercentile);
    if (StringUtils.equalsIgnoreCase(order, "desc")) {
      orderComparator = orderComparator.reversed();
    }
    inGenrePerformances.sort(orderComparator);
    return inGenrePerformances;
  }


  /**
   * @param inGenreRank
   * @param genreEntityCounts
   */
  private InGenrePerformance calculateGenrePerformance(InGenreRank inGenreRank,
      Map<String, Integer> genreEntityCounts) {
    if (inGenreRank.getRank() == null || inGenreRank.getRank() == 0
        || genreEntityCounts.get(inGenreRank.getGenre()) == null) {
      throw new IllegalArgumentException("Invalid inGenreRank or unknown genre: " + inGenreRank);
    }
    double percentile =
        100 - (100.0 * inGenreRank.getRank() / genreEntityCounts.get(inGenreRank.getGenre()));
    percentile = Math.max(0, Math.min(percentile, 100));
    // round to 2 decimals
    percentile = Double.parseDouble(decimalFormat.format(percentile));
    InGenrePerformance performance = new InGenrePerformance();
    performance.setGenre(inGenreRank.getGenre());
    performance.setPercentile(percentile);
    return performance;
  }
}
