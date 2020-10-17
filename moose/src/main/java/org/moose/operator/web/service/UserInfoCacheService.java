package org.moose.operator.web.service;

import org.moose.operator.model.dto.UserBaseInfoDTO;
import org.moose.operator.model.dto.UserInfoDTO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-17 20:32:20:32
 * @see org.moose.operator.web.service
 */
public interface UserInfoCacheService {

  /**
   * save user info redis cache
   *
   * @param accountId accountId
   * @param userInfoDTO user info
   */
  void saveUserInfoToCacheByAccountId(String accountId, UserBaseInfoDTO userInfoDTO);

  /**
   * save user info redis cache userId
   *
   * @param userId userId
   * @param userInfoDTO user info
   */
  void saveUserInfoToCacheByUserId(String userId, UserBaseInfoDTO userInfoDTO);

  /**
   * get user info form redis cache by accountId
   *
   * @param accountId accountId
   * @return user info
   */
  UserInfoDTO getUserInfoFromCacheByAccountId(String accountId);

  /**
   * get user info form redis cache by userId
   *
   * @param userId userId
   * @return user info
   */
  UserInfoDTO getUserInfoFromCacheByUserId(String userId);

  /**
   * 获取 User
   *
   * @param userId user id
   * @return User
   */
  UserBaseInfoDTO getUserBaseInfoFromCacheByUserId(String userId);
}
