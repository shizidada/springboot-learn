package org.learn.web.service;

import org.learn.bean.UserBO;

import java.util.List;

public interface UserService {

    public List<UserBO> selectAllUser() throws Exception;
}
