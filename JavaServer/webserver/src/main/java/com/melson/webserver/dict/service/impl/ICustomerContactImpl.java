package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
        List<CustomerContact> customerContacts = new ArrayList<>();
        for(Object[] obj:list){
            CustomerContact cc=new CustomerContact();
            cc.setId(obj[0]==null?null:new Integer((Integer) obj[0]));
            cc.setCustomerNo(obj[1]==null?null:obj[1].toString());
            cc.setContactName(obj[2]==null?null:obj[2].toString());
            cc.setDeliverAddress(obj[3]==null?null:obj[3].toString());
            cc.setPhone(obj[4]==null?null:obj[4].toString());
            cc.setTags(obj[5]==null?null:obj[5].toString());
            cc.setDescription(obj[6]==null?null:obj[6].toString());
            cc.setCreateBy(obj[7]==null?null:obj[7].toString());
            cc.setCreateDate(obj[8]==null?null:(Timestamp) obj[8]);
            cc.setStatus(obj[9]==null?null:obj[9].toString());
            cc.setCustomerName(obj[10]==null?null:obj[10].toString());

            customerContacts.add(cc);
        }
        return customerContacts.get(0);
    }

    @Override
    public Result SaveAndUpdate(CustomerContact contact) {
        Result result = new Result();
        CustomerContact saved=customerContactRepository.save(contact);
        if(saved==null){
            result.setResultStatus(-1);
            result.setMessage("保存失败！");
        }else {
            result.setData(saved);
        }
        return result;
    }

    @Override
    @Transactional
    public Integer DeleteContact(Integer id) {
        return customerContactRepository.deleteContactById(id);
    }
}
