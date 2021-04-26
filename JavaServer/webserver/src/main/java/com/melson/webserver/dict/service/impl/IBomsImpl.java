package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.service.IBoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IBomsImpl extends AbstractService<Boms> implements IBoms {
    @Override
    public JpaRepository<Boms, String> getRepository() {
        return null;
    }
}
