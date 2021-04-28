package com.melson.webserver.contract.service.impl;

import com.melson.base.constants.SysConstants;
import com.melson.base.utils.DateUtil;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Override
    public List<ContractShowVo> list(String type, String contractNo, String orgName) {
//        return contractRepository.list(type, contractNo, orgName);
        List<Object[]> objects= contractRepository.findIntentionList(type);
        List<ContractShowVo> vos= EntityUtils.castEntity(objects, ContractShowVo.class, new ContractShowVo());
        return vos;
    }

    @Override
    public ContractInfoVo get(Integer id) {
        ContractInfoVo vo = new ContractInfoVo();
        // 合同资料
        Contract contract = contractRepository.getOne(id);
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
        Contract existContract = contractRepository.findByContractNo(contract.getContractNo());
        if (existContract != null) {
            return null;
        }
//        contract.setContractNo(DateUtil.timeFormat(date));
        contract.setType(contractType);
        contract.setCreateDate(date);
        contract.setCreateUser(userId);
        contract.setState(Contract.STATE_NEW);
        contractRepository.saveAndFlush(contract);
        // 买方
        ContractOrg purchaser = vo.getPurchaser();
        purchaser.setContractId(contract.getId());
        purchaser.setContractNo(contract.getContractNo());
        purchaser.setType(ContractOrg.TYPE_PURCHASER);
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
        List<ContractExtend> extendList = new ArrayList<>();
        List<ContractExtendEnum> extendEnumList = ContractExtendEnum.list();
        for (ContractExtendEnum extendEnum : extendEnumList) {
            String value = request.getParameter(extendEnum.getAlias());
            if (StringUtils.isEmpty(value)) {
                if (extendEnum.getRequired()) {
                    logger.error("字段[{}]-[{}]必填", extendEnum.getName(), extendEnum.getAlias());
                    return null;
                }
                value = StringUtils.EMPTY;
            }
            extendList.add(new ContractExtend(contract.getId(), extendEnum.getAlias(), value));
        }
        contractExtendRepository.saveAll(extendList);
        return contract;
    }

    @Override
    public Contract invalid(Integer id) {
        if (id == null) {
            logger.error("作废id为空");
            return null;
        }
        Contract contract = contractRepository.getOne(id);
        contract.setState(SysConstants.COMMON_DISABLE);
        contractRepository.saveAndFlush(contract);
        return contract;
    }

    @Override
    public Contract approve(Integer id, int userId) {
        if (id == null) {
            logger.error("作废id为空");
            return null;
        }
        Contract contract = contractRepository.getOne(id);
        if (Contract.TYPE_FORMAL.equals(contract.getType())) {
            logger.error("合同id[{}]已经是正式合同", id);
            return null;
        }
        Date date = new Date();
        Contract formalContract = new Contract();
        BeanUtils.copyProperties(contract, formalContract);
        formalContract.setId(null);
        formalContract.setContractNo(DateUtil.timeFormat(date));
        formalContract.setType(Contract.TYPE_FORMAL);
        formalContract.setCreateDate(date);
        formalContract.setCreateUser(userId);
        formalContract.setSourceId(contract.getId());
        contractRepository.saveAndFlush(formalContract);
        // todo : 转为正式合同后需要自动生成订单信息 add by wuhuan on 2021-04-20
        return formalContract;
    }
}
