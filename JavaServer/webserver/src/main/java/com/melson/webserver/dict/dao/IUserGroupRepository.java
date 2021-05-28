package com.melson.webserver.dict.dao;

import com.melson.webserver.dict.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Messi on 2021/5/27
 */
@Repository
public interface IUserGroupRepository extends JpaRepository<UserGroup,String> {

    @Modifying
    @Query(value = "DELETE from `user_group` WHERE workGroupId =?1",nativeQuery = true)
    void deleteItemsByGroupId(Integer id);

    @Query(value = "SELECT ug.id,ug.workGroupId,ug.userId,ug.description,ug.createDate,us.userName,us.loginName from user_group ug LEFT JOIN user us on us.userId=ug.userId where ug.workGroupId=?1",nativeQuery = true)
    List<Object[]> findByWorkGroupId(Integer id);

    @Modifying
    @Query(value = "DELETE from `user_group` WHERE id =?1",nativeQuery = true)
    Integer deleteByIntId(Integer id);
}
