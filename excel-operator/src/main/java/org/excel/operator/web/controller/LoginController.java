package org.excel.operator.web.controller;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.model.params.LoginParam;
import org.excel.operator.web.service.impl.AccountServiceImpl;
import org.excel.operator.web.service.impl.LoginServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-30 23:15:23:15
 * @see org.excel.operator.web.controller
 */

@RestController
@RequestMapping(value = "/api/v1/login")
@Slf4j
public class LoginController {

  @Resource
  private AccountServiceImpl accountService;

  @Resource
  private LoginServiceImpl loginService;

  @PostMapping(value = "/status")
  public ResponseResult<Boolean> status() {
    return new ResponseResult<>(loginService.isLogin());
  }
}
