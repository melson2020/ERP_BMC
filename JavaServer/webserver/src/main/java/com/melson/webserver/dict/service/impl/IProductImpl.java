package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IProductRepository;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.service.IProduct;
import com.melson.webserver.dict.vo.ContractProductVo;
import com.melson.webserver.dict.vo.CustomerVo;
import com.melson.webserver.dict.vo.ProductVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductImpl extends AbstractService<Product> implements IProduct {
    private final IProductRepository productRepository;
    private final EntityManagerUtil entityManagerUtil;

    public IProductImpl(IProductRepository productRepository, EntityManagerUtil entityManagerUtil) {
        this.productRepository = productRepository;
        this.entityManagerUtil = entityManagerUtil;
    }

    @Override
    public JpaRepository getRepository() {
        return productRepository;
    }

    @Override
    public List<ContractProductVo> queryContractProducts(String customerNo, String searchValue) {
        List<ContractProductVo> contractProductList=new ArrayList<>();
        String sql = "SELECT pd.id, cb.productId,pd.`name` as productName,pd.specification,CONCAT(pd.`name`, '/', pd.specification, '/', cb.description) as vaule,cb.remark,cb.salesPrice from customer_bom cb\n" +
                "left JOIN product pd on cb.productId=pd.productId\n" +
                "left join product_bom pb on pb.bomNo=cb.bomNo";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where cb.customerNo='" + customerNo + "'");
        if (!org.springframework.util.StringUtils.isEmpty(searchValue)) {
            String likeStr = "%" + searchValue + "%";
            sBuffer.append(" and (pd.`name` like '" + likeStr + "' or pd.specification like '" + likeStr + "')");
        }
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<ProductVo> productVos= EntityUtils.castEntity(list, ProductVo.class, new ProductVo());
        ContractProductVo contractProductVo1 = new ContractProductVo();
        contractProductVo1.setGroupName("常用产品");
        contractProductVo1.setList(productVos);
        String sql2 = "SELECT id, productId,`name`,specification, CONCAT(`name`, '/', specification) as vaule,remark,salesPrice from product";
        StringBuffer sBuffer2 = new StringBuffer(sql2);
        if (!org.springframework.util.StringUtils.isEmpty(searchValue)) {
            String likeStr = "%" + searchValue + "%";
            sBuffer2.append(" where `name` like '" + likeStr + "' or specification like '" + likeStr + "'");
        }
        List<Object[]> list2 = entityManagerUtil.ExcuteSql(sBuffer2.toString());
        List<ProductVo> productVos2= EntityUtils.castEntity(list2, ProductVo.class, new ProductVo());
        ContractProductVo contractProductVo2 = new ContractProductVo();
        contractProductVo2.setGroupName("产品列表");
        contractProductVo2.setList(productVos2);
        contractProductList.add(contractProductVo1);
        contractProductList.add(contractProductVo2);
        return contractProductList;
    }
}
