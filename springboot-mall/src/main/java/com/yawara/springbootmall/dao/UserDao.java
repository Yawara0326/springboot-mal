package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.dto.UserRegisterRequest;
import com.yawara.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
