package com.melson.webserver.order.service.impl;

import com.melson.base.constants.SysConstants;
import com.melson.webserver.contract.dao.IContractRepository;
import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.contract.entity.ContractStock;
import com.melson.webserver.order.dao.IOrderFormRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.*;
import com.melson.webserver.order.vo.OrderFormConfirmVo;
import com.melson.webserver.order.vo.OrderFormInfoVo;
import com.melson.webserver.order.vo.OrderFormVo;
import com.melson.webserver.order.vo.OrderStateSummaryVo;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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
    @Autowired
    private IContractRepository contractRepository;

    @Override
    public OrderForm list(Integer contractId) {
        return orderFormRepository.findByContractId(contractId);
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
        Map<String, Integer> typeMap = new HashMap<>(4);
        typeMap.put("P", 1);
        typeMap.put("C", 2);
        typeMap.put("D", 3);
        typeMap.put("W", 4);
        //按照P C D W 排序
        detailList.sort(new Comparator<OrderFormDetail>() {
            @Override
            public int compare(OrderFormDetail o1, OrderFormDetail o2) {
                boolean res = typeMap.get(o1.getProduceType()) > typeMap.get(o2.getProduceType());
                return res ? -1 : 0;
            }
        });
        //生产清单
        List<OrderFormDetail> produceList = new ArrayList<>();
        //采购清单
        List<OrderFormDetail> purchaseList = new ArrayList<>();
        //委外清单
        List<OrderFormDetail> delegateList = new ArrayList<>();
        String orderType = "";
        for (OrderFormDetail detail : detailList) {
            if (!orderType.contains(detail.getProduceType())) {
                orderType= StringUtils.isEmpty(orderType)?detail.getProduceType():orderType+'/'+detail.getProduceType();
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
        orderFormDetailService.saveAll(detailList);
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
        orderForm.setCustomerNo(vendeeInfo.getCustomerNo());
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

    @Override
    public List<OrderStateSummaryVo> GetStateSummary() {
        List<OrderStateSummaryVo> list = new ArrayList<>();
        Integer contractCount = contractRepository.countByState(Contract.STATE_NEW);
        list.add(new OrderStateSummaryVo("合同确认", contractCount, "/contract"));
        List<Object[]> objects = orderFormRepository.getAllStatueCount();
        Map<String, Integer> stateMap = new HashMap<>();
        for (Object[] obj : objects) {
            String key = obj[0].toString();
            stateMap.put(key, Integer.parseInt(obj[1].toString()));
        }
        Integer orderConfirmCount = stateMap.get(OrderForm.STATE_CREATE) == null ? 0 : stateMap.get(OrderForm.STATE_CREATE);
        list.add(new OrderStateSummaryVo("订单下达", orderConfirmCount, "/release"));
        Integer processCount = stateMap.get(OrderForm.STATE_PROCESS) == null ? 0 : stateMap.get(OrderForm.STATE_PROCESS);
        list.add(new OrderStateSummaryVo("正在进行", processCount, ""));
        Integer deliveryCount = stateMap.get(OrderForm.STATE_DELIVERY) == null ? 0 : stateMap.get(OrderForm.STATE_DELIVERY);
        list.add(new OrderStateSummaryVo("待发货", deliveryCount, ""));
        return list;
    }

    @Override
    public List<OrderStateSummaryVo> GetProduceTypeSummary(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1Value = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1Value);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date beginDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endDate = calendar.getTime();

            List<Object[]> objects = orderFormRepository.getAllProduceTypeCount(sdf.format(beginDate), sdf.format(endDate));
            List<OrderStateSummaryVo> vos = new ArrayList<>();
            for (Object[] obj : objects) {
                String title = obj[0].toString();
                String newT = title.replace("D", "代工").replace("P", "生产").replace("C", "采购").replace("W", "委外");
                OrderStateSummaryVo vo = new OrderStateSummaryVo(newT, Integer.parseInt(obj[1].toString()), "");
                vos.add(vo);
            }
            return vos;
        } catch (Exception e) {
            logger.error("funcation  GetProduceTypeSummary error:" + e.getMessage());
            return null;
        }

    }

    @Override
    public List<OrderForm> GetOrderFormListByState(String state) {
        return orderFormRepository.findByState(state);
    }

    @Override
    public List<OrderForm> GetProcessingOrderForms() {
        return orderFormRepository.findByStateOrState(OrderForm.STATE_ORDER, OrderForm.STATE_PROCESS);
    }

    @Override
    public OrderFormInfoVo GetOrderFormInfo(Integer id) {
        OrderFormInfoVo infoVo = new OrderFormInfoVo();
        OrderForm orderForm = orderFormRepository.findById(id).orElse(null);
        List<OrderFormDetail> orderFormDetails = orderFormDetailService.findDetailListByFormId(orderForm.getId());
        ProducePlan producePlan = producePlanService.FindByOrderFormId(orderForm.getId());
        DelegateTicket delegateTicket = delegateTicketService.FindByOrderFormId(orderForm.getId());
        List<PurchaseDetail> purchaseDetailList = purchaseDetailService.FindByOrderFormId(orderForm.getId());
        infoVo.setOrderForm(orderForm);
        infoVo.setOrderFormDetailList(orderFormDetails);
        List<ProducePlan> producePlanList = new ArrayList<>();
        if (producePlan != null) {
            producePlanList.add(producePlan);
        }
        infoVo.setProducePlanList(producePlanList);
        List<DelegateTicket> delegateTicketList = new ArrayList<>();
        if (delegateTicket != null) {
            delegateTicketList.add(delegateTicket);
        }
        infoVo.setDelegateTicketList(delegateTicketList);
        infoVo.setPurchaseDetailList(purchaseDetailList);
        return infoVo;
    }
}
