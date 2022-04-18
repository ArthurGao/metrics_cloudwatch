package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.ProductSpec;

@Repository
public interface ProductSpecsRepository extends CrudRepository<ProductSpec, Integer>
{

    @Query("SELECT s from ProductSpec s WHERE s.idAccount = :idAccount")
    List<ProductSpec> getAccountModules(@Param("idAccount") Integer idAccount);

    @Query("SELECT s from ProductSpec s WHERE s.idAccount = :idAccount AND s.idProduct = :idProduct")
    List<ProductSpec> getAccountModulesByProdut(@Param("idAccount") Integer idAccount,
            @Param("idProduct") Integer idProduct);

    @Query("SELECT s from ProductSpec s WHERE s.idAccount = :idAccount AND s.idProduct = 1 AND s.specificationName = :specificationName")
    ProductSpec getDPContractValue(@Param("idAccount") Integer idAccount,
            @Param("specificationName") String specificationName);

    @Query("SELECT s from ProductSpec s WHERE s.idAccount = :idAccount AND s.idProduct = :idProduct")
    ProductSpec getModuleByAccountId(@Param("idAccount") Integer idAccount, @Param("idProduct") Integer idProduct);

}
