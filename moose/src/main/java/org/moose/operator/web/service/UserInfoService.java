package org.moose.operator.web.service;

import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.UserInfoDTO;

/**
 * @author taohua
 */
public interface UserInfoService {

  /**
   * mongodb save User
   *
   * @param userInfoDO user info
   * @return 是否保存成功
   */
  Boolean saveUserInfo(UserInfoDO userInfoDO);

  /**
   * mongo 数据库获取 User
   *
   * @param userId user id
   * @return User
   */
  UserInfoDTO getUserByUserId(String userId);

  /**
   * 获取 User 对应 Product
   *
   * @return User 对应 Product
   */
  Object getUserProducts();

  /**
   * 获取用户信息
   * @param accountId 账号 Id
   * @param accountName 账号
   * @return 用户信息
   */
  UserInfoDTO getUserInfo(Long accountId, String accountName);
}
