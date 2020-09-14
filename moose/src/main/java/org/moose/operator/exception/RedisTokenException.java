package org.moose.operator.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author taohua
 */
public class RedisTokenException extends AuthenticationException {

  public RedisTokenException(String msg) {
    super(msg);
  }
}
