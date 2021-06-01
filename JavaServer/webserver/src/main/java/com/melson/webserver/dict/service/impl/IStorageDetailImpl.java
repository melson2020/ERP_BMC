package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.webserver.dict.dao.IStorageDetailRepository;
import com.melson.webserver.dict.entity.StorageDetail;
import com.melson.webserver.dict.service.IStorageDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Messi on 2021/5/10
 */
@Service
public class IStorageDetailImpl extends AbstractService<StorageDetail> implements IStorageDetail {
    private final IStorageDetailRepository storageDetailRepository;

    public IStorageDetailImpl(IStorageDetailRepository storageDetailRepository) {
        this.storageDetailRepository = storageDetailRepository;
    }

    @Override
    public JpaRepository<StorageDetail, String> getRepository() {
        return storageDetailRepository;
    }

    @Override
    public StorageDetail findByProductMaterialNo(String productNo) {
        return storageDetailRepository.findByMaterialNo(productNo);
    }

    @Override
    @Transactional
    public Integer DeleteStorage(String materialNo) {
        return storageDetailRepository.deleteByMaterialNo(materialNo);
    }

    @Override
    public Page<StorageDetail> FindAllStorageList(String searchValue,Integer page,Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "id");
        Specification<StorageDetail> specification = new Specification<StorageDetail>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<StorageDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(!StringUtils.isEmpty(searchValue)){
                    predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%"+searchValue+"%"),criteriaBuilder.like(root.get("storageCode"), "%"+searchValue+"%")));
                }
                return predicate;
            }
        };
        Page<StorageDetail> adminAccounts = storageDetailRepository.findAll(specification, pageable);
        return adminAccounts;
    }
}
