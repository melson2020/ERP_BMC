package com.melson.webserver.dict.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.entity.User;
import com.melson.base.service.ISysSequence;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.EntityUtils;
import com.melson.base.utils.NumUtil;
import com.melson.webserver.dict.dao.*;
import com.melson.webserver.dict.entity.*;
import com.melson.webserver.dict.service.IProduct;
import com.melson.webserver.dict.vo.ContractProductVo;
import com.melson.webserver.dict.vo.GroupProductVo;
import com.melson.webserver.dict.vo.ProductVo;
import com.melson.webserver.dict.vo.UserPassVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        String sql = "SELECT cp.productNo, pr.name,pr.specification,pr.categoryId, pc.`name` as category,su.id as supplyId,cp.salesPrice,su.`name` as supplyName ,''as bomNo,'' as bomGenericId, CONCAT(pc.`name`,' / ',pr.`name`,' / ',pr.specification,' / ',su.`name`,' / ',cp.remark) as alias ,pr.id,pr.unit ,cp.remark FROM `customer_product` cp left JOIN product pr on pr.productNo=cp.productNo left JOIN supply su on pr.supplyId=su.id left JOIN product_category pc on pr.categoryId=pc.categoryId";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where cp.customerNo='" + customerNo + "'");
        if (!org.springframework.util.StringUtils.isEmpty(searchValue)) {
            String likeStr = "%" + searchValue + "%";
            sBuffer.append(" and (pr.`name` like '" + likeStr + "' or pr.specification like '" + likeStr + "')");
        }
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<ProductVo> productVos = EntityUtils.castEntity(list, ProductVo.class, new ProductVo());
        ContractProductVo contractProductVo1 = new ContractProductVo();
        contractProductVo1.setGroupName("常用产品");
        contractProductVo1.setList(productVos);
        String sql2 = "SELECT pr.productNo,pr.name,pr.specification,pr.categoryId,pc.`name` as category,pr.supplyId,pr.salesPrice,su.`name` as supplyName,''as bomNo,'' as bomGenericId,CONCAT(pc.`name`,' / ',pr.`name`,' / ',pr.specification,' / ',su.`name`) as alias ,pr.id,pr.unit ,pr.remark from product pr left JOIN supply su on pr.supplyId=su.id left JOIN product_category pc on pr.categoryId=pc.categoryId ";
        StringBuffer sBuffer2 = new StringBuffer(sql2);
        if (!org.springframework.util.StringUtils.isEmpty(searchValue)) {
            String likeStr = "%" + searchValue + "%";
            sBuffer2.append(" where pr.`name` like '" + likeStr + "' or pr.specification like '" + likeStr + "'");
        }
        List<Object[]> list2 = entityManagerUtil.ExcuteSql(sBuffer2.toString());
        List<ProductVo> productVos2 = EntityUtils.castEntity(list2, ProductVo.class, new ProductVo());
        ContractProductVo contractProductVo2 = new ContractProductVo();
        contractProductVo2.setGroupName("产品列表");
        contractProductVo2.setList(productVos2);
        contractProductList.add(contractProductVo1);
        contractProductList.add(contractProductVo2);return contractProductList;
    }

    @Override
    public List<GroupProductVo> GetProductVoList(String productNo) {
        return generateVoList(productNo);
    }

    @Override
    public List<UserPassVo> GetVos() {
        String sql = "SELECT us.id,CONCAT(us.loginName,' / ',us.userName,' / ',de.departmentName) as alias FROM `user` us LEFT JOIN department de on de.id=us.departmentId where us.`status`='Y'";
        StringBuffer sBuffer = new StringBuffer(sql);
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<UserPassVo> vos=EntityUtils.castEntity(list, UserPassVo.class, new UserPassVo());
        return vos;
    }

    private List<GroupProductVo> generateVoList(String productNo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now=sdf.format(new Date());
        List<GroupProductVo> voList=new ArrayList<>();
        String sql ="SELECT pb.productNo,pb.productName as name,pb.specification,pb.categoryId,pc.`name` as category,pb.supplyId,pb.costPrice as salesPrice,pb.supplyName, pb.bomNo,pb.bomGenericId,CONCAT(pc.`name`,' / ',pb.productName,' / ',pb.specification,' / ',pb.supplyName,' / ',pb.description,' / ',pb.version) as alias ,''as id, pr.unit,'' as remark FROM `product_bom` pb left join product_category pc on pb.categoryId=pc.categoryId LEFT JOIN product pr on pr.productNo=pb.productNo";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where pb.status='Y'");
        sBuffer.append("  and pb.expirationDate>='" + now + "'");
        if (!org.springframework.util.StringUtils.isEmpty(productNo)) {
            sBuffer.append(" and pb.productNo<>'" + productNo + "'");
        }
        List<Object[]> list1 = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<ProductVo> productVos1 = GenerateList(list1);
        GroupProductVo gpvo1=new GroupProductVo();
        gpvo1.setGroupName("成品/半成品");
        gpvo1.setList(productVos1);
        String sql2 ="SELECT pr.productNo,pr.name,pr.specification,pr.categoryId,pc.`name` as category,pr.supplyId,pr.salesPrice,su.`name` as supplyName,''as bomNo,'' as bomGenericId,CONCAT(pc.`name`,' / ',pr.`name`,' / ',pr.specification,' / ',su.`name`) as alias ,''as id,''as unit,'' as remark from product pr left JOIN supply su on pr.supplyId=su.id left JOIN product_category pc on pr.categoryId=pc.categoryId left JOIN (SELECT productNo from product_bom) pb on pr.productNo=pb.productNo WHERE pb.productNo is null and pr.categoryId<>'CAT00000002' and pr.categoryId<>'CAT00000003'";
        StringBuffer sBuffer2 = new StringBuffer(sql2);
        sBuffer2.append("  and pr.expireDate>='" + now + "'");
        if (!org.springframework.util.StringUtils.isEmpty(productNo)) {
            sBuffer.append("  and pr.productNo<>'" + productNo + "'");
        }
        List<Object[]> list2 = entityManagerUtil.ExcuteSql(sBuffer2.toString());
        List<ProductVo> productVos2 = GenerateList(list2);
        GroupProductVo gpvo2=new GroupProductVo();
        gpvo2.setGroupName("底料");
        gpvo2.setList(productVos2);
        voList.add(gpvo1);
        voList.add(gpvo2);
        return voList;
    }

    private List<ProductVo> GenerateList(List<Object[]> list) {
        List<ProductVo> productVos=new ArrayList<>();
        for(Object[] obj : list) {
            ProductVo pr=new ProductVo();
            pr.setProductNo(obj[0] == null ? null : obj[0].toString());
            pr.setName(obj[1] == null ? null : obj[1].toString());
            pr.setSpecification(obj[2] == null ? null : obj[2].toString());
            pr.setCategory(obj[3] == null ? null : obj[3].toString());
            pr.setCategory(obj[4] == null ? null : obj[4].toString());
            pr.setSupplyId(obj[5] == null ? null : new Integer((Integer) obj[5]));
            pr.setSalesPrice(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            pr.setSupplyName(obj[7] == null ? null : obj[7].toString());
            pr.setBomNo(obj[8] == null ? null : obj[8].toString());
            pr.setBomGenericId(obj[9] == null ? null : obj[9].toString());
            pr.setAlias(obj[10] == null ? null : obj[10].toString());
            pr.setUnit(obj[12] == null ? null : obj[12].toString());
            pr.setRemark(obj[13] == null ? null : obj[13].toString());
            productVos.add(pr);
        }
        return productVos;
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
//            if(product.getId()==null) {
//                product.setProductNo(sysSequenceService.GenerateCode(Product.PRODUCT_NO_CHAR));
//            }
            Product saved = productRepository.save(product);
            Integer length=8;
            product.setProductNo(NumUtil.incrementCode(product.getId(), Product.PRODUCT_NO_CHAR,length));
            productRepository.save(product);
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
        ne.setProductId(saved.getId());
        ne.setLastestPrice(saved.getSalesPrice());
        ne.setUnit(saved.getUnit());
        ne.setFeature("");
        ne.setStorageCode(saved.getStorageCode());
        ne.setLevel(0);
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
        List<Product> products = new ArrayList<>();
        String sql = "SELECT pr.id,pr.productNo,pr.`name`,pr.specification,pr.unit,pr.salesPrice,sr.`name` as storageName,su.`name` as supplyName,pr.weight,pr.weightUnit,pr.volume,pr.volumeUnit,pc.`name` as category,pr.expireDate ,pr.categoryId,pr.supplyId ,pr.storageCode from product pr left JOIN product_category pc on pc.categoryId=pr.categoryId left JOIN storage_area_location sr on sr.storageCode=pr.storageCode LEFT JOIN supply su on su.id=pr.supplyId ";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where pr.productNo='" + productNo + "'");
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
        Product product = products.get(0);
        List<ProductBom> pbs = productBomRepository.findByProductNo(productNo);
        List<GroupProductVo> groupProductVos= generateVoList(productNo);
        product.setGroupProductVoList(groupProductVos);
        product.setProductBomList(pbs);
        return product;
    }



}
