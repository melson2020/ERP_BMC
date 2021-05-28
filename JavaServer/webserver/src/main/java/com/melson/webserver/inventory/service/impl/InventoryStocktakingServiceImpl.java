package com.melson.webserver.inventory.service.impl;

import com.melson.base.utils.NumUtil;
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
    public InventoryStocktaking save(InventoryStocktakingVo vo, Integer userId) {
        // 1.保存盘点单
        InventoryStocktaking form = new InventoryStocktaking();
        BeanUtils.copyProperties(vo, form);
        Date date = new Date();
        form.setCreateDate(date);
        form.setCreateUser(userId);
        inventoryStocktakingRepository.saveAndFlush(form);
        form.setFormNo(NumUtil.incrementCode(InventoryStocktaking.CODE_PREFIX, form.getId()));
        inventoryStocktakingRepository.saveAndFlush(form);
        // 2.保存盘点明细
        if (vo.getDetailVoList() == null || vo.getDetailVoList().isEmpty()) {
            logger.error("盘点明细为空");
            return form;
        }
        List<InventoryStocktakingDetail> detailList = new ArrayList<>();
        vo.getDetailVoList().forEach(detailVo -> {
            InventoryStocktakingDetail detail = new InventoryStocktakingDetail();
            BeanUtils.copyProperties(detailVo, detail);
            detail.setFormNo(form.getFormNo());
            detailList.add(detail);
        });
        inventoryStocktakingDetailRepository.saveAll(detailList);
        // 3.修改库存
        // todo : 关联库存表更新 by wuhuan
        return form;
    }
}
