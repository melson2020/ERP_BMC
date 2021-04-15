package com.melson.base.service.impl;

import com.melson.base.AbstractService;
import com.melson.base.dao.*;
import com.melson.base.entity.*;
import com.melson.base.service.IUser;
import com.melson.base.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserImpl(IUserDao userDao, IUserRoleDao userRoleDao, IRoleDao roleDao, IMenuDao menuDao, IRoleMenuDao roleMenuDao) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.roleDao = roleDao;
        this.menuDao = menuDao;
        this.roleMenuDao = roleMenuDao;
    }

    @Override
    public JpaRepository<User, String> getRepository() {
        return userDao;
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
