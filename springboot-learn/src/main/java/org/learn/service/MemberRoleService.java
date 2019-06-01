package org.learn.service;

import org.learn.service.model.MemberRoleModel;

public interface MemberRoleService {

    MemberRoleModel selectRoleByMemberId(Long memberId);

}
