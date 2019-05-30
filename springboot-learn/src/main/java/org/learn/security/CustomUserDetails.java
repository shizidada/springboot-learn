package org.learn.security;

import org.learn.service.model.MemberModel;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private MemberModel memberModel;
    private MemberPasswordModel memberPasswordModel;

    //    private String role;
//    , String role
    public CustomUserDetails(MemberModel memberModel, MemberPasswordModel memberPasswordModel) {
        this.memberModel = memberModel;
        this.memberPasswordModel = memberPasswordModel;
//        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role));
        return null;
    }

    @Override
    public String getPassword() {
        return memberPasswordModel.getPassword();
    }

    @Override
    public String getUsername() {
        return memberModel.getUsername();
    }

    //账号是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //账号凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

    public MemberPasswordModel getMemberPasswordModel() {
        return memberPasswordModel;
    }

    public void setMemberPasswordModel(MemberPasswordModel memberPasswordModel) {
        this.memberPasswordModel = memberPasswordModel;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
}
