package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.inventory.entity.*;
import com.melson.webserver.inventory.vo.StorageBatchVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

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

    List<StorageDetail> InventoryIn(InventoryInbound inbound,List<InventoryInboundDetail> details) throws RuntimeException;

    List<StorageDetail> InventoryOut(InventoryOutbound outVBound, List<InventoryOutboundDetail> details) throws RuntimeException;

    List<StorageBatchVo> FindStorageBatchInfo(Set<String> materialNos);

    List<StorageDetail> FindByProductIds(Set<Integer> productIds);

    List<StorageDetail> FindByProductId(Integer productId);
}
