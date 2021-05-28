package com.melson.webserver.order.service.impl;

import com.melson.webserver.order.dao.IDelegateDetailRepository;
import com.melson.webserver.order.entity.DelegateDetail;
import com.melson.webserver.order.service.IDelegateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/28
 */
@Service
public class DelegateDetailServiceImpl implements IDelegateDetailService {
    @Autowired
    private IDelegateDetailRepository delegateDetailRepository;

}
