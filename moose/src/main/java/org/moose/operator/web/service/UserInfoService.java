package org.moose.operator.web.service;

import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.UserInfoDTO;

/**
 * @author taohua
 */
public interface UserInfoService {

  /**
   * save User
   *
   * @param userInfoDO user info
   * @return 是否保存成功
   */
  void saveUserInfo(UserInfoDO userInfoDO);

  /**
   * 获取 User
   *
   * @param userId user id
   * @return User
   */
  UserInfoDTO getUserInfoByUserId(String userId);

  /**
   * 获取用户信息
   *
   * @param accountId   账号 Id
   * @return 用户信息
   */
  UserInfoDTO getUserInfoByAccountId(String accountId);

  /**
   * 更新用户信息
   *
   * @param userInfoDTO 用户信息
   * @return ResponseResult
   */
  Boolean updateUserInfo(UserInfoDTO userInfoDTO);
}
