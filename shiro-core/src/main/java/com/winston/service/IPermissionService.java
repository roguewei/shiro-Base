package com.winston.service;

import com.winston.entity.Permission;

import java.util.List;

public interface IPermissionService {

    List<Permission> queryAll();

    // 根据用户名查询拥有的权限（包括在组内的权限）
    List<Permission> queryByUserName(String username);

    List<Permission> queryHaveNot(String username);

    void addAllUrl(Permission permission);

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    void delPermission(Integer id);

}
