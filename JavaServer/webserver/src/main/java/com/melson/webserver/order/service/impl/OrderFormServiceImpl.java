package com.melson.webserver.order.service.impl;

import com.melson.base.constants.SysConstants;
import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.order.dao.IOrderFormRepository;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.service.IOrderFormService;
import com.melson.webserver.order.vo.OrderFormVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 订单接口实现类
 *
 * @author wuhuan
 * @date 2021年04月27日
 */
@Service
public class OrderFormServiceImpl implements IOrderFormService {

    private static final Logger logger = LoggerFactory.getLogger(OrderFormServiceImpl.class);

    @Autowired
    private IOrderFormRepository orderFormRepository;

    @Override
    public List<OrderForm> list(Integer contractId) {
        return orderFormRepository.findByContractId(contractId);
    }

    @Override
    public OrderFormVo get(Integer id) {
        OrderFormVo vo = new OrderFormVo();
        OrderForm orderForm = orderFormRepository.getOne(id);
        BeanUtils.copyProperties(orderForm, vo);
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderForm save(OrderFormVo vo, int userId) {
        boolean saveCheck = vo.saveCheck();
        if (!saveCheck) {
            return null;
        }
        Date date = new Date();
        OrderForm orderForm = new OrderForm();
        BeanUtils.copyProperties(vo, orderForm);
        orderForm.setCreateDate(date);
        orderForm.setCreateUser(userId);
        orderFormRepository.saveAndFlush(orderForm);
        return orderForm;
    }

    @Override
    public OrderForm create(Contract contract) {
        OrderForm orderForm = new OrderForm();
        String formNo = MessageFormat.format("O{0}-{1}", OrderForm.TYPE_SELF, UUID.randomUUID().toString());
        orderForm.setFormNo(formNo);
        orderForm.setContractId(contract.getId());
        orderForm.setType(OrderForm.TYPE_SELF);
        orderForm.setState(OrderForm.STATE_ORDER);
        orderForm.setCreateDate(contract.getCreateDate());
        orderForm.setCreateUser(contract.getCreateUser());
        orderFormRepository.saveAndFlush(orderForm);
        return orderForm;
    }

    @Override
    public OrderForm invalid(Integer id) {
        if (id == null) {
            logger.error("作废id为空");
            return null;
        }
        OrderForm orderForm = orderFormRepository.getOne(id);
        orderForm.setState(SysConstants.COMMON_DISABLE);
        orderFormRepository.saveAndFlush(orderForm);
        return orderForm;
    }
}
