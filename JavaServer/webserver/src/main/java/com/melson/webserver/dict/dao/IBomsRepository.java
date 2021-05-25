package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Boms;
import com.melson.webserver.dict.vo.BomVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IBomsRepository extends JpaRepository<Boms, Integer> {
    @Query(value = "SELECT b.id, b.bomNo,b.PartNo,p.name,b.chPartNo,p.unit,b.chQty,b.supplyName as manufacturer,p.specification FROM boms b RIGHT JOIN product p on b.chPartNo=p.productNo WHERE b.bomNo= ? 1 ORDER BY b.`sindex`", nativeQuery = true)
    List<Object[]> findBomInfoByNo(String bomNo);

    @Query(value = "SELECT b.id,b.bomNo,b.partNo,p.`name`,b.chPartNo,p.unit,b.chQty,b.supplyName,p.specification FROM `boms` b RIGHT JOIN product p ON b.chPartNo=p.productNo WHERE b.bomNo in ? 1 ORDER BY b.`index`", nativeQuery = true)
    List<Object[]> findBomInfoInBomNos(Set<String> bomNos);

    @Query(nativeQuery = true, value = "SELECT b.id,b.bomNo,b.processId,b.processNo,pp.`name` as processName,b.partNo,b.chPartNo,b.chName as materialName,b.chQty as materialCount,b.sIndex as 'index',pp.delegateFlag,p.id as materialId from boms b RIGHT JOIN produce_process pp ON b.processId=pp.id RIGHT JOIN product p on b.chPartNo=p.productNo WHERE b.bomNo in ?1")
    List<Object[]> findBomProcessInfoInBomNos(Set<String> bomNos);

    List<Boms> findByBomNo(String bomNo);

    List<Boms> findByBomNoAndBomGenericId(String bomNo, String bomGenericId);

}
