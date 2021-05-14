package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.Supply;

import java.util.List;

/**
 * Created by Messi on 2021/5/13
 */
public interface ISupply extends IService<Supply> {
    List<Supply> findAll();

}
