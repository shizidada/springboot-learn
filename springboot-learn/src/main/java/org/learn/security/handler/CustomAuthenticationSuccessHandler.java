package org.learn.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.learn.controller.viewobject.MemberVO;
import org.learn.security.CustomUserDetails;
import org.learn.service.model.MemberModel;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证成功时返回给前端的数据
 */
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // 登录成功设置 authentication TODO ！！！？？
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MemberModel memberModel = customUserDetails.getMemberModel();
        log.info("{} CustomAuthenticationSuccessHandler 登录认证成功 >>> {}", memberModel.getId(), memberModel.getUsername());
// String jwtToken = JwtTokenManager.generateToken(userDetails.getUsername(), 1500);
        MemberVO memberVO = convertFromModel(memberModel);
        response.getWriter().write(JSON.toJSONString(AjaxResult.success(ResultCode.MEMBER_LOGIN_SUCCESS.getMessage(), authentication), SerializerFeature.DisableCircularReferenceDetect));

    }

    //将Model转为VO
    private MemberVO convertFromModel(MemberModel memberModel) {
        if (memberModel == null) {
            return null;
        }
        MemberVO memberVO = new MemberVO();
        BeanUtils.copyProperties(memberModel, memberVO);
        return memberVO;
    }
}
