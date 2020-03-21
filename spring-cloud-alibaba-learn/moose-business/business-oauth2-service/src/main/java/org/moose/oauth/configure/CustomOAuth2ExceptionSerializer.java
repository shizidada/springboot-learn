package org.moose.oauth.configure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 自定义json 序列化异常，返回异常信息
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 23:42
 * @see org.moose.oauth.configure
 */
public class CustomOAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {
  protected CustomOAuth2ExceptionSerializer() {
    super(CustomOAuth2Exception.class);
  }

  @Override public void serialize(CustomOAuth2Exception e, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    //HttpServletRequest request =
    //    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("code", e.getCode());
    jsonGenerator.writeStringField("message", e.getMessage());
    //jsonGenerator.writeStringField("path", request.getServletPath());
    //jsonGenerator.writeNumberField("timestamp", System.currentTimeMillis());
    if (e.getAdditionalInformation() != null) {
      for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
        String key = entry.getKey();
        String add = entry.getValue();
        jsonGenerator.writeStringField(key, add);
      }
    }
    jsonGenerator.writeEndObject();
  }
}
