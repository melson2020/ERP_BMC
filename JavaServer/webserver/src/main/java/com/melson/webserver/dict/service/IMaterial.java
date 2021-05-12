package com.melson.webserver.dict.service;

import com.melson.base.IService;
import com.melson.base.Result;
import com.melson.webserver.dict.entity.Material;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
public interface IMaterial extends IService<Material> {
    List<Material> findAll();

    Result SaveAndUpdate(Material material);

    Integer DeleteMaterial(Integer id);

    Material Query(String partNo);

    List<Material> findAllList();

}
