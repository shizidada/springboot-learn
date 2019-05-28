package org.learn.mapper;

import org.learn.entity.MemberDO;

public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberDO record);

    int insertSelective(MemberDO record);

    MemberDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberDO record);

    int updateByPrimaryKey(MemberDO record);

    /**
     * 根据 NickName 查询是否存在相同 NickName
     *
     * @param memberDO
     * @return
     */
    MemberDO selectMemberByNickName(MemberDO memberDO);

    /**
     * 根据 UserName 查询是否存在相同 UserName
     *
     * @param memberDO
     * @return
     */
    MemberDO selectMemberByUserName(MemberDO memberDO);
}