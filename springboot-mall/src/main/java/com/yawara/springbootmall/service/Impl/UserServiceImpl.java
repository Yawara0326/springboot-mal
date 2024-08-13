package com.yawara.springbootmall.service.Impl;

import com.yawara.springbootmall.dao.UserDao;
import com.yawara.springbootmall.dto.UserRegisterRequest;
import com.yawara.springbootmall.model.User;
import com.yawara.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    //因為實作中還包含其他有關註冊的動作(檢查email是否已被註冊)，因此不叫createUser，而是register。
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        //檢查email是否已被註冊
        if (user != null){
            log.warn("該email {} 已被註冊",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userDao.createUser(userRegisterRequest);

    }
}
