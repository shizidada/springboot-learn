package org.learn.mapper;

import org.learn.entity.MemberPasswordDO;

public interface MemberPasswordMapper {
    int insert(MemberPasswordDO record);

    int insertSelective(MemberPasswordDO record);
}