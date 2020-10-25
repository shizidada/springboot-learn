package org.moose.operator.web.security.component;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-25 22:27:22:27
 * @see org.moose.operator.web.security.component
 */
@Slf4j
@Component
public class CustomBearerTokenExtractor extends BearerTokenExtractor {

  // TODO: BearerTokenExtractor
  @Override public Authentication extract(HttpServletRequest request) {
    String url = request.getRequestURI();
    log.info("CustomBearerTokenExtractor :: {}", url);
    if ("/api/v1/dynamic/recommend/list".equals(url)) {
      return null;
    }
    return super.extract(request);
  }
}
