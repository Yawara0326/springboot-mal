package com.yawara.springbootmall.service.Impl;

import com.yawara.springbootmall.dao.UserDao;
import com.yawara.springbootmall.dto.UserRegisterRequest;
import com.yawara.springbootmall.model.User;
import com.yawara.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        return userDao.createUser(userRegisterRequest);

    }
}
