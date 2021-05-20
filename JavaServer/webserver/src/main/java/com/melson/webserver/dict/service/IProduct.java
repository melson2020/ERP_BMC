package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.ProductBom;
import com.melson.webserver.dict.vo.ContractProductVo;
import com.melson.webserver.dict.vo.GroupProductVo;
import com.melson.webserver.dict.vo.ProductVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IProduct extends IService<Product> {
    List<ContractProductVo> queryContractProducts(String customerNo, String searchValue);

    List<Product> findAllProduct();

    Result SaveAndUpdate(Product product);

    Integer DeleteProduct(Integer id);


    Product Query(String productNo);

    Product QueryProductAndBomList(String productNo);

    List<GroupProductVo> GetProductVoList(String productNo);
}
