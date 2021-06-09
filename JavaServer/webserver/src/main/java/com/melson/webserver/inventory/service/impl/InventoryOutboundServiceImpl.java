package com.melson.webserver.inventory.service.impl;

import com.melson.base.utils.NumUtil;
import com.melson.webserver.inventory.dao.IInventoryOutboundDetailRepository;
import com.melson.webserver.inventory.dao.IInventoryOutboundRepository;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.entity.InventoryOutbound;
import com.melson.webserver.inventory.entity.InventoryOutboundDetail;
import com.melson.webserver.inventory.service.IInventoryOutboundService;
import com.melson.webserver.inventory.vo.InventoryOutboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryOutboundVo;
import com.melson.webserver.order.service.IPickingTicketService;
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
 * 出库接口实现类
 *
 * @author wuhuan
 * @date 2021年05月10日
 */
@Service
public class InventoryOutboundServiceImpl implements IInventoryOutboundService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryOutboundServiceImpl.class);

    @Autowired
    private IInventoryOutboundRepository inventoryOutboundRepository;
    @Autowired
    private IInventoryOutboundDetailRepository inventoryOutboundDetailRepository;
    @Autowired
    private IPickingTicketService pickingTicketService;

    @Override
    public List<InventoryOutboundVo> list(Date date) {
        List<InventoryOutbound> list = inventoryOutboundRepository.findByCreateDateAfter(date);
        if (list == null || list.isEmpty()) {
            logger.error("出库单为空");
            return null;
        }
        List<InventoryOutboundVo> voList = new ArrayList<>();
        list.forEach(form -> {
            InventoryOutboundVo vo = new InventoryOutboundVo();
            BeanUtils.copyProperties(form, vo);
            voList.add(vo);
        });
        return voList;
    }

    @Override
    public InventoryOutboundVo get(String formNo) {
        if (StringUtils.isEmpty(formNo)) {
            logger.error("formNo为空");
            return null;
        }
        InventoryOutbound form = inventoryOutboundRepository.findByFormNo(formNo);
        if (form == null) {
            logger.error("formNo[{}]无效", formNo);
            return null;
        }
        InventoryOutboundVo formVo = new InventoryOutboundVo();
        BeanUtils.copyProperties(form, formVo);
        List<InventoryOutboundDetail> detailList = inventoryOutboundDetailRepository.findByFormNo(formNo);
        if (detailList != null && !detailList.isEmpty()) {
            List<InventoryOutboundDetailVo> voList = new ArrayList<>();
            detailList.forEach(detail -> {
                InventoryOutboundDetailVo detailVo = new InventoryOutboundDetailVo();
                BeanUtils.copyProperties(detail, detailVo);
                voList.add(detailVo);
            });
            formVo.setDetailVoList(voList);
        }
        return formVo;
    }

    @Override
    public InventoryOutbound save(InventoryOutboundVo vo, Integer userId) {
        // 1.保存出库单
        InventoryOutbound form = new InventoryOutbound();
        BeanUtils.copyProperties(vo, form);
        Date date = new Date();
        form.setCreateDate(date);
        form.setCreateUser(userId);
        inventoryOutboundRepository.saveAndFlush(form);
        form.setFormNo(NumUtil.incrementCode(InventoryOutbound.CODE_PREFIX,form.getId()));
        inventoryOutboundRepository.saveAndFlush(form);
        // 2.保存出库明细
        if (vo.getDetailVoList() == null || vo.getDetailVoList().isEmpty()) {
            logger.error("出库明细为空");
            return form;
        }
        List<InventoryOutboundDetail> detailList = new ArrayList<>();
        vo.getDetailVoList().forEach(detailVo -> {
            InventoryOutboundDetail detail = new InventoryOutboundDetail();
            BeanUtils.copyProperties(detailVo, detail);
            detail.setFormNo(form.getFormNo());
            detailList.add(detail);
        });
        inventoryOutboundDetailRepository.saveAll(detailList);
        // 3.修改库存
        // todo : 关联库存表扣减 by wuhuan
        return form;
    }

    @Override
    public InventoryOutboundVo GenerateOutBoundWithExistTicket(Integer ticketId,String ticketType,Integer userId) throws RuntimeException {
        switch (ticketType) {
            case InventoryOutbound.TYPE_PICKING:
                return pickingTicketService.GenerateInventoryOutBound(ticketId, userId);
            case InventoryOutbound.TYPE_DELIVERY:
                return null;

        }
        return null;
    }
}
