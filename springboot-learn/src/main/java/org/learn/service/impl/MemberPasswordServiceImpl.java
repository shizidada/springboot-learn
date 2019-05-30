package org.learn.service.impl;

import org.learn.common.api.ResultCode;
import org.learn.entity.MemberPasswordDO;
import org.learn.mapper.MemberPasswordMapper;
import org.learn.service.MemberPasswordService;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberPasswordServiceImpl implements MemberPasswordService {

    @Autowired
    private MemberPasswordMapper passwordMapper;

    @Override
    public MemberPasswordModel findPasswordByMemberId(Long memberId) throws UsernameNotFoundException {
        MemberPasswordDO memberPasswordDO = passwordMapper.findPasswordByMemberId(memberId);
        if (memberPasswordDO == null) {
            throw new UsernameNotFoundException(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage());
        }
        return convertModelFromDataObject(memberPasswordDO);
    }

    private MemberPasswordModel convertModelFromDataObject(MemberPasswordDO memberPasswordDO) {
        MemberPasswordModel memberPasswordModel = new MemberPasswordModel();
        BeanUtils.copyProperties(memberPasswordDO, memberPasswordModel);
        return memberPasswordModel;
    }
}
