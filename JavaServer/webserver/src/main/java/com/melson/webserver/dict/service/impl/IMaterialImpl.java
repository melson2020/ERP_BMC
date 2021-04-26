package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.Material;
import com.melson.webserver.dict.service.IMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IMaterialImpl extends AbstractService<Material> implements IMaterial {
    @Override
    public JpaRepository<Material, String> getRepository() {
        return null;
    }
}
