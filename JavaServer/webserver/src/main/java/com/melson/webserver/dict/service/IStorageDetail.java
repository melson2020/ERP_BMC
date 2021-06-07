package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.entity.InventoryInboundDetail;
import com.melson.webserver.inventory.entity.StorageUnit;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Messi on 2021/5/10
 */
public interface IStorageDetail extends IService<StorageDetail> {
    StorageDetail findByProductMaterialNo(String productNo);

    Integer DeleteStorage(String materialNo);

    /**
     * 根据searchValue 查询 name 和 storageCode 相似的数据
     * @param searchValue
     * @return
     */
    Page<StorageDetail> FindAllStorageList(String searchValue, Integer page, Integer pageSize);

    StorageDetail SaveOneWhileNotExist(StorageUnit unit);

    StorageDetail FindByProductIdAndUnit(Integer productId,String unit);

    void DeleteOne(StorageDetail storageDetail);

    List<StorageDetail> InventoryIn(InventoryInbound inbound,List<InventoryInboundDetail> details);
}
