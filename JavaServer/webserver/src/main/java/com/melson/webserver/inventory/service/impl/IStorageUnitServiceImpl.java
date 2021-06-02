package com.melson.webserver.inventory.service.impl;

import com.melson.base.Result;
import com.melson.webserver.dict.dao.IProductRepository;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IProduct;
import com.melson.webserver.dict.service.IStorageDetail;
import com.melson.webserver.inventory.dao.IStorageUnitRepository;
import com.melson.webserver.inventory.entity.StorageUnit;
import com.melson.webserver.inventory.service.IStorageUnitService;
import com.melson.webserver.inventory.vo.StorageUnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/6/1
 */
@Service
public class IStorageUnitServiceImpl implements IStorageUnitService {
    @Autowired
    private IStorageUnitRepository storageUnitRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IStorageDetail storageDetailService;

    @Override
    public StorageUnitVo FindStorageUnitListByProductId(Integer productId) {
        Product product = productRepository.findById(productId).orElse(null);
        List<StorageUnit> list = storageUnitRepository.findByProductId(productId);
        return new StorageUnitVo(product, list);
    }

    @Override
    public StorageUnit SaveOne(StorageUnit unit) {
        StorageUnit saved= storageUnitRepository.save(unit);
        storageDetailService.SaveOneWhileNotExist(saved);
        return saved;
    }

    @Override
    public Result DeleteOne(StorageUnit unit) {
        StorageDetail existDetail=storageDetailService.FindByProductIdAndUnit(unit.getProductId(),unit.getPackageUnit());
        if(existDetail==null){
            return new Result(-1,"未查询到对应的库存信息，请联系管理员");
        }
        if(existDetail.getCount()<=0){
            storageDetailService.DeleteOne(existDetail);
            storageUnitRepository.delete(unit);
            return new Result(existDetail);
        }else {
            return new Result(-1,"此包装单位尚有库存，无法删除");
        }
    }

}
