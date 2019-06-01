package org.learn.mapper;

import org.learn.entity.MemberRoleDO;

public interface MemberRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberRoleDO record);

    int insertSelective(MemberRoleDO record);

    MemberRoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberRoleDO record);

    int updateByPrimaryKey(MemberRoleDO record);

    MemberRoleDO selectRoleByMemberId(Long memberId);
}