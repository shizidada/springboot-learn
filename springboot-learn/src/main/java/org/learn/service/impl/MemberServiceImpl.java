package org.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.learn.common.SnowflakeIdWorker;
import org.learn.common.api.ResultCode;
import org.learn.security.CustomUserDetails;
import org.learn.security.CustomUserDetailsService;
import org.learn.service.model.MemberModel;
import org.learn.entity.MemberDO;
import org.learn.entity.MemberPasswordDO;
import org.learn.exception.BusinessException;
import org.learn.mapper.MemberMapper;
import org.learn.mapper.MemberPasswordMapper;
import org.learn.service.MemberService;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberPasswordMapper memberPasswordMapper;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberModel findMemberByUsername(String username) {
        MemberDO memberDO = memberMapper.selectMemberByUserName(username);
        if (memberDO == null) {
            throw new UsernameNotFoundException(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage());
        }
        return convertMemberModelFromDataObject(memberDO);
    }

    @Transactional
    public MemberModel register(MemberModel memberModel, MemberPasswordModel memberPasswordModel) throws Exception {

        // 根据 UserName 查询是否存在相同 UserName
        MemberDO userNameMember = memberMapper.selectMemberByUserName(memberModel.getUsername());
        if (userNameMember != null) {
            throw new BusinessException(ResultCode.MEMBER_EXIST);
        }
        // 根据 NickName 查询是否存在相同 NickName
        MemberDO nickNameMember = memberMapper.selectMemberByNickName(memberModel.getNickname());
        if (nickNameMember != null) {
            throw new BusinessException(ResultCode.NICKNAME_EXIST);
        }

        try {
            memberModel.setCreateTime(new Date());

            memberModel.setId(snowflakeIdWorker.nextId()); // 设置 snowflakeIdWorker 生成ID

            MemberDO memberDO = convertMemberModel2MemberDO(memberModel);
            memberMapper.insertSelective(memberDO); // 插入数据库

            memberPasswordModel.setPassword(bCryptPasswordEncoder.encode(memberPasswordModel.getPassword())); // BCryptPasswordEncoder 密码加密
            MemberPasswordDO passwordDO = convertPasswordModel2PasswordDO(memberPasswordModel);

            passwordDO.setMemberId(memberDO.getId()); // 设置 密码关联 生成ID
            passwordDO.setId(snowflakeIdWorker.nextId());

            memberPasswordMapper.insertSelective(passwordDO);

            memberModel = convertMemberModelFromDataObject(memberDO);
            return memberModel;
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ResultCode.PHONE_EXIST);
        }

    }

    @Override
    public MemberModel login(String username, String password) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new BusinessException(ResultCode.MEMBER_PASSWORD_NOT_EXIST);
        }
        return userDetails.getMemberModel();
    }

    // 将 Model 转为 DO
    private MemberDO convertMemberModel2MemberDO(MemberModel memberModel) {
        if (memberModel == null) {
            return null;
        }
        MemberDO memberDO = new MemberDO();
        BeanUtils.copyProperties(memberModel, memberDO);
        return memberDO;
    }

    private MemberPasswordDO convertPasswordModel2PasswordDO(MemberPasswordModel memberPasswordModel) {
        MemberPasswordDO memberPasswordDO = new MemberPasswordDO();
        BeanUtils.copyProperties(memberPasswordModel, memberPasswordDO);
        return memberPasswordDO;
    }

    // DO to Model
    private MemberModel convertMemberModelFromDataObject(MemberDO memberDO) {
        MemberModel memberModel = new MemberModel();
        BeanUtils.copyProperties(memberDO, memberModel);
        return memberModel;
    }

    private MemberPasswordModel convertPasswordModelFromDataObject(MemberPasswordDO memberPasswordDO) {
        MemberPasswordModel memberPasswordModel = new MemberPasswordModel();
        BeanUtils.copyProperties(memberPasswordDO, memberPasswordModel);
        return memberPasswordModel;
    }
}
