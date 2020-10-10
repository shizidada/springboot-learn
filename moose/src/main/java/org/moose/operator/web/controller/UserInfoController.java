package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua 用户信息
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserInfoController {

  @Resource
  private AccountService accountService;

  @Resource
  private UserInfoService userInfoService;

  @PostMapping(value = "/info")
  public ResponseResult<Object> info() {
    return new ResponseResult<>(accountService.getAccountInfo(), "获取用户信息成功");
  }

  @PostMapping(value = "/update")
  public ResponseResult<Object> update(@Valid UserInfoDTO userInfoDTO,
      BindingResult result) {
    boolean isSuccess = userInfoService.updateUserInfo(userInfoDTO);
    return new ResponseResult<>(isSuccess, String.format("更新%s", isSuccess ? "成功" : "失败"));
  }
}
