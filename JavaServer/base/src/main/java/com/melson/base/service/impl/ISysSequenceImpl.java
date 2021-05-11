package com.melson.base.service.impl;

import com.melson.base.dao.ISysSequenceDao;
import com.melson.base.entity.SysSequence;
import com.melson.base.service.ISysSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/11
 */
@Service
public class ISysSequenceImpl implements ISysSequence {
    @Autowired
    private ISysSequenceDao sysSequenceDao;


    @Override
    public String GenerateCode(String key) {
        SysSequence sysSequence=sysSequenceDao.findByKey(key);
        if(sysSequence==null){
            sysSequence=new SysSequence();
            sysSequence.setKey(key);
            sysSequence.setValue(0);
            sysSequence.setValueLength(8);
            sysSequence.setMaxValue(10*sysSequence.getValueLength());
        }
        Integer newValue=sysSequence.getValue()+1;
        String code=key+String.format("%08d",newValue);
        sysSequenceDao.save(sysSequence);
        return code;
    }
}
