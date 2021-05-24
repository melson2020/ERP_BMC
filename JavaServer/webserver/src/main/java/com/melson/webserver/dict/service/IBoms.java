package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.vo.BomProcessVo;
import com.melson.webserver.dict.vo.BomVo;

import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/4/26
 */
public interface IBoms extends IService<Boms> {

    /**
     * 查询bom 以及相关物料信息
     * @param bomNo
     * @return
     */
    List<Boms> findByBomNo(String bomNo);

    List<BomVo> findBomVoInBomNos(Set<String> bomNos);

    /**
     * 查询Bom 以及相关工序信息 包含物料
     * @param bomNos
     * @return
     */
    List<BomProcessVo> findBomProcessVoByNo(Set<String> bomNos);
}
