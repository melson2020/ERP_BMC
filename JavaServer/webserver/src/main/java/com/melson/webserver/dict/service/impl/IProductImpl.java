package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.service.ISysSequence;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.webserver.dict.dao.*;
import com.melson.webserver.dict.entity.*;
import com.melson.webserver.dict.service.IProduct;
import com.melson.webserver.dict.vo.ContractProductVo;
import com.melson.webserver.dict.vo.ProductVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 2021/4/26
 */
@Service
public class IProductImpl extends AbstractService<Product> implements IProduct {
    private final IProductRepository productRepository;
    private final EntityManagerUtil entityManagerUtil;
    private final IStorageDetailRepository storageDetailRepository;
    private final IProductBomRepository productBomRepository;
    private final IStorageAreaLocationRepository storageAreaLocationRepository;
    private final IProductCategoryRepository iProductCategoryRepository;
    private final ISupplyRepository supplyRepository;

    private final ISysSequence sysSequenceService;

    public IProductImpl(IProductRepository productRepository, EntityManagerUtil entityManagerUtil, IStorageDetailRepository storageDetailRepository, IProductBomRepository productBomRepository, IStorageAreaLocationRepository storageAreaLocationRepository, IProductCategoryRepository iProductCategoryRepository, ISupplyRepository supplyRepository, ISysSequence sysSequenceService) {
        this.productRepository = productRepository;
        this.entityManagerUtil = entityManagerUtil;
        this.storageDetailRepository = storageDetailRepository;
        this.productBomRepository = productBomRepository;
        this.storageAreaLocationRepository = storageAreaLocationRepository;
        this.iProductCategoryRepository = iProductCategoryRepository;
        this.supplyRepository = supplyRepository;
        this.sysSequenceService = sysSequenceService;
    }

    @Override
    public JpaRepository getRepository() {
        return productRepository;
    }

    @Override
    public List<ContractProductVo> queryContractProducts(String customerNo, String searchValue) {
        List<ContractProductVo> contractProductList = new ArrayList<>();
        String sql = "SELECT pd.id, cb.productNo,pd.`name` as productName,pd.specification,CONCAT(pd.`name`, '/', pd.specification, '/', cb.description) as vaule,cb.remark,pd.unit,cb.salesPrice from customer_bom cb\n" +
                "left JOIN product pd on cb.productNo=pd.productNo\n" +
                "left join product_bom pb on pb.bomNo=cb.bomNo";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where cb.customerNo='" + customerNo + "'");
        if (!org.springframework.util.StringUtils.isEmpty(searchValue)) {
            String likeStr = "%" + searchValue + "%";
            sBuffer.append(" and (pd.`name` like '" + likeStr + "' or pd.specification like '" + likeStr + "')");
        }
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<ProductVo> productVos = EntityUtils.castEntity(list, ProductVo.class, new ProductVo());
        ContractProductVo contractProductVo1 = new ContractProductVo();
        contractProductVo1.setGroupName("常用产品");
        contractProductVo1.setList(productVos);
        String sql2 = "SELECT id, productNo,`name`,specification, CONCAT(`name`, '/', specification) as vaule,remark,unit,salesPrice from product";
        StringBuffer sBuffer2 = new StringBuffer(sql2);
        if (!org.springframework.util.StringUtils.isEmpty(searchValue)) {
            String likeStr = "%" + searchValue + "%";
            sBuffer2.append(" where `name` like '" + likeStr + "' or specification like '" + likeStr + "'");
        }
        List<Object[]> list2 = entityManagerUtil.ExcuteSql(sBuffer2.toString());
        List<ProductVo> productVos2 = EntityUtils.castEntity(list2, ProductVo.class, new ProductVo());
        ContractProductVo contractProductVo2 = new ContractProductVo();
        contractProductVo2.setGroupName("产品列表");
        contractProductVo2.setList(productVos2);
        contractProductList.add(contractProductVo1);
        contractProductList.add(contractProductVo2);
        return contractProductList;
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT pr.id,pr.productNo,pr.`name`,pr.specification,pr.unit,pr.salesPrice,sr.`name` as storageName,su.`name` as supplyName,pr.weight,pr.weightUnit,pr.volume,pr.volumeUnit,pc.`name` as category,pr.expireDate ,pr.categoryId,pr.supplyId ,pr.storageCode from product pr left JOIN product_category pc on pc.categoryId=pr.categoryId left JOIN storage_area_location sr on sr.storageCode=pr.storageCode LEFT JOIN supply su on su.id=pr.supplyId order by pr.id DESC";
        StringBuffer sBuffer = new StringBuffer(sql);
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        for (Object[] obj : list) {
            Product pr = new Product();
            pr.setId(obj[0] == null ? null : new Integer((Integer) obj[0]));
            pr.setProductNo(obj[1] == null ? null : obj[1].toString());
            pr.setName(obj[2] == null ? null : obj[2].toString());
            pr.setSpecification(obj[3] == null ? null : obj[3].toString());
            pr.setUnit(obj[4] == null ? null : obj[4].toString());
            pr.setSalesPrice(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            pr.setStorageName(obj[6] == null ? null : obj[6].toString());
            pr.setSupplyName(obj[7] == null ? null : obj[7].toString());
            pr.setWeight(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            pr.setWeightUnit(obj[9] == null ? null : obj[9].toString());
            pr.setVolume(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            pr.setVolumeUnit(obj[11] == null ? null : obj[11].toString());
            pr.setCategory(obj[12] == null ? null : obj[12].toString());
            pr.setExpireDate(obj[13]==null?null:(Timestamp) obj[13]);
            pr.setCategoryId(obj[14] == null ? null : obj[14].toString());
            pr.setSupplyId(obj[15] == null ? null : new Integer((Integer) obj[15]));
            pr.setStorageCode(obj[16] == null ? null : obj[16].toString());
            products.add(pr);
        }
        return products;
    }

    @Override
    public Result SaveAndUpdate(Product product) {
        Result result = new Result();
        Product checkExist = productRepository.findByCeriteria(product.getName(),product.getCategoryId(),product.getSpecification(),product.getSupplyId());
        if (checkExist != null) {
            if (product.getId() == checkExist.getId()) {
                Product saved = productRepository.save(product);
                if (saved == null) {
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                } else {
                    StorageDetail checkStorageDetail = storageDetailRepository.findByMaterialNo(saved.getProductNo());
                    UpdateStorageTable(checkStorageDetail, saved);
                    StorageAreaLocation storage = storageAreaLocationRepository.findByStorageCode(saved.getStorageCode());
                    saved.setStorageName(storage.getName());
                    ProductCategory category = iProductCategoryRepository.findByCategoryId(saved.getCategoryId());
                    saved.setCategory(category.getName());
                    Supply supply = supplyRepository.findById(saved.getSupplyId());
                    saved.setSupplyName(supply.getName());
                    result.setData(saved);
                }
            } else {
                result.setResultStatus(-1);
                result.setMessage("已经存在此产品名称或联系管理员！");
            }
        } else {
            if(product.getId()==null) {
                product.setProductNo(sysSequenceService.GenerateCode(Product.PRODUCT_NO_CHAR));
            }
            Product saved = productRepository.save(product);
            if (saved == null) {
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            } else {
                StorageDetail checkStorageDetail=new StorageDetail();
                UpdateStorageTable(checkStorageDetail, saved);
                StorageAreaLocation storage = storageAreaLocationRepository.findByStorageCode(saved.getStorageCode());
                saved.setStorageName(storage.getName());
                ProductCategory category = iProductCategoryRepository.findByCategoryId(saved.getCategoryId());
                saved.setCategory(category.getName());
                Supply supply = supplyRepository.findById(saved.getSupplyId());
                saved.setSupplyName(supply.getName());
                result.setData(saved);
            }
        }
        return result;
    }

    private void UpdateStorageTable(StorageDetail checkStorageDetail, Product saved) {
        StorageDetail ne = new StorageDetail();   //同时保存或更新Storage表
        ne.setMaterialNo(saved.getProductNo());
        ne.setName(saved.getName());
        ne.setSpecification(saved.getSpecification());
        ne.setCount(0);
        ne.setLastestPrice(saved.getSalesPrice());
        ne.setUnit(saved.getUnit());
        ne.setFeature("");
        ne.setStorageCode(saved.getStorageCode());
        if (checkStorageDetail != null&&checkStorageDetail.getId()!=null) {
            ne.setId(checkStorageDetail.getId());
            ne.setCount(checkStorageDetail.getCount());
            ne.setFeature(checkStorageDetail.getFeature());
        }
        storageDetailRepository.save(ne);
    }

    @Override
    @Transactional
    public Integer DeleteProduct(Integer id) {
        return productRepository.deleteProductById(id);
    }

    @Override
    public Product Query(String productNo) {
        return productRepository.findByProductNo(productNo);
    }

    @Override
    public Product QueryProductAndBomList(String productNo) {
        Product product = productRepository.findByProductNo(productNo);
        List<ProductBom> pbs = productBomRepository.findByProductNo(productNo);
        product.setProductBomList(pbs);
        return product;
    }


}
