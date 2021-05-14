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
    public String GenerateCode(String type) {
        SysSequence sysSequence=sysSequenceDao.findByType(type);
        if(sysSequence==null){
            sysSequence=new SysSequence();
            sysSequence.setType(type);
            sysSequence.setValue(0);
            sysSequence.setValueLength(8);
        }
        Integer newValue=sysSequence.getValue()+1;
        String code=type+String.format("%07d",newValue);
        sysSequence.setValue(newValue);
        sysSequenceDao.save(sysSequence);
        return code;
    }
}
