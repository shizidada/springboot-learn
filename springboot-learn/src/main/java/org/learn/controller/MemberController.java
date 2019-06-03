package org.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.learn.controller.viewobject.MemberVO;
import org.learn.exception.BusinessException;
import org.learn.service.model.MemberModel;
import org.learn.service.MemberService;
import org.learn.service.model.MemberPasswordModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RestController
public class MemberController {


    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/api/v1/member/register", method = {RequestMethod.POST})
    public AjaxResult memberRegister(@Valid MemberModel memberModel, BindingResult memberResult,
                                     @Valid MemberPasswordModel memberPasswordModel, BindingResult passwordResult) throws Exception {

        MemberModel member = memberService.register(memberModel, memberPasswordModel);
        if (member == null) {
            throw new BusinessException(ResultCode.REGISTER_FAILED);
        }
        MemberVO memberVO = convertFromModel(member);
        return AjaxResult.success("注册成功", memberVO);
    }

//    @RequestMapping(value = "/api/v1/member/login", method = {RequestMethod.POST})
//    public AjaxResult memberLogin(@RequestParam("username") String username,
//                                  @RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request) throws Exception {
//        if (StringUtils.isAnyBlank(username, password)) {
//            throw new BusinessException(ResultCode.MEMBER_PASSWORD_NOT_EXIST);
//        }
//        MemberModel memberModel = memberService.login(username, password);
//        if (memberModel == null) {
//            throw new BusinessException(ResultCode.MEMBER_PASSWORD_NOT_EXIST);
//        }
//        MemberVO memberVO = convertFromModel(memberModel);
//        return AjaxResult.success("登录成功", memberVO);
//    }

    @RequestMapping(value = "/api/v1/member/logout", method = {RequestMethod.POST})
    public void memberLogout(HttpServletResponse response, HttpServletRequest request) throws Exception {
    }

    @RequestMapping(value = "/api/v1/member/check", method = {RequestMethod.POST})
    public AjaxResult memberCheck(MemberModel memberModel) throws Exception {
        MemberModel member = memberService.check(memberModel);
        if (member != null) {
            return AjaxResult.failure();
        }
        return AjaxResult.success(ResultCode.SUCCESS.getMessage(), null);
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

//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        Map<String, Object> map = new HashMap<String, Object>();
//        if (username.equals("admin") && password.equals("admin")) {
//            map.put("isLogin", true);
//        } else {
//        }

//        FieldError fieldError = bindingResult.getFieldError();
//        ValidatorUtil.validate(memberBO);
//        if (bindingResult.hasErrors()) {
//            FieldError fieldError = bindingResult.getFieldError();
//            return AjaxResult.validateFailed(fieldError.getDefaultMessage());
//        }
//        ValidatorUtil.validate(memberBO);

//        String username = request.getParameter("username");
//        if (StringUtils.isEmpty(username)) {
//            results.setCode(Constants.FAILD_CODE);
//            results.setStatus(Boolean.FALSE);
//            results.setMessage("用户名不能为空!");
//            this.print(response, results);
//            return;
//        }
//        String nickname = request.getParameter("nickname");
//        String phone = request.getParameter("phone");
//        String icon = request.getParameter("icon");
//        String gender = request.getParameter("gender");
//        String birthday = request.getParameter("birthday");
//        MemberBO memberBO = new MemberBO();
//        Long id = snowflakeIdWorker.nextId();
//        memberBO.setId(id);
//        int row = memberMapper.insert(memberBO);
//        if (row > 0) {
//            return AjaxResult.success(map);
//        }
//        return AjaxResult.failed();
