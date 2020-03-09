package org.excel.operator.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author shizi
 */
public class RedisTokenException extends AuthenticationException {

  public RedisTokenException(String msg) {
    super(msg);
  }
}
