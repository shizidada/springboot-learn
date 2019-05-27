package org.learn.service.impl;

import org.joda.time.DateTime;
import org.learn.bean.MemberBO;
import org.learn.bean.MemberPasswordBO;
import org.learn.common.SnowflakeIdWorker;
import org.learn.common.api.ResultCode;
import org.learn.exception.BusinessException;
import org.learn.mapper.MemberMapper;
import org.learn.mapper.PasswordMapper;
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
    PasswordMapper passwordMapper;

    @Transactional
    public boolean register(MemberBO memberBO, MemberPasswordBO passwordBO) throws Exception {

        // 根据 UserName 查询是否存在相同 UserName
        MemberBO userNameMember = memberMapper.selectMemberByUserName(memberBO);
        if (userNameMember != null) {
            throw new BusinessException(ResultCode.USER_EXIST);
        }
        // 根据 NickName 查询是否存在相同 NickName
        MemberBO nickNameMember = memberMapper.selectMemberByNickName(memberBO);
        if (nickNameMember != null) {
            throw new BusinessException(ResultCode.NICKNAME_EXIST);
        }

        try {
            memberBO.setCreateTime(new Date());

            memberBO.setId(snowflakeIdWorker.nextId());
            int memRow = memberMapper.insert(memberBO);
            passwordBO.setMId(memberBO.getId());
            passwordBO.setId(snowflakeIdWorker.nextId());
            int pwdRow = passwordMapper.insert(passwordBO);
            return memRow > 0 && pwdRow > 0;
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ResultCode.PHONE_EXIST);
        }

    }
}
