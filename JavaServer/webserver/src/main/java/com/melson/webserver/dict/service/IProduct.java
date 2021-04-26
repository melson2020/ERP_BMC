package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.vo.ContractProductVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IProduct extends IService<Product> {
    List<ContractProductVo> queryContractProducts(String customerNo, String searchValue);

}
