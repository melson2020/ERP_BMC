package com.melson.webserver.inventory.service.impl;

import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.inventory.dao.IInventoryStocktakingDetailRepository;
import com.melson.webserver.inventory.dao.IInventoryStocktakingRepository;
import com.melson.webserver.inventory.entity.InventoryStocktaking;
import com.melson.webserver.inventory.entity.InventoryStocktakingDetail;
import com.melson.webserver.inventory.service.IInventoryStocktakingService;
import com.melson.webserver.inventory.vo.InventoryStocktakingDetailVo;
import com.melson.webserver.inventory.vo.InventoryStocktakingVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 盘点接口实现类
 *
 * @author wuhuan
 * @date 2021年05月15日
 */
@Service
public class InventoryStocktakingServiceImpl implements IInventoryStocktakingService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryStocktakingServiceImpl.class);

    @Autowired
    private IInventoryStocktakingRepository inventoryStocktakingRepository;
    @Autowired
    private IInventoryStocktakingDetailRepository inventoryStocktakingDetailRepository;
    @Autowired
    private IStorageDetailRepository storageDetailRepository;

    @Override
    public List<InventoryStocktakingVo> list(Date date) {
        List<InventoryStocktaking> list = inventoryStocktakingRepository.findByCreateDateAfter(date);
        if (list == null || list.isEmpty()) {
            logger.error("盘点单为空");
            return null;
        }
        List<InventoryStocktakingVo> voList = new ArrayList<>();
        list.forEach(form -> {
            InventoryStocktakingVo vo = new InventoryStocktakingVo();
            BeanUtils.copyProperties(form, vo);
            voList.add(vo);
        });
        return voList;
    }

    @Override
    public InventoryStocktakingVo get(String formNo) {
        if (StringUtils.isEmpty(formNo)) {
            logger.error("formNo为空");
            return null;
        }
        InventoryStocktaking form = inventoryStocktakingRepository.findByFormNo(formNo);
        if (form == null) {
            logger.error("formNo[{}]无效", formNo);
            return null;
        }
        InventoryStocktakingVo formVo = new InventoryStocktakingVo();
        BeanUtils.copyProperties(form, formVo);
        List<InventoryStocktakingDetail> detailList = inventoryStocktakingDetailRepository.findByFormNo(formNo);
        if (detailList != null && !detailList.isEmpty()) {
            List<InventoryStocktakingDetailVo> voList = new ArrayList<>();
            detailList.forEach(detail -> {
                InventoryStocktakingDetailVo detailVo = new InventoryStocktakingDetailVo();
                BeanUtils.copyProperties(detail, detailVo);
                voList.add(detailVo);
            });
            formVo.setDetailVoList(voList);
        }
        return formVo;
    }

    @Override
    public InventoryStocktaking save(InventoryStocktaking inventoryStocktaking){
         String formNo=InventoryStocktaking.CODE_PREFIX+System.currentTimeMillis();
         inventoryStocktaking.setFormNo(formNo);

    }
}
