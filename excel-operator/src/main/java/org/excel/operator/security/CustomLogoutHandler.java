//package org.excel.operator.security;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.stereotype.Component;
//
///**
// * <p>
// * Description
// * </p>
// *
// * @author taohua
// * @version v1.0.0
// * @date 2019 2019/11/20 21:57
// * @see org.excel.operator.component
// */
//@Component
//@Slf4j
//public class CustomLogoutHandler implements LogoutHandler {
//  @Override
//  public void logout(HttpServletRequest request, HttpServletResponse response,
//      Authentication authentication) {
//    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//    log.info(" >>>> CustomLogoutHandler >>>> 用户 [{}] 登出。", customUserDetails.getUsername());
//  }
//}
