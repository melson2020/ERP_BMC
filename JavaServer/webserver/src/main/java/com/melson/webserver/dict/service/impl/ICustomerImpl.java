package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.utils.EntityUtils;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.webserver.dict.dao.ICustomerContactRepository;
import com.melson.webserver.dict.dao.ICustomerRepository;
import com.melson.webserver.dict.dao.ITaxRateRepository;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.CustomerContact;
import com.melson.webserver.dict.vo.DeliverAddress;
import com.melson.webserver.dict.entity.TaxRate;
import com.melson.webserver.dict.service.ICustomer;
import com.melson.webserver.dict.vo.ContractVo;
import com.melson.webserver.dict.vo.CustomerVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerImpl extends AbstractService<Customer> implements ICustomer {
   private final ICustomerRepository customerRepository;
   private final EntityManagerUtil entityManagerUtil;
   private final ITaxRateRepository taxRateRepository;
   private final ICustomerContactRepository customerContactRepository;

    public ICustomerImpl(ICustomerRepository customerRepository, EntityManagerUtil entityManagerUtil, ITaxRateRepository taxRateRepository,  ICustomerContactRepository customerContactRepository) {
        this.customerRepository = customerRepository;
        this.entityManagerUtil = entityManagerUtil;
        this.taxRateRepository = taxRateRepository;
        this.customerContactRepository = customerContactRepository;
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
    public List<Customer> findAllExclude() {
        String sql = "SELECT id,customerNo,`name`,contactName,address,phone,taxNo,bankNo,payTerm,payWay,currency from customer where `status`='Y'";
        StringBuffer buffer = new StringBuffer(sql);
        String excuteSql = buffer.toString();
        List<Object[]> list = entityManagerUtil.ExcuteSql(excuteSql);
        List<Customer> customers=new ArrayList<>();
        for(Object[] obj:list){
            Customer cu=new Customer();
            cu.setId(obj[0]==null?null:new Integer((Integer) obj[0]));
            cu.setCustomerNo(obj[1]==null?null:obj[1].toString());
            cu.setName(obj[2]==null?null:obj[2].toString());
            cu.setContactName(obj[3]==null?null:obj[3].toString());
            cu.setAddress(obj[4]==null?null:obj[4].toString());
            cu.setPhone(obj[5]==null?null:obj[5].toString());
            cu.setTaxNo(obj[6]==null?null:obj[6].toString());
            cu.setBankNo(obj[7]==null?null:obj[7].toString());
            cu.setPayTerm(obj[8]==null?null:obj[8].toString());
            cu.setPayWay(obj[9]==null?null:obj[9].toString());
            cu.setCurrency(obj[10]==null?null:obj[10].toString());
            customers.add(cu);
        }
        return customers;
    }

//    @Override
//    public Customer Query(Customer customer) {
//        Customer customer1;
//        customer1 = customerRepository.findById(customer.getId());
//        return null;
//    }
//
//    @Override
//    public Customer Query(Customer customer) {
//        Customer customer1=customerRepository.findById(customer.getId());
//        List<CustomerContact> cc=customerContactRepository.findByCustomerNo(customer.getCustomerNo());
//
//        return cus;
//    }

    @Override
    public Customer Query(Integer id, String customerNo) {
        Customer customer1=customerRepository.findByCustomerNo(customerNo);
        List<CustomerContact> cc=customerContactRepository.findByCustomerNoByStatus(customerNo);
        customer1.setContactList(cc);
        return customer1;
    }

    @Override
    @Transactional
    public Integer DisableCustomer(Customer customer) {
        Integer count = customerRepository.disableCustomer(customer.getId());
        return count;
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

        String sql = "SELECT id,customerNo,contactName,deliverAddress,phone,tags,description from customer_contact";
        StringBuffer buffer = new StringBuffer(sql);
        buffer.append(" where customerNo='" + customerNo + "'");
        String excuteSql = buffer.toString();
        List<Object[]> list = entityManagerUtil.ExcuteSql(excuteSql);
        List<DeliverAddress> deliverAddresses = EntityUtils.castEntity(list, DeliverAddress.class, new DeliverAddress());
        contractVo.setCustomer(customer);
        contractVo.setDeliverAddresses(deliverAddresses);
        contractVo.setTaxRates(taxRates);
        return contractVo;
    }

    @Override
    public Result SaveAndUpdate(Customer customer) {
        Result result = new Result();
        //判断是否存在相同名称
        customer.getContactList().forEach(
                contact -> {contact.setCustomerNo(customer.getCustomerNo());contact.setCreateBy(customer.getCreateBy());contact.setCreateDate(customer.getCreateDate());}
                );
        Customer checkExist=CheckExisting(customer.getName());
        if(checkExist!=null){
            if(customer.getId()==checkExist.getId()){
                Customer saved=customerRepository.save(customer);
                customerContactRepository.saveAll(customer.getContactList());
                if(saved==null){
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                }else {
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("已经存在此客户名称！");
            }
        }
        else
        {
            Customer saved=customerRepository.save(customer);
            customerContactRepository.saveAll(customer.getContactList());
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                result.setData(saved);
            }
        }
        return result;
    }



    private Customer CheckExisting(String name) {
        Customer customer=customerRepository.findByName(name);
        return customer;
    }
}
