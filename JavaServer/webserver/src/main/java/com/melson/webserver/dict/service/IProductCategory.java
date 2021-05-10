package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.ProductCategory;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IProductCategory extends IService<ProductCategory> {

    List<ProductCategory> findAll();

    Result SaveAndUpdate(ProductCategory productCategory);

    Integer DisableCustomer(ProductCategory productCategory);

    Integer DeleteCategory(Integer id);

    ProductCategory Query(String categoryId);
}
