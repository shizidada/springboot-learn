package org.learn.service.impl;

import org.learn.common.api.ResultCode;
import org.learn.entity.MemberRoleDO;
import org.learn.mapper.MemberRoleMapper;
import org.learn.service.MemberRoleService;
import org.learn.service.model.MemberRoleModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberRoleServiceImpl implements MemberRoleService {

    @Autowired
    private MemberRoleMapper roleMapper;

    @Override
    public MemberRoleModel selectRoleByMemberId(Long memberId) throws UsernameNotFoundException {
        MemberRoleDO memberRoleDO = roleMapper.selectRoleByMemberId(memberId);
        if (memberRoleDO == null) {
            throw new UsernameNotFoundException(ResultCode.MEMBER_ROLE_NOT_FOUND.getMessage());
        }
        return convertModelFromDataObject(memberRoleDO);
    }

    private MemberRoleModel convertModelFromDataObject(MemberRoleDO memberRoleDO) {

        MemberRoleModel memberRoleModel = new MemberRoleModel();
        BeanUtils.copyProperties(memberRoleDO, memberRoleModel);
        return memberRoleModel;
    }
}
