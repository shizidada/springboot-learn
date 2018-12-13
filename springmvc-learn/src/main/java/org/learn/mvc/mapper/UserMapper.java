package org.learn.mvc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.learn.mvc.bean.UserBO;

import java.util.List;

@Mapper
public interface UserMapper {

    public boolean insertUser(UserBO userBO);

    public List<UserBO> selectAllUser();
}
