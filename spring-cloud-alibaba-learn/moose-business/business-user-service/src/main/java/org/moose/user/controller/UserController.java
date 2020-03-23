package org.moose.user.controller;

import com.google.common.collect.Maps;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.oauth.feign.OAuth2RequestTokenApi;
import org.moose.oauth.model.params.LoginParam;
import org.moose.oauth.model.params.RegisterParam;
import org.moose.user.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Value;
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
  private OAuth2RequestTokenApi oAuth2RequestTokenApi;

  @Value("${security.oauth2.client.client-id}")
  private String clientId;

  @Value("${security.oauth2.client.client-secret}")
  private String clientSecret;

  @Value("${security.oauth2.client.grant-type}")
  private String grantType;

  @PostMapping("/register")
  public ResponseResult<?> register(
      @RequestBody RegisterParam registerParam, BindingResult bindingResult) {
    Map<String, Object> map = new HashMap<>(16);
    map.put("registerParam", registerParam);
    return new ResponseResult<Map<String, Object>>(map);
  }

  @PostMapping("/login")
  public ResponseResult<?> login(
      @RequestBody @Valid LoginParam loginParam, BindingResult bindingResult
  ) {
    Map<String, String> param = Maps.newHashMap();
    param.put("username", loginParam.getAccountName());
    param.put("password", loginParam.getPassword());
    param.put("grant_type", grantType);
    param.put("client_id", clientId);
    param.put("client_secret", clientSecret);

    Map<String, Object> authToken = oAuth2RequestTokenApi.getOAuthToken(param);
    if (authToken == null) {
      return new ResponseResult<Map<String, Object>>(ResultCode.NET_WORK_UNKNOWN.getCode(),
          ResultCode.NET_WORK_UNKNOWN.getMessage());
    }

    String accessToken = (String) authToken.get("access_token");
    if (accessToken == null) {
      Integer code = (Integer) authToken.get("code");
      String message = (String) authToken.get("message");
      return new ResponseResult<Map<String, Object>>(code, message);
    }

    String refreshToken = (String) authToken.get("refresh_token");
    Map<String, Object> result = Maps.newHashMap();
    result.put("accessToken", accessToken);
    result.put("refreshToken", refreshToken);
    return new ResponseResult<Map<String, Object>>(result);
  }

  @GetMapping("/info")
  public ResponseResult<?> info() {
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    userInfoDTO.setUserId(658545465487979L);
    userInfoDTO.setUsername("张世豪");
    userInfoDTO.setAvatar(
        "https://huyaimg.msstatic.com/avatar/1021/38/798015db445aa31accd2fb80a80f10_180_135.jpg?440020");
    userInfoDTO.setNickName("阿豪");
    userInfoDTO.setBirthday(new Date());
    return new ResponseResult<UserInfoDTO>(userInfoDTO);
  }

  @PostMapping("/echo/{message}")
  public String echo(
      @PathVariable("message") String message) {
    return "Echo " + message;
  }
}
