package com.melson.webserver.inventory.service.impl;

import com.melson.base.utils.DateUtil;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.service.IStorageDetail;
import com.melson.webserver.inventory.dao.IInventoryInboundDetailRepository;
import com.melson.webserver.inventory.dao.IInventoryInboundRepository;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.entity.InventoryInboundDetail;
import com.melson.webserver.inventory.service.IInventoryInboundService;
import com.melson.webserver.inventory.vo.InventoryInboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.service.IDelegateTicketService;
import com.melson.webserver.order.service.IProducePlanService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 入库接口实现类
 *
 * @author wuhuan
 * @date 2021年05月03日
 */
@Service
public class InventoryInboundServiceImpl implements IInventoryInboundService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryInboundServiceImpl.class);

    @Autowired
    private IInventoryInboundRepository inventoryInboundRepository;
    @Autowired
    private IInventoryInboundDetailRepository inventoryInboundDetailRepository;
    @Autowired
    private IDelegateTicketService delegateTicketService;
    @Autowired
    private IStorageDetail storageDetailService;
    @Autowired
    private IProducePlanService producePlanService;

    @Override
    public List<InventoryInboundVo> list(Date date) {
        List<InventoryInbound> list = inventoryInboundRepository.findByCreateDateAfter(date);
        if (list == null || list.isEmpty()) {
            logger.error("入库单为空");
            return null;
        }
        List<InventoryInboundVo> voList = new ArrayList<>();
        list.forEach(form -> {
            InventoryInboundVo vo = new InventoryInboundVo();
            BeanUtils.copyProperties(form, vo);
            voList.add(vo);
        });
        return voList;
    }

    @Override
    public InventoryInboundVo get(String formNo) {
        if (StringUtils.isEmpty(formNo)) {
            logger.error("formNo为空");
            return null;
        }
        InventoryInbound form = inventoryInboundRepository.findByFormNo(formNo);
        if (form == null) {
            logger.error("formNo[{}]无效", formNo);
            return null;
        }
        InventoryInboundVo formVo = new InventoryInboundVo();
        BeanUtils.copyProperties(form, formVo);
        List<InventoryInboundDetail> detailList = inventoryInboundDetailRepository.findByFormNo(formNo);
        if (detailList != null && !detailList.isEmpty()) {
            List<InventoryInboundDetailVo> voList = new ArrayList<>();
            detailList.forEach(detail -> {
                InventoryInboundDetailVo detailVo = new InventoryInboundDetailVo();
                BeanUtils.copyProperties(detail, detailVo);
                voList.add(detailVo);
            });
            formVo.setDetailVoList(voList);
        }
        return formVo;
    }

    @Override
    @Transactional
    public InventoryInbound save(InventoryInboundVo vo, Integer userId) {
        // 1.保存入库单
        InventoryInbound form = new InventoryInbound();
        BeanUtils.copyProperties(vo, form);
        Date date = new Date();
        form.setCreateDate(date);
        form.setCreateUser(userId);
        inventoryInboundRepository.saveAndFlush(form);
        form.setFormNo(NumUtil.incrementCode(InventoryInbound.CODE_PREFIX, form.getId()));
        inventoryInboundRepository.saveAndFlush(form);
        // 2.保存入库明细
        if (vo.getDetailVoList() == null || vo.getDetailVoList().isEmpty()) {
            logger.error("入库明细为空");
            return form;
        }
        List<InventoryInboundDetail> detailList = new ArrayList<>();
        vo.getDetailVoList().forEach(detailVo -> {
            InventoryInboundDetail detail = new InventoryInboundDetail();
            BeanUtils.copyProperties(detailVo, detail);
            detail.setFormNo(form.getFormNo());
            detailList.add(detail);
        });
        List<InventoryInboundDetail> details = inventoryInboundDetailRepository.saveAll(detailList);
        // 3.修改库存
        storageDetailService.InventoryIn(form, details);
        UpdateSourceTicketState(form);
        return form;
    }

    @Override
    public InventoryInboundVo createInBoundWithTicket(Integer ticketId, String ticketType, Integer userId) {
        switch (ticketType) {
            case InventoryInbound.TYPE_DELEGATE:
                return delegateTicketService.GenerateInventoryInBound(ticketId, userId);
            case InventoryInbound.TYPE_PRODUCE:
                return producePlanService.GenerateInventoryInBound(ticketId, userId);
        }
        return null;
    }

    private void UpdateSourceTicketState(InventoryInbound inbound) {
        switch (inbound.getType()) {
            case InventoryInbound.TYPE_DELEGATE:
                delegateTicketService.UpdateStateById(DelegateTicket.STATE_COMPLETE, inbound.getSourceId(),inbound.getFormNo());
                break;
            default:break;
        }
    }
}
