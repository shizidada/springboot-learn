package org.moose.oauth.service;

import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 12:51:12:51
 * @see org.moose.oauth.service
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String USERNAME = "admin";
  private static final String PASSWORD =
      "$2a$10$YNUV/BtCel2orbhgrxyPJeljdKVty6yTAL.Cj4v3XhwHWXBkgyPYW";

  @Override public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
    grantedAuthorities.add(grantedAuthority);

    return new User(USERNAME, PASSWORD, grantedAuthorities);
  }
}
