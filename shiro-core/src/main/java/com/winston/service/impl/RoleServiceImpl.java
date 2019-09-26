package com.winston.service.impl;

import com.winston.entity.Role;
import com.winston.mapper.RoleMapper;
import com.winston.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Winston
 * @title: RoleServiceImpl
 * @projectName shiroDemo
 * @description:
 * @date 2019/7/24 14:32
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper mapper;

    @Override
    public List<Role> queryAll(Role role) {
        return null;
    }

    @Override
    public Role queryById(Integer id) {
        return null;
    }

    @Override
    public List<Role> queryByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public List<Role> queryByUserName(String username) {
        return null;
    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public void updateRole(Role role) {

    }

    @Override
    public void delRole(Integer roleid) {

    }
}
