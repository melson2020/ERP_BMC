package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.utils.EntityUtils;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.webserver.dict.dao.ICustomerRepository;
import com.melson.webserver.dict.dao.IDeliveryAddressRepository;
import com.melson.webserver.dict.dao.ITaxRateRepository;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.DeliverAddress;
import com.melson.webserver.dict.entity.TaxRate;
import com.melson.webserver.dict.service.ICustomer;
import com.melson.webserver.dict.vo.ContractVo;
import com.melson.webserver.dict.vo.CustomerVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerImpl extends AbstractService<Customer> implements ICustomer {
   private final ICustomerRepository customerRepository;
   private final EntityManagerUtil entityManagerUtil;
   private final ITaxRateRepository taxRateRepository;
   private final IDeliveryAddressRepository deliveryAddressRepository;

    public ICustomerImpl(ICustomerRepository customerRepository, EntityManagerUtil entityManagerUtil, ITaxRateRepository taxRateRepository, IDeliveryAddressRepository deliveryAddressRepository) {
        this.customerRepository = customerRepository;
        this.entityManagerUtil = entityManagerUtil;
        this.taxRateRepository = taxRateRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerVo> findVoAll() {
        String sql = "SELECT customerNo,`name` from customer";
        StringBuffer buffer = new StringBuffer(sql);
        String excuteSql = buffer.toString();
        List<Object[]> list = entityManagerUtil.ExcuteSql(excuteSql);
        List<CustomerVo> vos = EntityUtils.castEntity(list, CustomerVo.class, new CustomerVo());
        return vos;
    }

    @Override
    public Customer findCustomerByCustomerNo(String customerNo) {
        Customer customer=customerRepository.findByCustomerNo(customerNo);
        return null;
    }

    @Override
    public ContractVo findCustomerVoByCustomerNo(String customerNo) {
        ContractVo contractVo=new ContractVo();
        Customer customer=customerRepository.findByCustomerNo(customerNo);
        List<TaxRate> taxRates=taxRateRepository.findAll();
        List<DeliverAddress> deliverAddresses=deliveryAddressRepository.findByCustomerNo(customerNo);
        contractVo.setCustomer(customer);
        contractVo.setDeliverAddresses(deliverAddresses);
        contractVo.setTaxRates(taxRates);
        return contractVo;
    }
}
