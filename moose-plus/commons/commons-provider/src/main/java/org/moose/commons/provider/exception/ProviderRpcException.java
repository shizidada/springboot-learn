package org.moose.commons.provider.exception;

import org.apache.dubbo.rpc.RpcException;
import org.moose.commons.base.dto.ResultCode;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/12 10:02
 * @see org.moose.commons.provider.exception
 */
public class ProviderRpcException extends RpcException {

  public ProviderRpcException() {}

  public ProviderRpcException(ResultCode resultCode, Object... args) {
    super(resultCode.getCode(), String.format(resultCode.getMessage(), args));
  }
}
