package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

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

    @Query(value = "SELECT * from product where name =?1 and categoryId=?2 and specification=?3 and supplyId=?4",nativeQuery = true)
    Product findByCeriteria(String name, String category, String specification, Integer supplyId);

    @Query(nativeQuery = true, value = "SELECT pr.productNo,pr.name,pr.specification,pr.categoryId,pc.`name` as category,pr.supplyId,pr.salesPrice,su.`name` as supplyName,null as bomNo,null as bomGenericId,CONCAT(pc.`name`,' / ',pr.`name`,' / ',pr.specification,' / ',su.`name`) as alias from product pr left JOIN supply su on pr.supplyId=su.id left JOIN product_category pc on pr.categoryId=pc.categoryId  WHERE pr.productNo not in (SELECT productNo from product_bom)  and pr.productNo<>?1")
    List<Object[]> findBySpecial(String productNo);

    @Modifying
    @Query(value = "update product set salesPrice=?1 where productNo=?2",nativeQuery = true)
    void UpdatePrice(BigDecimal costPrice,String materialNo);

    List<Product> findByNameLikeOrProductNoLikeOrSpecificationLike(String value1,String value2,String value3);
}
