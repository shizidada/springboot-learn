package org.moose.operator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moose.operator.model.domain.UserInfoDO;

/**
 * @author taohua
 */
@Mapper
public interface UserInfoMapper {

  /**
   * 保存用户信息
   *
   * @param userInfoDO 用户信息
   */
  void insertUserInfo(UserInfoDO userInfoDO);

  /**
   * 根据 accountId and accountName 查询用户
   *
   * @param accountId 账号Id
   * @return 用户信息
   */
  UserInfoDO findByAccountId(@Param("accountId") String accountId);

  /**
   * 根据用户 Id 查询用户信息
   *
   * @param userId 用户Id
   * @return 用户信息
   */
  UserInfoDO findByUserId(@Param("userId") String userId);

  /**
   * 更新用户信息
   *
   * @param userInfo  用户信息
   * @param accountId 账号Id
   * @return 是否成功
   */
  Boolean updateUserInfoByAccountId(@Param("accountId") String accountId,
      @Param("userInfo") UserInfoDO userInfo);

  /**
   * 更新用户信息手机号码
   *
   * @param accountId 账号Id
   * @param phone     手机号码
   * @return 是否更新成功
   */
  Boolean updatePhoneByAccountId(@Param("accountId") String accountId,
      @Param("phone") String phone);
}
