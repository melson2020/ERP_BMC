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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(IStorageUnitServiceImpl.class);
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
        //如果基础单位和转换单位一致 则基础单位转换数量=转换数量
        if(unit.getConvertUnit().equals(unit.getBaseUnit())){
            unit.setBaseUnitConvertCount(unit.getConvertCount());
            unit.setLevel(1);
        }else {
            //获取转换单位
           List<StorageUnit> packageUnitList=storageUnitRepository.findByProductIdAndPackageUnit(unit.getProductId(), unit.getConvertUnit());
           if(packageUnitList==null||packageUnitList.size()!=1){
               logger.error("保存转换单位失败，为能获取到[{}]对应的基础转换数据",unit.getConvertUnit());
               return null;
           }
           Integer baseUnitConvertNum=packageUnitList.get(0).getBaseUnitConvertCount()*unit.getConvertCount();
           unit.setBaseUnitConvertCount(baseUnitConvertNum);
           unit.setLevel(packageUnitList.get(0).getLevel()+1);
        }
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

    @Override
    public List<StorageUnit> FindPackageUnitList(Integer productId, String convertUnit) {
        return storageUnitRepository.findByProductIdAndConvertUnit(productId,convertUnit);
    }

    @Override
    public List<StorageUnit> FindUnPackageUnitList(Integer productId, String packageUnit) {
        return storageUnitRepository.findByProductIdAndPackageUnit(productId,packageUnit);
    }

}
