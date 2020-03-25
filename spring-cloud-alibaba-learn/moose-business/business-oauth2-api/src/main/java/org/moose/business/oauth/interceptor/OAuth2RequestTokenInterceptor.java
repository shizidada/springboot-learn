package org.moose.business.oauth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.utils.MapperUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 授权 feign 请求拦截器
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/22 09:49
 * @see org.moose.business.oauth.interceptor
 */
@Slf4j
public class OAuth2RequestTokenInterceptor implements RequestInterceptor {

  /**
   * The name of the token.
   */
  public static final String BEARER = "Bearer";

  /**
   * The name of the header.
   */
  public static final String AUTHORIZATION = "Authorization";

  @Override public void apply(RequestTemplate requestTemplate) {

    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    assert attributes != null;

    HttpServletRequest request = attributes.getRequest();

    // 设置请求头
    Enumeration<String> headerNames = request.getHeaderNames();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        String value = request.getHeader(name);
        requestTemplate.header(name, value);
      }
    }

    StringBuilder body = new StringBuilder();
    // TODO: feign application/json auto cover application/x-www-form-urlencoded ??
    // set TODO: auto cover
    requestTemplate.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    try {
      byte[] bytes = requestTemplate.body();
      if (bytes.length > 0) {
        String jsonString = new String(bytes, "UTF-8");
        if (jsonString.length() > 0) {
          Map<String, Object> map = MapperUtils.json2map(jsonString);
          if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
              System.out.println(entry.getKey() + "," + entry.getValue());
              String name = entry.getKey();
              String value = (String) entry.getValue();
              body.append(name).append("=").append(value).append("&");
            }
          }
        }
      }
    } catch (Exception e) {
      log.warn("RequestTemplate Body json2map {}", e);
    }

    //log.info("bodyTemplate {}", requestTemplate.bodyTemplate());
    //log.info("requestTemplate.body() {} ", body);
    // 设置请求体
    if (body.length() > 0) {
      // 去掉最后一位 & 符号
      body.deleteCharAt(body.length() - 1);

      requestTemplate.body(body.toString().getBytes(), Charset.defaultCharset());
    }
  }
}
