package com.melson.webserver.order.service.impl;

import com.melson.base.utils.DateUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.delegate.vo.DelegateReleaseVo;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.vo.InventoryInboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.order.dao.IDelegateDetailRepository;
import com.melson.webserver.order.dao.IDelegateTicketRepository;
import com.melson.webserver.order.dao.IPickingTicketDetailRepository;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IDelegateTicketService;
import com.melson.webserver.order.service.IPickingTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/7
 */
@Service
public class DelegateTicketServiceImpl implements IDelegateTicketService {
    private static final Logger logger = LoggerFactory.getLogger(DelegateTicketServiceImpl.class);
    @Autowired
    private IDelegateTicketRepository delegateTicketRepository;
    @Autowired
    private IDelegateDetailRepository delegateDetailRepository;
    @Autowired
    private IPickingTicketService pickingTicketService;
    @Autowired
    private IPickingTicketDetailRepository pickingTicketDetailRepository;

    @Override
    /**
     * 订单委外 我方提供物料 故需要生成取料单
     */
    public DelegateTicket GenerateTicket(List<OrderFormDetail> detailList, OrderForm form) {
        if (detailList == null || detailList.size() <= 0) return null;
        DelegateTicket ticket = new DelegateTicket();
        ticket.setSourceId(form.getId());
        ticket.setSourceNo(form.getFormNo());
        ticket.setCustomerNo(form.getCustomerNo());
        ticket.setCustomerName(form.getCustomerName());
        ticket.setContractNo(form.getContractNo());
        ticket.setType(DelegateTicket.TYPE_ORDER);
        ticket.setTicketNo("W" + System.currentTimeMillis());
        ticket.setCreateDate(new Date());
        ticket.setState(DelegateTicket.STATE_CREATE);
        delegateTicketRepository.saveAndFlush(ticket);
        List<DelegateDetail> delegateDetails = new ArrayList<>();
        for (OrderFormDetail detail : detailList) {
            DelegateDetail delegateDetail = CreateDelegateDetail(detail, ticket);
            delegateDetails.add(delegateDetail);
        }
        delegateDetailRepository.saveAll(delegateDetails);
        PickingTicket pickingTicket= pickingTicketService.GeneratePickTicketWithOrderFormDetail(form,detailList);
        return ticket;
    }

    @Override
    public DelegateTicket GenerateTicketWithProcess(List<ProducePlanProcess> list, ProducePlan plan, List<ProducePlanDetail> detailList) {
        if (list == null || list.size() <= 0) return null;
        DelegateTicket ticket = new DelegateTicket();
        Map<Integer, ProducePlanDetail> detailIdMap = new HashMap<>(detailList.size());
        for (ProducePlanDetail detail : detailList) {
            detailIdMap.put(detail.getProductId(), detail);
        }
        ticket.setSourceId(plan.getId());
        ticket.setSourceNo(plan.getPlanNo());
        ticket.setCustomerNo(plan.getCustomerNo());
        ticket.setCustomerName(plan.getCustomerName());
        ticket.setContractNo(plan.getContractNo());
        ticket.setTicketNo("W" + System.currentTimeMillis());
        ticket.setCreateDate(new Date());
        ticket.setType(DelegateTicket.TYPE_PLAN);
        ticket.setState(DelegateTicket.STATE_CREATE);
        delegateTicketRepository.saveAndFlush(ticket);
        List<DelegateDetail> delegateDetails = new ArrayList<>();
        for (ProducePlanProcess p : list) {
            delegateDetails.add(CreateDelegateDetailWithProcess(p, ticket.getId(), detailIdMap.get(p.getProductId())));
        }
        delegateDetailRepository.saveAll(delegateDetails);
        return ticket;
    }

    @Override
    public DelegateTicket FindByOrderFormId(Integer orderFormId) {
        return delegateTicketRepository.findBySourceIdAndType(orderFormId,DelegateTicket.TYPE_ORDER);
    }

    @Override
    public DelegateTicket FindByPlanId(Integer planId) {
        return delegateTicketRepository.findBySourceIdAndType(planId,DelegateTicket.TYPE_PLAN);
    }

    @Override
    public List<DelegateTicket> FindByState(String state) {
        return delegateTicketRepository.findByState(state);
    }

    @Override
    public List<DelegateTicket> FindProcessingList() {
        return delegateTicketRepository.findByStateOrState(DelegateTicket.STATE_PROCESSING,DelegateTicket.STATE_PICKING);
    }

    @Override
    public List<DelegateTicket> FindRecordList(Map<String, String[]> kvMap) {
        List<DelegateTicket> recordList = delegateTicketRepository.findAll(new Specification<DelegateTicket>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<DelegateTicket> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"), "4"));
                for (String key : kvMap.keySet()) {
                    if (StringUtils.isEmpty(kvMap.get(key)[0])) continue;
                    if (key.contains("delegateDate")) {
                        try {
                            Date createDate = new SimpleDateFormat("yyyy-MM-dd").parse(kvMap.get(key)[0]);
                            predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get(key), createDate));
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(createDate);
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            Date newDate = calendar.getTime();
                            predicate.getExpressions().add(criteriaBuilder.lessThan(root.get(key), newDate));
                        } catch (ParseException e) {
                            continue;
                        }
                    } else {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get(key), kvMap.get(key)[0]));
                    }
                }
                return predicate;
            }
        });
        return recordList;
    }

    @Override
    public DelegateReleaseVo FindReleaseInfo(Integer ticketId) {
        DelegateReleaseVo releaseVo=new DelegateReleaseVo();
        DelegateTicket ticket=delegateTicketRepository.findById(ticketId).orElse(null);
        releaseVo.setDelegateTicket(ticket);
        List<DelegateDetail> ticketDetailList=delegateDetailRepository.findByDelegateTicketId(ticketId);
        releaseVo.setDelegateDetailList(ticketDetailList);
        PickingTicket pickingTicket=pickingTicketService.FindBySourceIdAndType(ticket.getSourceId(),ticket.getType());
        ticket.setPickingTicketNo(pickingTicket.getTicketNo());
        releaseVo.setPickingTicket(pickingTicket);
        List<PickingTicketDetail> pickingTicketDetailList=pickingTicketDetailRepository.findByTicketIdAndType(pickingTicket.getId(),OrderFormDetail.PRODUCE_TYPE_W);
        releaseVo.setPickingTicketDetailList(pickingTicketDetailList);
        return releaseVo;
    }

    @Override
    @Transactional
    public DelegateTicket DelegateTicketConfirm(DelegateReleaseVo releaseVo) {
        DelegateTicket ticket=releaseVo.getDelegateTicket();
        ticket.setState(DelegateTicket.STATE_PROCESSING);
        List<DelegateDetail> delegateDetails=releaseVo.getDelegateDetailList();
        delegateDetailRepository.saveAll(delegateDetails);
        return delegateTicketRepository.save(ticket);

    }

    @Override
    public InventoryInboundVo GenerateInventoryInBound(Integer ticketId,Integer userId) {
        DelegateTicket delegateTicket=delegateTicketRepository.findById(ticketId).orElse(null);
        if(delegateTicket==null){
            logger.info("创建入库单未找到委外单");
            return null;
        }
        List<Object[]> inBoundDetailList=delegateDetailRepository.findInBoundDetailList(ticketId);
        List<InventoryInboundDetailVo> detailVos= EntityUtils.castEntity(inBoundDetailList,InventoryInboundDetailVo.class,new InventoryInboundDetailVo());
        InventoryInboundVo inventoryInbound=new InventoryInboundVo();
        inventoryInbound.setFormNo(InventoryInbound.CODE_PREFIX+ System.currentTimeMillis());
        inventoryInbound.setBatchNo(DateUtil.timeShortFormat(new Date()));
        inventoryInbound.setType(InventoryInbound.TYPE_DELEGATE);
        inventoryInbound.setSourceNo(delegateTicket.getTicketNo());
        inventoryInbound.setSourceId(ticketId);
        inventoryInbound.setCreateDate(new Date());
        inventoryInbound.setCreateUser(userId);
        inventoryInbound.setSupplyId(delegateTicket.getSupplyId());
        inventoryInbound.setDetailVoList(detailVos);
        return inventoryInbound;
    }

    @Override
    public int UpdateStateById(String state,Integer id,String inboundTicketNo) {
        return delegateTicketRepository.updateStateById(state,inboundTicketNo,id);
    }


    private DelegateDetail CreateDelegateDetail(OrderFormDetail orderFormDetail, DelegateTicket ticket) {
        DelegateDetail delegateDetail = new DelegateDetail();
        delegateDetail.setDelegateTicketId(ticket.getId());
        delegateDetail.setBomNo(orderFormDetail.getBomNo());
        delegateDetail.setObjectName(orderFormDetail.getProductName());
        delegateDetail.setRemark(orderFormDetail.getRemark());
        delegateDetail.setSpecification(orderFormDetail.getSpecification());
        delegateDetail.setCount(new BigDecimal(orderFormDetail.getCount()));
        delegateDetail.setCountUnit(orderFormDetail.getCountUnit());
        delegateDetail.setObjectNo(orderFormDetail.getProductNo());
        return delegateDetail;
    }

    private DelegateDetail CreateDelegateDetailWithProcess(ProducePlanProcess process, Integer ticketId, ProducePlanDetail producePlanDetail) {
        DelegateDetail delegateDetail = new DelegateDetail();
        delegateDetail.setDelegateTicketId(ticketId);
        delegateDetail.setObjectName(process.getProcessName());
        delegateDetail.setRemark(producePlanDetail.getRemark());
        delegateDetail.setSpecification(producePlanDetail.getSpecification());
        delegateDetail.setCount(producePlanDetail.getCount());
        delegateDetail.setCountUnit(producePlanDetail.getCountUnit());
        delegateDetail.setObjectNo(process.getProcessNo());
        delegateDetail.setBomNo(producePlanDetail.getBomNo());

        //TODO:DelegateDetail添加materialNo字段
        delegateDetail.setMaterialInfo(process.getMaterialName() + "x" + process.getMaterialCount());
        return delegateDetail;

    }
}
