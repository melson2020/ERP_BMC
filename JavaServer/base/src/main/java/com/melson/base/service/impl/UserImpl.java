package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.Result;
import com.melson.base.dao.*;
import com.melson.base.entity.*;
import com.melson.base.service.ISysSequence;
import com.melson.base.service.IUser;
import com.melson.base.utils.EntityManagerUtil;
import com.melson.base.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/1/25
 */
@Service
public class UserImpl extends AbstractService<User> implements IUser {

    private static final Logger logger = LoggerFactory.getLogger(UserImpl.class);

    private final IUserDao userDao;
    private final IUserRoleDao userRoleDao;
    private final IRoleDao roleDao;
    private final IMenuDao menuDao;
    private final IRoleMenuDao roleMenuDao;
    private final ISysSequence sysSequenceService;
    private final EntityManagerUtil entityManagerUtil;

    public UserImpl(IUserDao userDao, IUserRoleDao userRoleDao, IRoleDao roleDao, IMenuDao menuDao, IRoleMenuDao roleMenuDao, ISysSequence sysSequenceService, EntityManagerUtil entityManagerUtil) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
        this.menuDao = menuDao;
        this.roleMenuDao = roleMenuDao;
        this.sysSequenceService = sysSequenceService;
        this.entityManagerUtil = entityManagerUtil;
    }

    @Override
    public JpaRepository<User, String> getRepository() {
        return userDao;
    }

    @Override
    public List<User> GetEmployeeList(String userType) {
        List<User> userList=new ArrayList<>();
        String status="";
        switch (userType.toUpperCase()) {
            case "NORMAL":
//                userList = userDao.findByStatus();
                status="Y";
                userList = GenerateList(status);
                break;
            default:
                status="";
//                userList = userDao.findAll();
                userList = GenerateList(status);
                break;
        }
        return userList;
    }

    private List<User> GenerateList(String status) {
        String sql = "SELECT us.id,us.userId,us.userName,us.departmentId,us.userType,us.createDate,us.subscriptionId,us.companyCode,us.loginName,us.`password`,us.description,us.`status`,us.createBy, de.departmentName FROM `user` us LEFT JOIN department de on de.id=us.departmentId";
        StringBuffer sBuffer = new StringBuffer(sql);
        if (!org.springframework.util.StringUtils.isEmpty(status)) {
            sBuffer.append(" where us.`status`='" + status + "'");
        }
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<User> userList=GenerateUserList(list);
        return userList;
    }

    private List<User> GenerateUserList(List<Object[]> list) {
        List<User> userList=new ArrayList<>();
        for(Object[] obj : list) {
            User pr=new User();
            pr.setId(obj[0] == null ? null : new Integer((Integer) obj[0]));
            pr.setUserId(obj[1] == null ? null : obj[1].toString());
            pr.setUserName(obj[2] == null ? null : obj[2].toString());
            pr.setDepartmentId(obj[3] == null ? null : new Integer((Integer) obj[3]));
            pr.setUserType(obj[4] == null ? null : obj[4].toString());
            pr.setCreateDate(obj[5]==null?null:(Timestamp) obj[5]);
            pr.setSubscriptionId(obj[6] == null ? null : obj[6].toString());
            pr.setCompanyCode(obj[7] == null ? null : obj[7].toString());
            pr.setLoginName(obj[8] == null ? null : obj[8].toString());
            pr.setPassword(obj[9] == null ? null : obj[9].toString());
            pr.setDescription(obj[10] == null ? null : obj[10].toString());
            pr.setStatus(obj[11] == null ? null : obj[11].toString());
            pr.setCreateBy(obj[12] == null ? null : obj[12].toString());
            pr.setDepartment(obj[13] == null ? null : obj[13].toString());
            userList.add(pr);
        }
        return userList;
    }

    @Override
    @Transactional
    public Result SaveAndUpdate(User user) {
        Result result = new Result();
        User checkExist=userDao.findByUserNameAndLoginName(user.getUserName(),user.getLoginName());
        if(checkExist!=null){
            if(user.getId()==checkExist.getId()){
                userRoleDao.DeleteOlderRoles(user.getUserId());  //删除原先的权限列表
                User saved=userDao.save(user);
                user.getUserRoleList().forEach(userRole->{userRole.setUserId(user.getUserId());});  //设置列表中的userId;
                List<UserRole> userRoles=user.getUserRoleList();
                userRoleDao.saveAll(userRoles); //重新配置权限
                if(saved==null){
                    result.setResultStatus(-1);
                    result.setMessage("保存失败！");
                }else {
                    saved.setDepartment(user.getDepartment());
                    result.setData(saved);
                }
            }
            else
            {
                result.setResultStatus(-1);
                result.setMessage("已经存在此名称或联系管理员！");
            }
        }
        else
        {
            if(user.getId()==null) {
                user.setUserId(UUID.randomUUID().toString());
                user.setPassword(MD5Util.string2MD5(user.getLoginName()));
                user.setUserType("NORMAL");
            }
            User saved=userDao.save(user);
            user.getUserRoleList().forEach(userRole->{userRole.setUserId(user.getUserId());});  //设置列表中的userId;
            List<UserRole> userRoles=user.getUserRoleList();
            userRoleDao.saveAll(userRoles);
            if(saved==null){
                result.setResultStatus(-1);
                result.setMessage("保存失败！");
            }else {
                saved.setDepartment(user.getDepartment());
                result.setData(saved);
            }
        }
        return result;
    }

    @Override
    public User QueryEmployee(Integer id) {
        String sql = "SELECT us.id,us.userId,us.userName,us.departmentId,us.userType,us.createDate,us.subscriptionId,us.companyCode,us.loginName,us.`password`,us.description,us.`status`,us.createBy, de.departmentName FROM `user` us LEFT JOIN department de on de.id=us.departmentId";
        StringBuffer sBuffer = new StringBuffer(sql);
        sBuffer.append(" where us.id='" + id + "'");
        List<Object[]> list = entityManagerUtil.ExcuteSql(sBuffer.toString());
        List<User> userList=GenerateUserList(list);
        User user=userList.get(0);

        String sql2 = "SELECT ur.id,ur.roleId,ur.userId,ur.description,ro.roleName FROM `user_role` ur LEFT JOIN role ro on ro.id=ur.roleId";
        StringBuffer sBuffer2 = new StringBuffer(sql2);
        sBuffer2.append(" where ur.userId='" + user.getUserId() + "'");
        List<Object[]> list2 = entityManagerUtil.ExcuteSql(sBuffer2.toString());
        List<UserRole> roleList=GenerateRoleList(list2);
        user.setUserRoleList(roleList);
        return user;
    }

    private List<UserRole> GenerateRoleList(List<Object[]> list) {
        List<UserRole> userRoleList=new ArrayList<>();
        for(Object[] obj : list) {
            UserRole pr=new UserRole();
            pr.setId(obj[0] == null ? null : new Integer((Integer) obj[0]));
            pr.setRoleId(obj[1] == null ? null : obj[1].toString());
            pr.setUserId(obj[2] == null ? null : obj[2].toString());
            pr.setDescription(obj[3] == null ? null : obj[3].toString());
            pr.setRoleName(obj[4] == null ? null : obj[4].toString());
            userRoleList.add(pr);
        }
        return userRoleList;
    }

    @Override
    public Integer UpdateUserStatus(User user) {
        Integer count = userDao.UpdateUserStatus(user.getStatus(),user.getId());
        return count;
    }

    @Override
    public User CreateUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User CreateAdminUserForCompany(Company company, String password) {
        User user = new User();
        user.setUserName(company.getCompanyName() + "管理用户");
        user.setDepartment("admin");
        user.setUserType("admin");
        user.setSubscriptionId("");
        user.setCompanyCode(company.getCompanyCode());
        user.setLoginName(company.getPhoneNumber());
        user.setPassword(MD5Util.string2MD5(password));
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateDate(new Date());
        return userDao.save(user);
    }

    public UserRole CreateUserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        return userRoleDao.save(userRole);
    }

    @Override
    public User SystemLogin(User user) {
        String md5Pass = MD5Util.string2MD5(user.getPassword());
        User existUser = userDao.findByLoginNameAndPassword(user.getLoginName(), md5Pass);
        if (existUser == null) {
            //账户密码不正确
            return null;
        }
        //获取角色对应的MenuList  user-->userRole-->RoleMenu
        List<UserRole> userRoleList = userRoleDao.findByUserId(existUser.getUserId());
        if (userRoleList == null || userRoleList.size() <= 0) {
            //无对应的角色 这种数据不该存在 注意核对数据
            return null;
        }
        Set<String> roleIds = new HashSet<>(userRoleList.size());
        for (UserRole userRole : userRoleList) {
            roleIds.add(userRole.getRoleId());
        }
        List<RoleMenu> roleMenuList = roleMenuDao.findByRoleIdIn(roleIds);
        if (roleMenuList == null || roleMenuList.size() <= 0) {
            //无对应的菜单 这种数据不该存在 注意核对数据
            return null;
        }
        Set<String> menuIds = new HashSet<>(roleMenuList.size());
        for (RoleMenu roleMenu : roleMenuList) {
            menuIds.add(roleMenu.getMenuId());
        }
        List<Menu> menuList = menuDao.findByMenuIdIn(menuIds);
        List<Menu> sortedList = ReSortMenuList(menuList);
        existUser.setMenuList(sortedList);
        return existUser;
    }

    //重排菜单列表  把子菜单添加到对应的菜单内
    private List<Menu> ReSortMenuList(List<Menu> menuList) {
        List<Menu> firstLev = new ArrayList<>();
        List<Menu> remaindList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentMenuId().equals("-1")) {
                firstLev.add(menu);
            } else {
                remaindList.add(menu);
            }
        }
        for (Menu menu : firstLev) {
            menu.setSubMenuList(GetChildList(menu.getMenuId(), remaindList));
        }
        return firstLev;
    }

    //获取子菜单
    private List<Menu> GetChildList(String menuId, List<Menu> remaindList) {
        List<Menu> childList = new ArrayList<>();
        List<Menu> reList = new ArrayList<>();
        for (Menu menu : remaindList) {
            if (menu.getParentMenuId().equals(menuId)) {
                childList.add(menu);
            } else {
                reList.add(menu);
            }
        }
        if (reList.size() <= 0) return null;
        for (Menu menu : childList) {
            menu.setSubMenuList(GetChildList(menu.getMenuId(), reList));
        }
        return childList;
    }

    @Override
    public User get(Integer id) {
        if (id == null) {
            logger.error("用户id为空");
            return null;
        }
        return userDao.findById(id);
    }

    @Override
    public void updatePwd(Integer id, String pwd) {
        userDao.updatePwd(id, pwd);
    }


}
