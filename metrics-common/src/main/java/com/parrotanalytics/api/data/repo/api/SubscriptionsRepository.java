package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.apidb_model.ProductSpec;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.data.repo.api.custom.SubscriptionsRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Extension of {@link PagingAndSortingRepository} & implementation of {@link
 * SubscriptionsRepositoryCustom} to provide all the required data about licensed markets & content
 * titles to any of the REST API controllers.
 *
 * @author Sanjeev Sharma
 * @since v1
 */
@Repository
public interface SubscriptionsRepository
    extends PagingAndSortingRepository<Subscription, String>, SubscriptionsRepositoryCustom {

  @Query(value = "SELECT s from ProductSpec s WHERE s.idAccount = :accountId AND s.idProduct = :idProduct ")
  List<ProductSpec> getAccountConfigs(@Param("accountId") Integer accountId,
      @Param("idProduct") Integer idProduct);

}
