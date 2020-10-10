package org.moose.operator.util;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.moose.operator.constant.HttpMethod;

/**
 * @author taohua
 * HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
 * HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
 */
public class GetRequestJsonUtils {
  public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException {
    String json = getRequestJsonString(request);
    return JSONObject.parseObject(json);
  }

  /***
   * 获取 request 中 json 字符串的内容
   *
   * @param request
   * @return : <code>byte[]</code>
   * @throws IOException
   */
  public static String getRequestJsonString(HttpServletRequest request)
      throws IOException {
    String submitMethod = request.getMethod();
    // GET
    if (submitMethod.equals(HttpMethod.GET)) {
      return new String(request.getQueryString().getBytes(StandardCharsets.ISO_8859_1),
          StandardCharsets.UTF_8).replaceAll("%22", "\"");
      // POST
    } else {
      return getRequestPostStr(request);
    }
  }

  /**
   * 描述:获取 post 请求的 byte[] 数组
   * <pre>
   * 举例：
   * </pre>
   *
   * @param request
   * @return
   * @throws IOException
   */
  public static byte[] getRequestPostBytes(HttpServletRequest request)
      throws IOException {
    int contentLength = request.getContentLength();
    if (contentLength < 0) {
      return null;
    }
    byte[] buffer = new byte[contentLength];
    for (int i = 0; i < contentLength; ) {

      int readLength = request.getInputStream().read(buffer, i,
          contentLength - i);
      if (readLength == -1) {
        break;
      }
      i += readLength;
    }
    return buffer;
  }

  /**
   * 描述:获取 post 请求内容
   * <pre>
   * 举例：
   * </pre>
   *
   * @param request
   * @return
   * @throws IOException
   */
  public static String getRequestPostStr(HttpServletRequest request)
      throws IOException {
    byte[] buffer = getRequestPostBytes(request);
    String charEncoding = request.getCharacterEncoding();
    if (charEncoding == null) {
      charEncoding = "UTF-8";
    }
    if (buffer != null && buffer.length > 0) {
      return new String(buffer, charEncoding);
    }
    return "";
  }
}