package org.moose.business.user.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.user.web.model.params.RegisterParam;
import org.moose.business.user.web.service.RegisterService;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:12
 * @see org.moose.business.user.web.controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class RegisterController {

  @Resource
  private RegisterService registerService;

  @PostMapping("/register")
  public ResponseResult<?> register(
      @RequestBody @Valid RegisterParam registerParam, BindingResult registerResult) {
    return registerService.register(registerParam);
  }
}
