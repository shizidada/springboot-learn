package org.moose.operator.web.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.model.dto.UserBaseInfoDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.web.service.UserInfoCacheService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-17 20:32:20:32
 * @see org.moose.operator.web.service.impl
 */
@Service
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Override public UserInfoDTO getUserInfoFromCacheByAccountId(String accountId) {
    String userInfoKey = String.format(RedisKeyConstants.USER_INFO_KEY, accountId);
    return (UserInfoDTO) redisTemplate.opsForValue().get(userInfoKey);
  }
  @Override
  public void saveUserInfoToCacheByAccountId(String accountId, UserBaseInfoDTO userInfoDTO) {
    String userInfoKey = String.format(RedisKeyConstants.USER_INFO_KEY, accountId);
    redisTemplate.opsForValue().set(userInfoKey, userInfoDTO);
  }

  @Override public UserInfoDTO getUserInfoFromCacheByUserId(String userId) {
    String userInfoKey = String.format(RedisKeyConstants.USER_BASE_INFO_KEY, userId);
    return (UserInfoDTO) redisTemplate.opsForValue().get(userInfoKey);
  }

  @Override public UserBaseInfoDTO getUserBaseInfoFromCacheByUserId(String userId) {
    String userInfoKey = String.format(RedisKeyConstants.USER_BASE_INFO_KEY, userId);
    UserInfoDTO userInfoDTO = (UserInfoDTO)redisTemplate.opsForValue().get(userInfoKey);
    if (ObjectUtils.isEmpty(userInfoDTO)) {
      return null;
    }
    UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
    BeanUtils.copyProperties(userInfoDTO, userBaseInfoDTO);
    return userBaseInfoDTO;
  }

  @Override public void saveUserInfoToCacheByUserId(String userId, UserBaseInfoDTO userInfoDTO) {
    String userInfoKey = String.format(RedisKeyConstants.USER_BASE_INFO_KEY, userId);
    redisTemplate.opsForValue().set(userInfoKey, userInfoDTO);
  }
}
