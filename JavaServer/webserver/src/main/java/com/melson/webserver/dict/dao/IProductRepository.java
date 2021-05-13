package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);

    @Modifying
    @Query(value = "DELETE from product where id=?1",nativeQuery = true)
    Integer deleteProductById(Integer id);

    @Query(value = "SELECT * from product where productNo=?1",nativeQuery = true)
    Product findByProductNo(String productNo);
}
