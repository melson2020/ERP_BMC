package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.Material;
import com.melson.webserver.dict.entity.StorageAreaLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Messi on 2021/4/23
 */
@Repository
public interface IMaterialRepository extends JpaRepository<Material,Integer> {
    List<Material> findByPartNoIn(Set<String> partNos);

    Material findByName(String name);

    @Modifying
    @Query(value = "delete from material where id=?1",nativeQuery = true)
    Integer deleteMaterialById(Integer id);

    Material findMaterialByPartNo(String partNo);
}
