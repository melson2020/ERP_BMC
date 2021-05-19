package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.ProductBom;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IProductBom extends IService<ProductBom> {
    List<ProductBom> findProductBoms(String productNo);

    Result SaveAndUpdate(ProductBom pb);


    ProductBom UpdateProductBom(ProductBom pb);

    List<ProductBom> GetProductBomList();

    ProductBom Query(String bomNo);

    ProductBom QueryProductBomsDetailList(String bomNo);
}
