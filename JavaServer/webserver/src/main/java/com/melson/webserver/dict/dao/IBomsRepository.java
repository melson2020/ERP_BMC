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
    @Query(value = "SELECT b.id, b.bomNo,b.PartNo,m.name,b.chPartNo,m.unit,b.chQty,b.manufacturer,m.specification FROM boms b RIGHT JOIN material m on b.chPartNo=m.partNo WHERE b.bomNo=?1 ORDER BY b.`index`", nativeQuery = true)
    List<Object[]> findBomInfoByNo(String bomNo);

    //暂时不在使用
    @Query(value = "SELECT b.id, b.bomNo,b.PartNo,m.name,b.chPartNo,m.unit,b.chQty,b.manufacturer,m.specification FROM boms b RIGHT JOIN material m on b.chPartNo=m.partNo WHERE b.bomNo in ?1 ORDER BY b.`index`", nativeQuery = true)
    List<Object[]> findBomInfoByNos(Set<String> bomNos);

    @Query(nativeQuery = true, value = "SELECT b.id, b.bomNo,pp.id as processId,b.processNo,pp.`name` as processName,b.PartNo,b.chPartNo,m.`name` as materialName,b.chQty as materialCount,m.specification, b.`index`,pp.delegateFlag FROM `boms` b RIGHT JOIN material m on b.chPartNo=m.partNo RIGHT JOIN  produce_process pp on b.processNo=pp.processNo WHERE b.bomNo in ?1 ORDER BY b.`index`;")
    List<Object[]> findBomProcessInfoByNo(Set<String> bomNos);
}
