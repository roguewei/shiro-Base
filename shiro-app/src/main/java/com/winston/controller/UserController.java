package com.winston.controller;

import com.winston.entity.User;
import com.winston.service.IUserService;
import com.winston.utils.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Winston
 * @title: UserController
 * @projectName shiroDemo
 * @description:
 * @date 2019/7/24 15:53
 */
@RestController
@RequestMapping("/app/users")
public class UserController {

    @Autowired
    private IUserService userService;

//    @RequiresPermissions("/app/users")
    @GetMapping
    public Result query(){
        List<User> users = userService.queryAll();
        return Result.success(users);
    }

    @ApiOperation(value = "查询用户", notes = "根据条件查询用户")//接口说明
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "String", paramType = "String"),
            @ApiImplicitParam(name = "page", value = "页码,第几页", required = false, dataType = "Integer", paramType = "Integer"),
            @ApiImplicitParam(name = "length", value = "每页数量", required = false, dataType = "Integer", paramType = "Integer")
    })
    @RequiresPermissions("/app/users/queryByUser")
    @GetMapping("/queryByUser")
    public Result queryByUser(User user,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "10") int length){
        return Result.success(userService.queryByUser(user, page, length));
    }


}
