package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProductBom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IProductBomRepository extends JpaRepository<ProductBom,String> {
    List<ProductBom> findByProductNo(String productNo);

    @Modifying
    @Query(value = "update product_bom set `status`='N' where id=?1",nativeQuery = true)
    Integer disableCustomer(Integer id);

    @Modifying
    @Query(value = "update product_bom set `status`='Y' where id=?1",nativeQuery = true)
    Integer enableCustomer(Integer id);

    ProductBom findByBomNo(String bomNo);
}
