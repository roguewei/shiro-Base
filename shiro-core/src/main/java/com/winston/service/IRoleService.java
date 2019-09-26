package com.winston.service;

import com.winston.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> queryAll(Role role);

    Role queryById(Integer id);

    List<Role> queryByIds(List<Integer> ids);

    List<Role> queryByUserName(String username);

    void addRole(Role role);

    void updateRole(Role role);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleid
     */
    public void delRole(Integer roleid);

}
