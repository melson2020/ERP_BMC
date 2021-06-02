package com.melson.webserver.inventory.vo;

import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.inventory.entity.StorageUnit;

import java.util.List;

/**
 * @Author Nelson
 * @Description 配置storage 包装单位信息载体
 * @Date 2021/6/1
 */
public class StorageUnitVo {
    private Product product;
    private List<StorageUnit> storageUnitList;

    public StorageUnitVo(Product product, List<StorageUnit> storageUnitList) {
        this.product = product;
        this.storageUnitList = storageUnitList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<StorageUnit> getStorageUnitList() {
        return storageUnitList;
    }

    public void setStorageUnitList(List<StorageUnit> storageUnitList) {
        this.storageUnitList = storageUnitList;
    }
}
