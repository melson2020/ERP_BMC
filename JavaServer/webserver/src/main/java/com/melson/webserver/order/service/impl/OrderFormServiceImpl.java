package com.melson.webserver.order.service.impl;

import com.melson.base.constants.SysConstants;
import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.contract.entity.ContractStock;
import com.melson.webserver.order.dao.IOrderFormRepository;
import com.melson.webserver.order.entity.OrderForm;
import com.melson.webserver.order.entity.OrderFormDetail;
import com.melson.webserver.order.service.*;
import com.melson.webserver.order.vo.OrderFormConfirmVo;
import com.melson.webserver.order.vo.OrderFormVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

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
    @Autowired
    private IOrderFormDetailService orderFormDetailService;
    @Autowired
    private IProducePlanService producePlanService;
    @Autowired
    private IPurchaseDetailService purchaseDetailService;
    @Autowired
    private IDelegateTicketService delegateTicketService;

    @Override
    public OrderForm list(Integer contractId) {
        return orderFormRepository.findByContractId(contractId);
    }

    @Override
    public List<OrderForm> createdList() {
        return orderFormRepository.findByState(OrderForm.STATE_CREATE);
    }

    @Override
    public List<OrderFormDetail> detailList(Integer orderFormId) {
        return orderFormDetailService.findDetailListByFormId(orderFormId);
    }

    @Override
    @Transactional
    /**
     * 订单下达函数
     * 主逻辑：1：生成对应的 采购计划 生产计划 委外计划 2：更新orderForm 状态
     */
    public OrderForm confirm(OrderFormConfirmVo vo) {
        List<OrderFormDetail> detailList = vo.getOrderFormDetails();
        OrderForm formVo = vo.getOrderForm();
        //数据不全 直接返回
        if (detailList == null || detailList.size() <= 0) return null;
        //订单分类
        Map<String,Integer> typeMap=new HashMap<>(4);
        typeMap.put("P",1);
        typeMap.put("C",2);
        typeMap.put("D",3);
        typeMap.put("W",4);
        //按照P C D W 排序
        detailList.sort(new Comparator<OrderFormDetail>() {
            @Override
            public int compare(OrderFormDetail o1, OrderFormDetail o2) {
                boolean res=typeMap.get(o1.getProduceType())>typeMap.get(o2.getProduceType());
                return res?-1:0;
            }
        });
        //生产清单
        List<OrderFormDetail> produceList = new ArrayList<>();
        //采购清单
        List<OrderFormDetail> purchaseList = new ArrayList<>();
        //委外清单
        List<OrderFormDetail> delegateList = new ArrayList<>();
        String orderType="";
        for (OrderFormDetail detail : detailList) {
            if(!orderType.contains(detail.getProduceType())){
                orderType+=detail.getProduceType();
            }
            String type = detail.getProduceType();
            switch (type) {
                case OrderFormDetail.PRODUCE_TYPE_C:
                    purchaseList.add(detail);
                    break;
                case OrderFormDetail.PRODUCE_TYPE_W:
                    delegateList.add(detail);
                    break;
                default:
                    produceList.add(detail);
                    break;
            }
        }
        producePlanService.GeneratePlan(produceList, formVo);
        purchaseDetailService.GenerateOrderPurchase(purchaseList);
        delegateTicketService.GenerateTicket(delegateList, formVo);
        formVo.setState(OrderForm.STATE_ORDER);
        formVo.setProduceType(orderType);
        OrderForm form = orderFormRepository.save(formVo);
        return form;
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
    public OrderForm create(Contract contract, ContractOrg vendeeInfo, List<ContractStock> stockList) {
        OrderForm orderForm = orderFormRepository.findByContractId(contract.getId());
        if (orderForm == null) orderForm = new OrderForm();
        String formNo = contract.getOrderTicketNo();
        orderForm.setFormNo(formNo);
        orderForm.setContractId(contract.getId());
        orderForm.setType(OrderForm.TYPE_SELF);
        orderForm.setState(OrderForm.STATE_CREATE);
        orderForm.setContractNo(contract.getContractNo());
        orderForm.setCustomerName(vendeeInfo.getName());
        orderForm.setCreateDate(contract.getCreateDate());
        orderForm.setCreateUser(contract.getCreateUser());
        orderFormRepository.saveAndFlush(orderForm);
        orderFormDetailService.createDetailList(stockList, orderForm.getId());
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
