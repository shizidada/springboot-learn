package org.learn.security;

import org.learn.service.model.MemberModel;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private MemberModel memberModel;
    private MemberPasswordModel memberPasswordModel;
    private List<GrantedAuthority> authorities; // 权限集合

    public CustomUserDetails(MemberModel memberModel,
                             MemberPasswordModel memberPasswordModel,
                             List<GrantedAuthority> authorities) {
        this.memberModel = memberModel;
        this.memberPasswordModel = memberPasswordModel;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return memberModel.getStatus() != 0;
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

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
