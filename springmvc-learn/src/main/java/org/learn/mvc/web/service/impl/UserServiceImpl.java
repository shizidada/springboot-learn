package org.learn.mvc.web.service.impl;

import org.learn.mvc.bean.UserBO;
import org.learn.mvc.mapper.UserMapper;
import org.learn.mvc.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean insertUser(UserBO userBO) throws Exception {
        return userMapper.insertUser(userBO);
    }

    @Override
    public List<UserBO> selectAllUser() throws Exception {
        return userMapper.selectAllUser();
    }
}
