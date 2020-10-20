package org.moose.operator.util.callback;

/**
 * @author taohua
 */
@FunctionalInterface
public interface ListBeanUtilsCallBack<S, T> {

  /**
   * call back
   *
   * @param t source
   * @param s target
   */
  void callBack(S t, T s);
}
