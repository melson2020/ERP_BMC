package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.ICustomerContactRepository;
import com.melson.webserver.dict.dao.ICustomerRepository;
import com.melson.webserver.dict.entity.Customer;
import com.melson.webserver.dict.entity.CustomerContact;
import com.melson.webserver.dict.service.ICustomerContact;
import com.melson.webserver.dict.vo.CustomerContactVo;
import com.melson.webserver.dict.vo.CustomerVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class ICustomerContactImpl extends AbstractService<CustomerContact> implements ICustomerContact {
    private final EntityManagerUtil entityManagerUtil;
    private final ICustomerContactRepository customerContactRepository;
    private final ICustomerRepository customerRepository;

    public ICustomerContactImpl(EntityManagerUtil entityManagerUtil, ICustomerContactRepository customerContactRepository, ICustomerRepository customerRepository) {
        this.entityManagerUtil = entityManagerUtil;
        this.customerContactRepository = customerContactRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public JpaRepository<CustomerContact, String> getRepository() {
        return null;
    }

    @Override
    public List<CustomerContactVo> getAllContact() {
        String sql = "SELECT cc.id,cc.contactName,cc.phone,cc.customerNo,cu.name,cc.deliverAddress,cc.status from customer_contact as cc LEFT JOIN customer cu on cc.customerNo=cu.customerNo";
        StringBuffer buffer = new StringBuffer(sql);
        String excuteSql = buffer.toString();
        List<Object[]> list = entityManagerUtil.ExcuteSql(excuteSql);
        List<CustomerContactVo> vos = EntityUtils.castEntity(list, CustomerContactVo.class, new CustomerContactVo());
        return vos;
    }

    @Override
    public CustomerContact findById(Integer id) {
        CustomerContact cc=customerContactRepository.findById(id).orElse(null);
        Customer cu=customerRepository.findByCustomerNo(cc.getCustomerNo());
        cc.setCustomerName(cu.getName());
        return cc;
    }

    @Override
    public CustomerContact findContact(Integer id) {
        String sql = "SELECT cc.id,cc.customerNo,cc.contactName,cc.deliverAddress,cc.phone,cc.tags,cc.description,cc.createBy,cc.createDate,cc.status,cu.name as customerName from customer_contact as cc LEFT JOIN customer cu on cc.customerNo=cu.customerNo";
        StringBuffer buffer = new StringBuffer(sql);
        buffer.append(" where cc.id='" + id + "'");
        String excuteSql = buffer.toString();
        List<Object[]> list = entityManagerUtil.ExcuteSql(excuteSql);
        List<CustomerContact> cc = EntityUtils.castEntity(list, CustomerContact.class, new CustomerContact());
        return cc.get(0);
    }
}
