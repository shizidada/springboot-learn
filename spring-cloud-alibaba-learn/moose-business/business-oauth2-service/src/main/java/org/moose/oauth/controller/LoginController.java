package org.moose.oauth.controller;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.utils.MapperUtils;
import org.moose.commons.utils.OkHttpClientUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 10:41
 * @see org.moose.oauth.controller
 */
@RestController
@RequestMapping("/oauth")
@Slf4j
public class LoginController {

  private static final String OAUTH_TOKEN_URL = "http://localhost:9000/oauth/token";

  /**
   * for mock auth
   *
   * @return ResponseResult
   * @see ResponseResult
   */
  @PostMapping("/login")
  public ResponseResult<Map<String, Object>> login() {
    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();
    params.put("username", "tom");
    params.put("password", "123456");
    params.put("grant_type", "password");
    params.put("client_id", "client");
    params.put("client_secret", "secret");

    // DefaultHandlerExceptionResolver

    Response response = OkHttpClientUtil.getInstance().postData(OAUTH_TOKEN_URL, params);
    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      log.info("请求 {} 返回信息 {}", OAUTH_TOKEN_URL, jsonString);

      Map<String, Object> map = MapperUtils.json2map(jsonString);
      String accessToken = (String) map.get("access_token");
      if (accessToken != null) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("accessToken", accessToken);
        return new ResponseResult<Map<String, Object>>(result);
      } else {
        Integer code = (Integer) map.get("code");
        String message = (String) map.get("message");
        return new ResponseResult<Map<String, Object>>(code, message);
      }
    } catch (Exception e) {
      log.error("登录异常", e);
      Integer code = ResultCode.NET_WORK_UNKNOWN.getCode();
      String message = e.getMessage();
      return new ResponseResult<Map<String, Object>>(code, message);
    }
  }
}
