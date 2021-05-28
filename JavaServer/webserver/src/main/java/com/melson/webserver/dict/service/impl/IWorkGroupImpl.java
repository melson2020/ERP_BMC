package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.dao.IUserDao;
import com.melson.base.entity.User;
import com.melson.base.service.ISysSequence;
import com.melson.base.utils.EntityUtils;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.IUserGroupRepository;
import com.melson.webserver.dict.dao.IWorkGroupRepository;
import com.melson.webserver.dict.entity.Product;
import com.melson.webserver.dict.entity.UserGroup;
import com.melson.webserver.dict.entity.WorkGroup;
import com.melson.webserver.dict.service.IWorkGroup;
import com.melson.webserver.dict.vo.DeliverAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 2021/5/27
 */
@Service
public class IWorkGroupImpl extends AbstractService<WorkGroup> implements IWorkGroup {
    private final IWorkGroupRepository workGroupRepository;
    private final ISysSequence sysSequenceService;
    private final IUserDao userDao;
    private final IUserGroupRepository userGroupRepository;

    public IWorkGroupImpl(IWorkGroupRepository workGroupRepository, ISysSequence sysSequenceService, IUserDao userDao, IUserGroupRepository userGroupRepository) {
        this.workGroupRepository = workGroupRepository;
        this.sysSequenceService = sysSequenceService;
        this.userDao = userDao;
        this.userGroupRepository = userGroupRepository;
    }


    @Override
    public JpaRepository<WorkGroup, String> getRepository() {
        return null;
    }

    @Override
    public List<WorkGroup> GetWorkGroupList() {
        return workGroupRepository.findAll();
    }

    @Override
    public Result SaveAndUpdate(WorkGroup wg) {
        Result result = new Result();
        WorkGroup checkExist=workGroupRepository.findByName(wg.getName());
        if(checkExist!=null){
            if(wg.getId()==checkExist.getId()){
                WorkGroup saved=workGroupRepository.save(wg);
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
                result.setMessage("已经存在此名称或联系管理员！");
            }
        }
        else
        {
//            if(wg.getId()==null) {
//                wg.setWorkGroupCode(sysSequenceService.GenerateCode(WorkGroup.WORKGROUP_NO_CHAR));
//            }
            WorkGroup saved=workGroupRepository.save(wg);
            Integer length=8;
            wg.setWorkGroupCode(NumUtil.incrementCode(wg.getId(),WorkGroup.WORKGROUP_NO_CHAR,length));
            workGroupRepository.save(wg);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                result.setData(saved);
            }
        }
        return result;
    }

    @Override
    public WorkGroup Query(Integer id) {
        WorkGroup workGroup=workGroupRepository.findByWorkGroupId(id);
        List<Object[]> objects=userGroupRepository.findByWorkGroupId(id);
        List<UserGroup> userGroups= GeneratedList(objects);
        workGroup.setUserGroups(userGroups);
        return workGroup;
    }

    private List<UserGroup> GeneratedList(List<Object[]> objects) {
        List<UserGroup> userGroups=new ArrayList<>();
        for (Object[] obj : objects) {
            UserGroup pr = new UserGroup();
            pr.setId(obj[0] == null ? null : new Integer((Integer) obj[0]));
            pr.setWorkGroupId(obj[1] == null ? null : new Integer((Integer) obj[1]));
            pr.setUserId(obj[2] == null ? null : obj[2].toString());
            pr.setDescription(obj[3] == null ? null : obj[3].toString());
            pr.setCreateDate(obj[4]==null?null:(Timestamp) obj[4]);
            pr.setUserName(obj[5] == null ? null : obj[5].toString());
            pr.setLoginName(obj[6] == null ? null : obj[6].toString());
            userGroups.add(pr);
        }
        return userGroups;
    }

    @Override
    @Transactional
    public Integer DeleteWorkGroup(Integer id) {
        userGroupRepository.deleteItemsByGroupId(id);
        Integer count=workGroupRepository.deleteWorkGroupById(id);
        return count;
    }
}
