package org.learn.service;


import org.learn.service.model.MemberModel;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService {

    MemberModel register(MemberModel memberModel, MemberPasswordModel memberPasswordModel) throws Exception;

    MemberModel login(String username, String password) throws Exception;

    MemberModel findMemberByUsername(String username) throws UsernameNotFoundException;
}
