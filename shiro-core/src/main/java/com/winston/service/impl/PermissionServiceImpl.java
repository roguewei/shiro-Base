package com.winston.service.impl;

import com.winston.entity.*;
import com.winston.exception.ErrorException;
import com.winston.mapper.PermissionMapper;
import com.winston.service.IPermissionService;
import com.winston.service.IRolePermissionService;
import com.winston.service.IUserRoleService;
import com.winston.service.IUserService;
import com.winston.utils.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Winston
 * @title: PermissionServiceImpl
 * @projectName shiroDemo
 * @description:
 * @date 2019/7/24 14:32
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper mapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Override
    public List<Permission> queryAll() {
        return mapper.selectByExample(new PermissionExample());
    }

    /**
     * @auther: Winston
     * @Description: 根据用户名查询拥有的权限
     * @param:
     * @return:
     * @date: 2019/9/25 10:49
     */
    @Override
    public List<Permission> queryByUserName(String username) {
        User user = userService.queryByUsername(username);
        if(user != null){
            List<UserRoleKey> userRoleKeys = userRoleService.queryByUserId(user.getId());
            if(userRoleKeys != null && userRoleKeys.size() > 0){
                List<Integer> roleIds = new ArrayList<>();
                for(UserRoleKey userRole : userRoleKeys){
                    roleIds.add(Integer.valueOf(userRole.getRoleId()));
                }
                List<RolePermissionKey> rolePermissionKeys = rolePermissionService.queryByRoleIds(roleIds);
                if(rolePermissionKeys != null && rolePermissionKeys.size() > 0){
                    List<Integer> perIds = new ArrayList<>();
                    for(RolePermissionKey rolePer : rolePermissionKeys){
                        perIds.add(Integer.valueOf(rolePer.getPerId()));
                    }
                    PermissionExample example = new PermissionExample();
                    example.createCriteria().andIdIn(perIds);
                    List<Permission> permissionList = mapper.selectByExample(example);
                    return permissionList;
                }
            }
        }
        return null;
    }

    /**
     * @auther: Winston
     * @Description: 根据用户名查询未拥有的权限
     * @param:
     * @return:
     * @date: 2019/9/25 10:49
     */
    @Override
    public List<Permission> queryHaveNot(String username) {
        List<Permission> permissionsAll = queryAll();
        List<Permission> permissionsHave = queryByUserName(username);
        if(permissionsAll != null){
            if(permissionsHave == null){
                return permissionsAll;
            }
            for(Permission permission : permissionsHave){
                for(int perHave=0; perHave<permissionsAll.size() ; perHave++){
                    if(permission.getId().equals(permissionsAll.get(perHave).getId()) ){
                        permissionsAll.remove(permissionsAll.get(perHave));
                    }
                }
            }
            return permissionsAll;
        }
        return null;
    }

    @Override
    public void addAllUrl(Permission permission) {
        mapper.insert(permission);
    }

    @Override
    public void addPermission(Permission permission) {
        if(permission.getPername() != null && permission.getPerurl() != null){
            PermissionExample example = new PermissionExample();
            example.createCriteria().andPernameEqualTo(permission.getPername()).andPerurlEqualTo(permission.getPerurl());
            List<Permission> permissions = mapper.selectByExample(example);
            if(permissions != null && permissions.size() > 0){
                throw new ErrorException(CodeMsg.PERMISSION_ALERADY_EXIST);
            }
            mapper.insert(permission);
        }else{
            throw new ErrorException(CodeMsg.PERMISSION_PARAM_NULL);
        }
    }

    @Override
    public void updatePermission(Permission permission) {
        mapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void delPermission(Integer id) {

    }
}
