package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.WorkGroup;

import java.util.List;

/**
 * Created by Messi on 2021/5/27
 */
public interface IWorkGroup extends IService<WorkGroup> {
    List<WorkGroup> GetWorkGroupList();

    Result SaveAndUpdate(WorkGroup wg);

    WorkGroup Query(Integer id);

    Integer DeleteWorkGroup(Integer id);
}
