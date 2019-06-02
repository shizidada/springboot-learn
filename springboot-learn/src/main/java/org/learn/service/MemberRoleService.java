package org.learn.service;

import org.learn.service.model.MemberRoleModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberRoleService {

    MemberRoleModel selectRoleByMemberId(Long memberId) throws UsernameNotFoundException;

}
