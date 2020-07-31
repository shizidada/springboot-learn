package org.excel.operator.web.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.model.dto.AccountDTO;
import org.excel.operator.model.dto.RegisterInfoDTO;
import org.excel.operator.model.params.LoginParam;
import org.excel.operator.web.service.impl.AccountServiceImpl;
import org.excel.operator.web.service.impl.LoginServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@RestController
@RequestMapping(value = "/api/v1/account")
@Slf4j
public class AccountController {

  @Resource
  private AccountServiceImpl accountService;

  @Resource
  private LoginServiceImpl loginService;

  /**
   * spring security to login
   *
   * @PostMapping(value = "/login")
   */
  @PostMapping(value = "/login")
  public ResponseResult<Map<String, Object>> login(@Valid @RequestBody LoginParam loginParam,
      BindingResult result,
      HttpServletRequest request) {
    return loginService.login(loginParam);
  }

  /**
   * spring security to register
   */
  @PostMapping(value = "/register")
  public ResponseResult<Boolean> register(@Valid @RequestBody RegisterInfoDTO registerInfoDTO,
      BindingResult result,
      HttpServletRequest request) {
    return new ResponseResult<>(accountService.register(request, registerInfoDTO), "注册成功");
  }

  @PostMapping(value = "/info")
  public ResponseResult<AccountDTO> info() {
    return new ResponseResult<>(accountService.getAccountInfo(), "获取用户信息成功");
  }
}
