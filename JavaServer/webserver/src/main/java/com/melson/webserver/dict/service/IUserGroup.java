package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.UserGroup;

/**
 * Created by Messi on 2021/5/27
 */
public interface IUserGroup extends IService<UserGroup> {
    UserGroup save(UserGroup ug);

    Result SaveAndUpdate(UserGroup ug);

    Integer DeleteUserGroup(Integer id);
}
