package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.vo.BomVo;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IBoms extends IService<Boms> {

    List<BomVo> findByBomNo(String bomNo);
}
