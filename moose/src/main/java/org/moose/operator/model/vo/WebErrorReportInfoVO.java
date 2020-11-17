package org.moose.operator.model.vo;

import lombok.Data;

/**
 * @author taohua
 */

@Data
public class WebErrorReportInfoVO {

  /**
   * 监控应用名称
   */
  private String appName;

  /**
   * 错误信息
   */
  private String errorMessage;

  /**
   * 错误来源
   */
  private String errorFrom;

  /**
   * 错误栈信息
   */
  private String errorStack;

  /**
   * 渲染组件栈
   */
  private String componentStack;

  /**
   * 错误界面
   */
  private String href;

  /**
   * 错误资源
   */
  private String source;

  /**
   * 错误行
   */
  private Long row;

  /**
   * 错误列
   */
  private Long col;

  /**
   * 浏览器版本
   */
  private String browerVersion;

  /**
   * 浏览器代码名
   */
  private String browerCodeName;

  /**
   * 浏览器名称
   */
  private String browerName;

  /**
   * 浏览器平台
   */
  private String browerPlatform;

  /**
   * 浏览器 agent
   */
  private String browerUserAgent;

  /**
   * 浏览器当前语言
   */
  private String browerLanguage;
}
