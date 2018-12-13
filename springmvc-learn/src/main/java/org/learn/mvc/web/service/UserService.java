package org.learn.mvc.web.service;

import org.learn.mvc.bean.UserBO;

import java.util.List;

public interface UserService {

    public boolean insertUser(UserBO userBO) throws Exception;
    public List<UserBO> selectAllUser() throws Exception;
}
