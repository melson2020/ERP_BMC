package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    ProductCategory findByName(String name);

    @Modifying
    @Query(value = "update product_category set `status`='N' where id=?1",nativeQuery = true)
    Integer disableCustomer(Integer id);

    @Modifying
    @Query(value = "DELETE from product_category WHERE id =?1",nativeQuery = true)
    Integer deleteContactById(Integer id);

    @Query(value = "SELECT * from product_category where categoryId=?1",nativeQuery = true)
    ProductCategory findByCategoryId(String categoryId);
}
