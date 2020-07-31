package org.excel.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.excel.operator.utils.MapperUtils;
import org.excel.operator.utils.OkHttpClientUtil;

@Slf4j
public class OAuth2RequestTests {

  private static final String OAUTH_TOKEN_URL = "http://127.0.0.1:7000/oauth/token";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws JsonProcessingException {
    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();
    params.put("username", "jack");
    params.put("password", "123456");
    //params.put("grant_type", "password");
    params.put("grant_type", "sms_code");
    params.put("client_id", "client");
    params.put("client_secret", "secret");

    Response response = OkHttpClientUtil.getInstance().postData(OAUTH_TOKEN_URL, params);

    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      Map<String, Object> map = MapperUtils.json2map(jsonString);
      String accessToken = (String) map.get("access_token");
      if (accessToken != null) {
        Map<String, Object> result = Maps.newHashMap();
        String refreshToken = (String) map.get("refresh_token");
        result.put("access_token", accessToken);
        result.put("refresh_token", refreshToken);
      } else {
        Integer code = (Integer) map.get("code");
        String message = (String) map.get("message");
        log.info("code {} message {}", code, message);
      }
      log.info("返回信息 {}", objectMapper.writeValueAsString(map));
    } catch (Exception e) {
      log.error("Exception ", e);
    }
  }
}
