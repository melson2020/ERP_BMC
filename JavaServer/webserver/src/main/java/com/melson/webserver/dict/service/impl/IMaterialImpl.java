package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.webserver.dict.dao.IMaterialRepository;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.dict.entity.Material;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IMaterialImpl extends AbstractService<Material> implements IMaterial {
    private final IMaterialRepository materialRepository;
    private final IStorageDetailRepository storageDetailRepository;
    private final EntityManagerUtil entityManagerUtil;

    public IMaterialImpl(IMaterialRepository materialRepository, IStorageDetailRepository storageDetailRepository, EntityManagerUtil entityManagerUtil) {
        this.materialRepository = materialRepository;
        this.storageDetailRepository = storageDetailRepository;
        this.entityManagerUtil = entityManagerUtil;
    }

    @Override
    public JpaRepository getRepository() {
        return materialRepository;
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Result SaveAndUpdate(Material material) {
        Result result = new Result();
        Material checkExist = materialRepository.findByName(material.getName());
        if(checkExist!=null){
            if(material.getId()==checkExist.getId()){
                Material saved=materialRepository.save(material);
                if(saved==null){
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                }else {
                    StorageDetail checkStorageDetail=storageDetailRepository.findByMaterialNo(saved.getPartNo());
                    UpdateStorageTable(checkStorageDetail,saved);
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("已经存在此物料名称或联系管理员！");
            }
        }
        else
        {
            Material saved=materialRepository.save(material);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                StorageDetail checkStorageDetail=storageDetailRepository.findByMaterialNo(saved.getPartNo());
                UpdateStorageTable(checkStorageDetail,saved);
                result.setData(saved);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Integer DeleteMaterial(Integer id) {
        return materialRepository.deleteMaterialById(id);
    }

    @Override
    public Material Query(String partNo) {
        return materialRepository.findMaterialByPartNo(partNo);
    }

    @Override
    public List<Material> findAllList() {
        List<Material> materials= new ArrayList<>();
        String sql = "SELECT ma.id,ma.partNo,ma.`name`,ma.unit,stal.name as storageName,ma.manufacturer,ma.specification,ma.weight,ma.weightUnit,ma.volume,ma.volumeUnit,ma.semiGoods,ma.expireDate from material ma left join storage_area_location stal on ma.storageCode=stal.storageCode ORDER BY ma.id ASC";
        StringBuffer sBuffer = new StringBuffer(sql);
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        for(Object[] obj:list){
            Material pr=new Material();
            pr.setId(obj[0]==null?null:new Integer((Integer) obj[0]));
            pr.setPartNo(obj[1]==null?null:obj[1].toString());
            pr.setName(obj[2]==null?null:obj[2].toString());
            pr.setUnit(obj[3]==null?null:obj[3].toString());
            pr.setStorageName(obj[4]==null?null:obj[4].toString());
            pr.setManufacturer(obj[5]==null?null:obj[5].toString());
            pr.setSpecification(obj[6]==null?null:obj[6].toString());
            pr.setWeight(obj[7]==null?null:new BigDecimal(obj[7].toString()));
            pr.setWeightUnit(obj[8]==null?null:obj[8].toString());
            pr.setVolume(obj[9]==null?null:new BigDecimal(obj[9].toString()));
            pr.setVolumeUnit(obj[10]==null?null:obj[10].toString());
            pr.setSemiGoods(obj[11]==null?null:obj[11].toString());
            pr.setExpireDate(obj[12]==null?null:(Timestamp) obj[12]);
            materials.add(pr);
        }
        return materials;
    }


    private void UpdateStorageTable(StorageDetail checkStorageDetail, Material saved) {
        StorageDetail ne=new StorageDetail();   //同时保存或更新Storage表
        ne.setMaterialNo(saved.getPartNo());
        ne.setName(saved.getName());
        ne.setSpecification(saved.getSpecification());
        ne.setLastestPrice(saved.getCost());
        ne.setUnit(saved.getUnit());
        ne.setCount(0);
        ne.setStorageCode(saved.getStorageCode());
        ne.setManufacturer("");
        if(checkStorageDetail!=null)
        {
            ne.setId(checkStorageDetail.getId());
            ne.setManufacturer(checkStorageDetail.getManufacturer());
            ne.setCount(checkStorageDetail.getCount());
            ne.setFeature(checkStorageDetail.getFeature());
        }
        storageDetailRepository.save(ne);
    }
}
