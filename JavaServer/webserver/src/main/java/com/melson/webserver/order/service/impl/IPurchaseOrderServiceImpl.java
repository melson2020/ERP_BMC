package com.melson.webserver.order.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.DateUtil;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.IProductRepository;
import com.melson.webserver.dict.dao.ISupplyRepository;
import com.melson.webserver.dict.entity.Supply;
import com.melson.webserver.inventory.entity.InventoryInbound;
import com.melson.webserver.inventory.vo.InventoryInboundDetailVo;
import com.melson.webserver.inventory.vo.InventoryInboundVo;
import com.melson.webserver.order.dao.*;
import com.melson.webserver.order.entity.*;
import com.melson.webserver.order.service.IPurchaseOrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Messi on 2021/6/5
 */
@Service
public class IPurchaseOrderServiceImpl extends AbstractService<PurchaseOrder> implements IPurchaseOrderService {
    private final IPurchaseOrderRepository purchaseOrderRepository;
    private final IPurchaseDetailRepository purchaseDetailRepository;
    private final IPurchasePlanRepository purchasePlanRepository;
    private final IProductRepository productRepository;
    private final ISupplyRepository supplyRepository;
    private final EntityManagerUtil entityManagerUtil;
    private final IPickingTicketRepository pickingTicketRepository;
    private final IPickingTicketDetailRepository pickingTicketDetailRepository;

    public IPurchaseOrderServiceImpl(IPurchaseOrderRepository purchaseOrderRepository, IPurchaseDetailRepository purchaseDetailRepository, IPurchasePlanRepository purchasePlanRepository, IProductRepository productRepository, ISupplyRepository supplyRepository, EntityManagerUtil entityManagerUtil, IPickingTicketRepository pickingTicketRepository, IPickingTicketDetailRepository pickingTicketDetailRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.purchasePlanRepository = purchasePlanRepository;
        this.productRepository = productRepository;
        this.supplyRepository = supplyRepository;
        this.entityManagerUtil = entityManagerUtil;
        this.pickingTicketRepository = pickingTicketRepository;
        this.pickingTicketDetailRepository = pickingTicketDetailRepository;
    }

    @Override
    public JpaRepository<PurchaseOrder, String> getRepository() {
        return null;
    }

    @Override
    public List<PurchaseOrder> GetAllCreatedPoList(String state) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday=sdf.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        String sql ="SELECT po.id,po.poNo,po.state,po.supplyId,po.deliverDay,po.payterm,po.createDate,po.createBy,po.description ,su.`name` as supplyName ,us.userName as createName ,po.amount FROM `purchase_order` po left JOIN supply su on po.supplyId=su.id LEFT JOIN `user` us on po.createBy=us.id ";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where po.state='" + state + "'");
        sBuffer.append("  and po.createDate>='" + yesterday + "' ORDER BY po.id desc ");
        List<Object[]> objects = entityManagerUtil.ExcuteSql(sBuffer.toString());
//        List<Object[]> objects=purchaseOrderRepository.findByStateWithName(state);
        List<PurchaseOrder> purchaseOrders=GeneratedList(objects);
        return purchaseOrders;
    }

    private List<PurchaseOrder> GeneratedList(List<Object[]> objects) {
        List<PurchaseOrder> list=new ArrayList<>();
        for (Object[] obj : objects) {
            PurchaseOrder po=new PurchaseOrder();
            po.setId(obj[0] == null ? null : new Integer((Integer) obj[0]));
            po.setPoNo(obj[1] == null ? null : obj[1].toString());
            po.setState(obj[2] == null ? null : obj[2].toString());
            po.setSupplyId(obj[3] == null ? null : new Integer((Integer) obj[3]));
            po.setDeliverDay(obj[4] == null ? null : obj[4].toString());
            po.setPayterm(obj[5] == null ? null : obj[5].toString());
            po.setCreateDate(obj[6]==null?null:(Timestamp) obj[6]);
            po.setCreateBy(obj[7] == null ? null : new Integer((Integer) obj[7]));
            po.setDescription(obj[8] == null ? null : obj[8].toString());
            po.setSupplyName(obj[9] == null ? null : obj[9].toString());
            po.setCreateName(obj[10] == null ? null : obj[10].toString());
            po.setAmount(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            list.add(po);
        }
        return list;
    }

    @Override
    @Transactional
    public Result Save(PurchaseOrder po) {
        Result result=new Result();
        PurchaseOrder saved=purchaseOrderRepository.save(po);
        List<PurchaseDetail> poDetails=po.getPurchaseDetailList();
        purchaseDetailRepository.saveAll(poDetails);
        for(PurchaseDetail detail:poDetails){
            purchasePlanRepository.UpdateState(detail.getState(),detail.getPurchasePlanNo());    //更新PR状态
            productRepository.UpdatePrice(detail.getCostPrice(),detail.getMaterialNo());         //更新product价格
        }
        result.setData(saved);
        return result;
    }

    @Override
    public PurchaseOrder QueryPo(PurchaseOrder po) {
        List<Object[]> objects=purchaseOrderRepository.findByPoNoWithSupplyName(po.getPoNo());
        List<PurchaseOrder> purchaseOrders=GeneratedList(objects);
        PurchaseOrder newPo=purchaseOrders.get(0);
        List<PurchaseDetail> details=purchaseDetailRepository.findByPoNo(po.getPoNo());
        Supply sup=supplyRepository.findById(newPo.getSupplyId());
        newPo.setPurchaseDetailList(details);
        newPo.setSupply(sup);
        return newPo;
    }

    @Override
    public List<PurchaseOrder> GetAllWithCreate() {
        List<Object[]> objects=purchaseOrderRepository.findAllWithCreate();
        List<PurchaseOrder> purchaseOrders=GeneratedList(objects);
        return purchaseOrders;
    }

    @Override
    public InventoryInboundVo GenerateInventoryInBound(Integer ticketId, Integer userId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(ticketId).orElse(null);
        List<Object[]> objects = purchaseDetailRepository.findInboundListByPlanNo(purchaseOrder.getPoNo());
        List<InventoryInboundDetailVo> detailVos = EntityUtils.castEntity(objects, InventoryInboundDetailVo.class, new InventoryInboundDetailVo());
        InventoryInboundVo inventoryInbound = new InventoryInboundVo();
        inventoryInbound.setFormNo(InventoryInbound.CODE_PREFIX + System.currentTimeMillis());
        inventoryInbound.setBatchNo(DateUtil.timeShortFormat(new Date()));
        inventoryInbound.setType(InventoryInbound.TYPE_PURCHASE);
        inventoryInbound.setSourceNo(purchaseOrder.getPoNo());
        inventoryInbound.setSourceId(ticketId);
        inventoryInbound.setCreateDate(new Date());
        inventoryInbound.setCreateUser(userId);
        inventoryInbound.setDetailVoList(detailVos);
        return inventoryInbound;
    }

    @Override
    @Transactional
    public void UpdateAfterInBound(String formNo, String sourceNo, Date createDate) {
        String state=PurchaseOrder.PO_STATE_COMPLETE;
        purchaseOrderRepository.updateInboundstate(formNo,state,createDate,sourceNo);
        purchaseDetailRepository.updateInboundsDetail(state,sourceNo);
        List<Object[]> details=purchaseDetailRepository.findPrByPoNo(sourceNo);
        Map<String,String> prMap=new HashMap<>();
        for(Object[] obj: details)
        {
            String key=obj[0].toString();
            String existInMap=prMap.get(key);
            if(existInMap==null){
                prMap.put(key,obj[1].toString());
            }
        }
        for(Map.Entry<String,String> entry:prMap.entrySet()){
            String prNo=entry.getKey();
            String prState=entry.getValue();
            List<Object[]> objects=purchaseDetailRepository.findObjectByPurchasePlanNo(prNo);
            Map<String,String> checkMap=new HashMap<>();
            for(Object[] obj: objects)
            {
                String checkState=obj[0].toString();
                String existInMap=checkMap.get(checkState);
                if(existInMap==null){
                    checkMap.put(checkState,prNo);
                }
            }
            if(checkMap.size()==1)
            {
                purchasePlanRepository.UpdateState(state,prNo);
                List<Object[]> pickObjects= purchaseDetailRepository.findPickingDetail(prNo);
                if(pickObjects.size()>0) {
                    List<PickingTicketDetail> pickingTicketDetails = generateList(pickObjects);
                    pickingTicketDetailRepository.saveAll(pickingTicketDetails);
                }
            }
        }
    }

    private List<PickingTicketDetail> generateList(List<Object[]> pickObjects) {
        List<PickingTicketDetail> list=new ArrayList<>();
        for(Object[] obj: pickObjects)
        {
            PickingTicketDetail pd=new PickingTicketDetail();
            pd.setType(obj[0] == null ? null : obj[0].toString());
            pd.setMaterialNo(obj[1] == null ? null : obj[1].toString());
            pd.setMaterialName(obj[2] == null ? null : obj[2].toString());
            pd.setSpecification(obj[3] == null ? null : obj[3].toString());
            pd.setRemark(obj[4] == null ? null : obj[4].toString());
            pd.setCount(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            pd.setCountUnit(obj[6] == null ? null : obj[6].toString());
            pd.setPurchasePlanNo(obj[7] == null ? null : obj[7].toString());
            pd.setCreateEmployeeNo(obj[8] == null ? null : obj[8].toString());
            pd.setCreateDate(obj[9]==null?null:(Timestamp) obj[9]);
            pd.setState(obj[10] == null ? null : obj[10].toString());
            pd.setDelegateFlag(obj[11] == null ? null : obj[11].toString());
            pd.setTicketId(obj[12] == null ? null : new Integer((Integer) obj[12]));
            pd.setProductId(obj[13] == null ? null : new Integer((Integer) obj[13]));
            list.add(pd);
        }
        return list;
    }
}
