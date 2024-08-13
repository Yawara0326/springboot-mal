package com.yawara.springbootmall.service;

import com.yawara.springbootmall.dto.UserRegisterRequest;
import com.yawara.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

}
