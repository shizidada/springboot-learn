package org.learn.service;

import org.learn.service.model.MemberPasswordModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberPasswordService {

    MemberPasswordModel findPasswordByMemberId(Long memberId) throws UsernameNotFoundException;

}
