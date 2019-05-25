package org.learn.service.impl;

import org.learn.bean.UserBO;
import org.learn.mapper.UserMapper;
import org.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    public List<UserBO> selectAllUser() throws Exception {
        return userMapper.selectAllUser();
    }
}
