package org.moose.operator.model.emun;

/**
 * @author taohua
 */

public enum GenderEnum {

  /**
   * male
   */
  MALE("1"),
  /**
   * female
   */
  FEMALE("2"),
  /**
   * un_known or hide
   */
  UN_KNOWN("0"),
  ;

  private String value;

  GenderEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
