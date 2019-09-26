package com.winston.service;


import com.winston.entity.User;
import com.winston.utils.result.Result;
import com.winston.utils.wechat.WeChatUser;

import java.util.List;

public interface IUserService {

    List<User> queryAll();

    Result queryByUser(User user, int page, int length);

    User queryByUsername(String username);

    User queryByOpenId(String openId);

    int save(WeChatUser weChatUser);

    int save(User user);

    void update(User user);

    void delete(Integer id);
}
