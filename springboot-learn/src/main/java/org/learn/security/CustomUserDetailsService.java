package org.learn.security;

import org.learn.service.MemberPasswordService;
import org.learn.service.MemberService;
import org.learn.service.model.MemberModel;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * SpringSecurity 需要自定义 UserDetailsService ，将用户信息和权限注入进来。
 */

@Service("customUserDetailsService")
public class CustomUserDetailsService implements  UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberPasswordService memberPasswordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberModel memberModel = memberService.findMemberByUsername(username);
        if (memberModel == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        MemberPasswordModel memberPasswordModel = memberPasswordService.findPasswordByMemberId(memberModel.getId());
        return new CustomUserDetails(memberModel, memberPasswordModel);
    }
}
