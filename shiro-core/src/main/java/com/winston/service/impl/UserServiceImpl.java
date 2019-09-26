package com.winston.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winston.entity.User;
import com.winston.entity.UserExample;
import com.winston.exception.ErrorException;
import com.winston.mapper.UserMapper;
import com.winston.service.IUserService;
import com.winston.utils.jwt.RawAccessJwtToken;
import com.winston.utils.result.CodeMsg;
import com.winston.utils.result.Result;
import com.winston.utils.shiro.PasswordHelper;
import com.winston.utils.wechat.WeChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Winston
 * @title: UserServiceImpl
 * @projectName shiroDemo
 * @description:
 * @date 2019/7/24 14:25
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RawAccessJwtToken rawAccessJwtToken;

    @Override
    public List<User> queryAll() {
        return mapper.selectByExample(new UserExample());
    }

    @Override
    public Result queryByUser(User user, int page, int length) {
        PageHelper.startPage(page, length);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if(user.getId() != null){
            criteria.andIdEqualTo(user.getId());
        }
        if(user.getUsername() != null){
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if(user.getOpenId() != null){
            criteria.andOpenIdEqualTo(user.getOpenId());
        }
        List<User> users = mapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(users);
        return Result.success(users, pageInfo);
    }

    @Override
    public User queryByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andStateEqualTo("1");
        List<User> users = mapper.selectByExample(example);
        if(users != null && users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    /**
     * @auther: Winston
     * @Description: 微信登录时保存用户
     * @param:
     * @return:
     * @date: 2019/9/26 12:00
     */
    @Override
    public int save(WeChatUser weChatUser) {
        String nicname = weChatUser.getNickname();
        long nowTime = new Date().getTime();

        User user = new User();
        user.setOpenId(weChatUser.getOpenId());
        user.setOpenidHex(weChatUser.getOpenId());
        user.setEnable(1);
        user.setSex(String.valueOf(weChatUser.getSex()));
        user.setNickName(weChatUser.getNickname());
        user.setState("1");
        user.setCreateOpr(nicname);
        user.setCreateTime(nowTime);
        user.setUpdateOpr(nicname);
        user.setUpdateTime(nowTime);
        user.setOperatorType("1");
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPasswordWx(user);
        return save(user);
    }

    @Override
    public int save(User user) {
        String nicname = rawAccessJwtToken.getUserName();
        long nowTime = new Date().getTime();

        user.setEnable(1);
        user.setState("1");
        user.setCreateOpr(nicname);
        user.setCreateTime(nowTime);
        user.setUpdateOpr(nicname);
        user.setUpdateTime(nowTime);
        user.setOperatorType("0");
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(user);
        mapper.insert(user);
        return user.getId();
    }

    @Override
    public void update(User user) {
        if(user == null || user.getId() == null){
            throw new ErrorException(CodeMsg.USER_UPDATE_FAILED);
        }
        user.setPassword(null);
        int i = mapper.updateByPrimaryKeySelective(user);
        if(i <= 0){
            throw new ErrorException(CodeMsg.USER_UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id) {
        User user = mapper.selectByPrimaryKey(id);
        if(user != null){
            user.setState("1");
            mapper.updateByPrimaryKeySelective(user);
        }else{
            throw new ErrorException(CodeMsg.USER_DEL_FAILE);
        }
    }

    @Override
    public User queryByOpenId(String openId) {
//        UserExample example = new UserExample();
//        example.createCriteria().andOpenIdEqualTo(openId);
//        List<User> users = mapper.selectByExample(example);
//        return users.get(0);
        return null;
    }
}
