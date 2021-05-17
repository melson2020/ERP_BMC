package com.melson.webserver.contract.service.impl;

import com.melson.base.constants.SysConstants;
import com.melson.base.utils.DateUtil;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.contract.dao.IContractExtendRepository;
import com.melson.webserver.contract.dao.IContractOrgRepository;
import com.melson.webserver.contract.dao.IContractRepository;
import com.melson.webserver.contract.dao.IContractStockRepository;
import com.melson.webserver.contract.entity.Contract;
import com.melson.webserver.contract.entity.ContractExtend;
import com.melson.webserver.contract.entity.ContractOrg;
import com.melson.webserver.contract.entity.ContractStock;
import com.melson.webserver.contract.enums.ContractExtendEnum;
import com.melson.webserver.contract.service.IContractService;
import com.melson.webserver.contract.vo.ContractInfoVo;
import com.melson.webserver.contract.vo.ContractShowVo;
import com.melson.webserver.dict.vo.ProductVo;
import com.melson.webserver.order.service.IOrderFormService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 合同接口实现类
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
@Service
public class ContractServiceImpl implements IContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    private IContractRepository contractRepository;
    @Autowired
    private IContractOrgRepository contractOrgRepository;
    @Autowired
    private IContractStockRepository contractStockRepository;
    @Autowired
    private IContractExtendRepository contractExtendRepository;
    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private EntityManagerUtil entityManagerUtil;

    @Override
    public List<ContractShowVo> intentionList(String type) {
//        return contractRepository.list(type, contractNo, orgName);
        List<Object[]> objects = contractRepository.findIntentionList(type);
        List<ContractShowVo> vos = EntityUtils.castEntity(objects, ContractShowVo.class, new ContractShowVo());
        return vos;
    }

    @Override
    public ContractInfoVo get(Integer id) {
        ContractInfoVo vo = new ContractInfoVo();
        // 合同资料
        Contract contract = contractRepository.findById(id).orElse(null);
        vo.setContract(contract);
        // 合同公司
        List<ContractOrg> orgList = contractOrgRepository.findByContractId(id);
        if (orgList != null && !orgList.isEmpty()) {
            orgList.forEach(org -> {
                if (ContractOrg.TYPE_PURCHASER.equals(org.getType())) {
                    vo.setPurchaser(org);
                } else if (ContractOrg.TYPE_GOOD_RECEIVE.equals(org.getType())) {
                    vo.setGoodReceiveInfo(org);
                } else if (ContractOrg.TYPE_VENDOR_CONFIRM.equals(org.getType())) {
                    vo.setVendorConfirm(org);
                } else if (ContractOrg.TYPE_PURCHASER_CONFIRM.equals(org.getType())) {
                    vo.setPurchaserConfirm(org);
                }
            });
        }
        // 合同货物
        List<ContractStock> stockList = contractStockRepository.findByContractId(id);
        vo.setProductList(stockList);
        // 合同额外属性
        List<ContractExtend> extendList = contractExtendRepository.findByContractId(id);
        if (extendList != null && !extendList.isEmpty()) {
            vo.setExt(new HashMap<>(extendList.size()));
            extendList.forEach(extend -> vo.getExt().put(extend.getCode(), extend.getValue()));
        }
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Contract save(HttpServletRequest request, String contractType, ContractInfoVo vo, int userId) {
        boolean saveCheck = vo.saveCheck();
        if (!saveCheck) {
            return null;
        }

        Date date = new Date();
        // 合同资料
        Contract contract = new Contract();
        BeanUtils.copyProperties(vo.getContract(), contract);
        //防止重复创建
        if (contract.getId() == null) {
            Contract existContract = contractRepository.findByContractNo(contract.getContractNo());
            if (existContract != null) {
                return null;
            }
        }
//        contract.setContractNo(DateUtil.timeFormat(date));
        contract.setType(contractType);
        contract.setCreateDate(date);
        contract.setCreateUser(userId);
        contract.setState(Contract.STATE_NEW);
        contract.setFormalDate(date);
        contractRepository.saveAndFlush(contract);
        // 买方
        ContractOrg purchaser = vo.getPurchaser();
        purchaser.setContractId(contract.getId());
        purchaser.setContractNo(contract.getContractNo());
        purchaser.setType(ContractOrg.TYPE_PURCHASER);
        purchaser.setCustomerNo(purchaser.getCustomerNo());
        contractOrgRepository.saveAndFlush(purchaser);
        // 收货单位
        ContractOrg goodReceiveInfo = vo.getGoodReceiveInfo();
        goodReceiveInfo.setContractId(contract.getId());
        goodReceiveInfo.setContractNo(contract.getContractNo());
        goodReceiveInfo.setType(ContractOrg.TYPE_GOOD_RECEIVE);
        contractOrgRepository.saveAndFlush(goodReceiveInfo);
        // 合同产品集合
        List<ContractStock> productList = vo.getProductList();
        productList.forEach(product -> product.setContractId(contract.getId()));
        contractStockRepository.saveAll(productList);
        // 卖方确认
        ContractOrg vendorConfirm = vo.getVendorConfirm();
        vendorConfirm.setContractId(contract.getId());
        vendorConfirm.setContractNo(contract.getContractNo());
        vendorConfirm.setType(ContractOrg.TYPE_VENDOR_CONFIRM);
        contractOrgRepository.saveAndFlush(vendorConfirm);
        // 买方确认
        ContractOrg purchaserConfirm = vo.getPurchaserConfirm();
        purchaserConfirm.setContractId(contract.getId());
        purchaserConfirm.setContractNo(contract.getContractNo());
        purchaserConfirm.setType(ContractOrg.TYPE_PURCHASER_CONFIRM);
        contractOrgRepository.saveAndFlush(purchaserConfirm);
        // 额外属性
//        List<ContractExtend> extendList = new ArrayList<>();
//        List<ContractExtendEnum> extendEnumList = ContractExtendEnum.list();
//        for (ContractExtendEnum extendEnum : extendEnumList) {
//            String value = request.getParameter(extendEnum.getAlias());
//            if (StringUtils.isEmpty(value)) {
//                if (extendEnum.getRequired()) {
//                    logger.error("字段[{}]-[{}]必填", extendEnum.getName(), extendEnum.getAlias());
//                    return null;
//                }
//                value = StringUtils.EMPTY;
//            }
//            extendList.add(new ContractExtend(contract.getId(), extendEnum.getAlias(), value));
//        }
//        contractExtendRepository.saveAll(extendList);
        return contract;
    }

    @Override
    public Contract invalid(Integer id) {
        if (id == null) {
            logger.error("作废id为空");
            return null;
        }
        Contract contract = contractRepository.findById(id).orElse(null);
        contract.setState(SysConstants.COMMON_DISABLE);
        contractRepository.saveAndFlush(contract);
        return contract;
    }

    @Override
    @Transactional
    public Contract approve(Integer id, int userId) {
        if (id == null) {
            logger.error("合同approve id为空");
            return null;
        }
        Contract contract = contractRepository.findById(id).orElse(null);
        ;
        if (Contract.TYPE_FORMAL.equals(contract.getType())) {
            logger.error("合同id[{}]已经是正式合同", id);
            return null;
        }
//        Date date = new Date();
//        Contract formalContract = new Contract();
//        BeanUtils.copyProperties(contract, formalContract);
//        formalContract.setId(null);
//        formalContract.setContractNo(DateUtil.timeFormat(date));
//        formalContract.setType(Contract.TYPE_FORMAL);
//        formalContract.setCreateDate(date);
//        formalContract.setCreateUser(userId);
//        formalContract.setSourceId(contract.getId());
        contract.setFormalDate(new Date());
        contract.setType(Contract.TYPE_FORMAL);
        contract.setState(Contract.STATE_PROCESSING);
        contractRepository.saveAndFlush(contract);
        //合同转正成功 创建对应的订单
        List<ContractStock> stockList=contractStockRepository.findByContractId(contract.getId());
        ContractOrg vendeeInfo=contractOrgRepository.findByContractIdAndType(contract.getId(),ContractOrg.TYPE_PURCHASER);
        orderFormService.create(contract,vendeeInfo,stockList);
        return contract;
    }

    @Override
    public List<ContractShowVo> findFormalList(Map<String, String[]> kvMap) {
        StringBuffer baseSql = new StringBuffer("select c.id,c.orderTicketNo,c.contractNo,co.name ,c.createDate,u.userName,c.type,c.state,c.formalDate from contract c RIGHT JOIN contract_org co on c.id=co.contractId RIGHT JOIN `user` u on c.createUser =u.id where c.type='2' and co.type='1'");
        for (String key : kvMap.keySet()) {
            if (StringUtils.isEmpty(kvMap.get(key)[0])) continue;
            if (key.contains("customerName")) {
                baseSql.append(" and co.name like '%" + kvMap.get(key)[0] + "%'");
            } else {
                baseSql.append(" and c." + key + " like '%" + kvMap.get(key)[0] + "%'");
            }

        }
        List<Object[]> list = entityManagerUtil.ExcuteSql(baseSql.toString());
        List<ContractShowVo> vos = EntityUtils.castEntity(list, ContractShowVo.class, new ContractShowVo());
        return vos;
    }

    @Override
    public void DeleteContractStock(Integer id) {
        contractStockRepository.deleteById(id);
    }
}
