package org.learn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.learn.bean.UserBO;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<UserBO> selectAllUser();


}
