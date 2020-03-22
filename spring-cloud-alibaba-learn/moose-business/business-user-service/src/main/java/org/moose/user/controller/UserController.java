package org.moose.user.controller;

import com.google.common.collect.Maps;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.model.dto.PasswordDTO;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.oauth.feign.OAuth2LoginFeignApi;
import org.moose.oauth.model.params.LoginParam;
import org.moose.oauth.model.params.OAuthParam;
import org.moose.user.dto.UserInfoDTO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 14:09
 * @see org.moose.user.controller
 */
@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

  @Resource
  private OAuth2LoginFeignApi oAuth2LoginFeignApi;

  @PostMapping("/register")
  public ResponseResult<Map<String, Object>> register(
      @RequestBody AccountDTO accountDTO,
      @RequestBody PasswordDTO passwordDTO) {
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    userInfoDTO.setUserId(658545465487979L);
    userInfoDTO.setUsername("热情往来");
    userInfoDTO.setBirthday(new Date());

    Map<String, Object> map = new HashMap<>(16);
    map.put("account", accountDTO);
    map.put("password", passwordDTO);
    map.put("userInfo", userInfoDTO);
    return new ResponseResult<>(map);
  }

  @GetMapping("/info")
  public ResponseResult<UserInfoDTO> info() {
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    userInfoDTO.setUserId(658545465487979L);
    userInfoDTO.setUsername("张世豪");
    userInfoDTO.setAvatar(
        "https://huyaimg.msstatic.com/avatar/1021/38/798015db445aa31accd2fb80a80f10_180_135.jpg?440020");
    userInfoDTO.setNickName("阿豪");
    userInfoDTO.setBirthday(new Date());

    // for test
    //if (true) {
    //  throw new BusinessException("抛出来一个自定义 ... ", 100000);
    //}
    //String login = oAuth2LoginFeignApi.login(accountName, password);
    //log.info("receive data from business-oauth2-service [{}]", login);

    return new ResponseResult<>(userInfoDTO);
  }

  @PostMapping("/login")
  public ResponseResult<?> login(
      @RequestBody @Valid LoginParam loginParam, BindingResult bindingResult
  ) {

    Map<String, String> param = Maps.newHashMap();
    param.put("username", loginParam.getAccountName());
    param.put("password", loginParam.getPassword());
    param.put("grant_type", "password");
    param.put("client_id", "client");
    param.put("client_secret", "secret");

    //MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
    //param.add("username", loginParam.getAccountName());
    //param.add("password", loginParam.getPassword());
    //param.add("grant_type", "password");
    //param.add("client_id", "client");
    //param.add("client_secret", "secret");

    //OAuthParam param = new OAuthParam();
    //param.setUsername(loginParam.getAccountName());
    //param.setPassword(loginParam.getPassword());
    //param.setClientId("client");
    //param.setClientSecret("secret");
    //param.setGrantType("password");

    String jsonString = oAuth2LoginFeignApi.getOAuthToken(param);
    log.info("获取结果 {}", jsonString);
    return new ResponseResult<>(jsonString);
  }

  @PostMapping("/echo/{message}")
  public String echo(
      @PathVariable("message") String message) {
    return "Echo " + message;
  }
}
