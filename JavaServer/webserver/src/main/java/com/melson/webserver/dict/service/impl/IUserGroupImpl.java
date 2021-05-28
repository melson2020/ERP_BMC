package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.webserver.dict.dao.IUserGroupRepository;
import com.melson.webserver.dict.entity.UserGroup;
import com.melson.webserver.dict.entity.WorkGroup;
import com.melson.webserver.dict.service.IUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Messi on 2021/5/27
 */
@Service
public class IUserGroupImpl extends AbstractService<UserGroup> implements IUserGroup {
    private final IUserGroupRepository userGroupRepository;

    public IUserGroupImpl(IUserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public JpaRepository<UserGroup, String> getRepository() {
        return null;
    }

    @Override
    public UserGroup save(UserGroup ug) {
        return userGroupRepository.save(ug);
    }

    @Override
    public Result SaveAndUpdate(UserGroup ug) {
        Result result = new Result();
        UserGroup saved=userGroupRepository.save(ug);
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
    public Integer DeleteUserGroup(Integer id) {
        Integer count=userGroupRepository.deleteByIntId(id);
        return count;
    }
}
