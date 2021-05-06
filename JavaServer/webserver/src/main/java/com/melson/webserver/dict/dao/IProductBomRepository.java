package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProductBom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IProductBomRepository extends JpaRepository<ProductBom,Integer> {
    List<ProductBom> findByProductId(String productId);
}
