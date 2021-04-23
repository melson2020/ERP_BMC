package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Messi on 2021/4/23
 */
public interface IProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
}
