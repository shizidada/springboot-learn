package org.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.learn.bean.MemberBO;
import org.learn.bean.MemberPasswordBO;
import org.learn.common.api.AjaxResult;
import org.learn.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class MemberController {


    @Autowired
    MemberService memberService;

    @RequestMapping(value = "api/v1/member/register", method = {RequestMethod.POST})
    public AjaxResult memberRegister(@Valid MemberBO memberBO, BindingResult memberResult,
                                     @Valid MemberPasswordBO passwordBO, BindingResult passwordResult) throws Exception {
        boolean isSuccess = memberService.register(memberBO, passwordBO);
        if (isSuccess) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", memberBO.getUsername());
            map.put("nickname", memberBO.getNickname());
            return AjaxResult.success("注册成功", map);
        }
        return AjaxResult.failed("注册失败");
    }

    @RequestMapping(value = "api/v1/member/login", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public void login(HttpServletResponse response, HttpServletRequest request) {
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
//            results.setCode(Constant.FAILD_CODE);
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
