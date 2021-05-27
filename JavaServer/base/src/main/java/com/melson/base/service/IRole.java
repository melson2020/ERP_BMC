package com.melson.base.service;

import com.melson.base.IService;
import com.melson.base.entity.Role;

import java.util.List;

/**
 * Created by Messi on 2021/5/25
 */
public interface IRole extends IService<Role> {
    List<Role> GetRoleList();
}
