package org.learn.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.learn.common.SnowflakeIdWorker;
import org.learn.common.api.ResultCode;
import org.learn.entity.MemberRoleDO;
import org.learn.mapper.MemberRoleMapper;
import org.learn.service.model.CustomUserDetails;
import org.learn.service.model.MemberModel;
import org.learn.entity.MemberDO;
import org.learn.entity.MemberPasswordDO;
import org.learn.exception.BusinessException;
import org.learn.mapper.MemberMapper;
import org.learn.mapper.MemberPasswordMapper;
import org.learn.service.MemberService;
import org.learn.service.model.MemberPasswordModel;
import org.learn.service.model.MemberRoleModel;
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
    private MemberRoleMapper memberRoleMapper;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberModel findMemberByUsername(String username) {
        MemberDO memberDO = memberMapper.selectMemberByUserName(username);
        if (memberDO == null) {
            throw new UsernameNotFoundException(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage());
        }
        return convertModelFromMemberDO(memberDO);
    }

    @Override
    public MemberModel check(MemberModel memberModel) throws Exception {
        // 根据 UserName 查询是否存在相同 UserName
        MemberDO member = memberMapper.selectMemberByUserName(memberModel.getUsername());
        if (member != null) {
            throw new BusinessException(ResultCode.MEMBER_EXIST);
        }
        // 根据 NickName 查询是否存在相同 NickName
        member = memberMapper.selectMemberByNickName(memberModel.getNickname());
        if (member != null) {
            throw new BusinessException(ResultCode.NICKNAME_EXIST);
        }
        // 根据 phone 查询是否存在相同 phone
        member = memberMapper.selectMemberByPhone(memberModel.getPhone());
        if (member != null) {
            throw new BusinessException(ResultCode.PHONE_EXIST);
        }
        return null;
    }

    @Transactional
    public MemberModel register(MemberModel memberModel, MemberPasswordModel memberPasswordModel) throws Exception {
        try {
            memberModel.setCreateTime(new Date());
            memberModel.setId(snowflakeIdWorker.nextId()); // 设置 snowflakeIdWorker 生成ID
            // TODO 设置账号状态是否存在问题呢？
            memberModel.setStatus(1);
            MemberDO memberDO = convertModel2MemberDO(memberModel);
            memberMapper.insertSelective(memberDO); // 插入数据库

            memberPasswordModel.setPassword(bCryptPasswordEncoder.encode(memberPasswordModel.getPassword())); // BCryptPasswordEncoder 密码加密
            MemberPasswordDO passwordDO = convertModel2PasswordDO(memberPasswordModel);
            passwordDO.setId(snowflakeIdWorker.nextId());
            passwordDO.setMemberId(memberDO.getId()); // 设置 密码关联 生成ID
            memberPasswordMapper.insertSelective(passwordDO);

            MemberRoleDO memberRoleDO = new MemberRoleDO();
            memberRoleDO.setId(snowflakeIdWorker.nextId());
            memberRoleDO.setMemberId(memberDO.getId());
            // 注册分配基本权限
            memberRoleDO.setRoleName("ROLE_MEMBER");
            memberRoleMapper.insertSelective(memberRoleDO);

            memberModel = convertModelFromMemberDO(memberDO);
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
    private MemberDO convertModel2MemberDO(MemberModel memberModel) {
        if (memberModel == null) {
            return null;
        }
        MemberDO memberDO = new MemberDO();
        BeanUtils.copyProperties(memberModel, memberDO);
        return memberDO;
    }

    private MemberPasswordDO convertModel2PasswordDO(MemberPasswordModel memberPasswordModel) {
        MemberPasswordDO memberPasswordDO = new MemberPasswordDO();
        BeanUtils.copyProperties(memberPasswordModel, memberPasswordDO);
        return memberPasswordDO;
    }

    private MemberRoleDO convertModel2PasswordDO(MemberRoleModel memberRoleModel) {
        MemberRoleDO memberRoleDO = new MemberRoleDO();
        BeanUtils.copyProperties(memberRoleModel, memberRoleDO);
        return memberRoleDO;
    }

    // DO to Model
    private MemberModel convertModelFromMemberDO(MemberDO memberDO) {
        MemberModel memberModel = new MemberModel();
        BeanUtils.copyProperties(memberDO, memberModel);
        return memberModel;
    }

    private MemberPasswordModel convertModelFromMemberPasswordDO(MemberPasswordDO memberPasswordDO) {
        MemberPasswordModel memberPasswordModel = new MemberPasswordModel();
        BeanUtils.copyProperties(memberPasswordDO, memberPasswordModel);
        return memberPasswordModel;
    }
}
