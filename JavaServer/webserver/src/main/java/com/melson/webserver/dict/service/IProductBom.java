package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.ProductBom;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IProductBom extends IService<ProductBom> {
    List<ProductBom> findProductBoms(Integer productId);
}
