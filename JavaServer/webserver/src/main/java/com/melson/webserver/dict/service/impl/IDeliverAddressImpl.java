package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IDeliveryAddressRepository;
import com.melson.webserver.dict.entity.DeliverAddress;
import com.melson.webserver.dict.service.IDeliverAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IDeliverAddressImpl extends AbstractService<DeliverAddress> implements IDeliverAddress {
    private final IDeliveryAddressRepository deliveryAddressRepository;

    public IDeliverAddressImpl(IDeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return deliveryAddressRepository;
    }
}
