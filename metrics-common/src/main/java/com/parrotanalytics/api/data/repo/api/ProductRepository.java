package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.Product;
import com.parrotanalytics.api.data.repo.api.custom.SubscriptionsRepositoryCustom;

/**
 * Extension of {@link PagingAndSortingRepository} & implementation of
 * {@link SubscriptionsRepositoryCustom} to provide all the required data about
 * licensed markets & content titles to any of the REST API controllers.
 * 
 * @author jackson
 * @since v1
 *
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>
{
    @Query("SELECT p from Product p WHERE p.status = 1")
    List<Product> loadAllProducts();

    @Query("SELECT p from Product p WHERE p.status = 1 AND p.access_type = 'account_level'")
    List<Product> loadAllAccountProducts();

    @Query("SELECT p from Product p WHERE p.idProduct = :idProduct")
    Product findProductById(@Param("idProduct") Integer idProduct);

}
