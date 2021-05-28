package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.service.ISysSequence;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IProductCategoryRepository;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.ProductCategory;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import com.melson.webserver.dict.service.IProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductCategoryImpl extends AbstractService<ProductCategory> implements IProductCategory {
    private final IProductCategoryRepository productCategoryRepository;
    private final ISysSequence sysSequenceService;

    public IProductCategoryImpl(IProductCategoryRepository productCategoryRepository, ISysSequence sysSequenceService) {
        this.productCategoryRepository = productCategoryRepository;
        this.sysSequenceService = sysSequenceService;
    }

    @Override
    public JpaRepository getRepository() {
        return productCategoryRepository;
    }


    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public Result SaveAndUpdate(ProductCategory productCategory) {
        Result result = new Result();
        ProductCategory checkExist=productCategoryRepository.findByName(productCategory.getName());
        if(checkExist!=null){
            if(productCategory.getId()==checkExist.getId()){
                ProductCategory saved=productCategoryRepository.save(productCategory);
                if(saved==null){
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                }else {
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("已经存在此类别名称或联系管理员！");
            }
        }
        else
        {
//            if(productCategory.getId()==null) {
//                productCategory.setCategoryId(sysSequenceService.GenerateCode(ProductCategory.CATEGORY_NO_CHAR));
//            }
            ProductCategory saved=productCategoryRepository.save(productCategory);
            Integer length=8;
            productCategory.setCategoryId(NumUtil.incrementCode(productCategory.getId(), ProductCategory.CATEGORY_NO_CHAR,length));
            productCategoryRepository.save(productCategory);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                result.setData(saved);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Integer DisableCustomer(ProductCategory productCategory) {
        Integer count = productCategoryRepository.disableCustomer(productCategory.getId());
        return count;
    }

    @Override
    @Transactional
    public Integer DeleteCategory(Integer id) {
        return productCategoryRepository.deleteContactById(id);
    }

    @Override
    public ProductCategory Query(String categoryId) {
        return productCategoryRepository.findByCategoryId(categoryId);
    }
}
