package org.learn.service.impl;

import org.learn.common.SnowflakeIdWorker;
import org.learn.common.api.ResultCode;
import org.learn.entity.MemberDO;
import org.learn.entity.MemberPasswordDO;
import org.learn.exception.BusinessException;
import org.learn.mapper.MemberMapper;
import org.learn.mapper.MemberPasswordMapper;
import org.learn.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    MemberPasswordMapper memberPasswordMapper;

    @Transactional
    public boolean register(MemberDO memberDO, MemberPasswordDO passwordBO) throws Exception {

        // 根据 UserName 查询是否存在相同 UserName
        MemberDO userNameMember = memberMapper.selectMemberByUserName(memberDO);
        if (userNameMember != null) {
            throw new BusinessException(ResultCode.USER_EXIST);
        }
        // 根据 NickName 查询是否存在相同 NickName
        MemberDO nickNameMember = memberMapper.selectMemberByNickName(memberDO);
        if (nickNameMember != null) {
            throw new BusinessException(ResultCode.NICKNAME_EXIST);
        }

        try {
            memberDO.setCreateTime(new Date());

            memberDO.setId(snowflakeIdWorker.nextId());
            int memRow = memberMapper.insertSelective(memberDO);
            passwordBO.setMemberId(memberDO.getId());
            passwordBO.setId(snowflakeIdWorker.nextId());
            int pwdRow = memberPasswordMapper.insertSelective(passwordBO);
            return memRow > 0 && pwdRow > 0;
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ResultCode.PHONE_EXIST);
        }

    }
}
