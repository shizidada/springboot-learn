package org.moose.operator.web.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SmsTypeConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.AccountMapper;
import org.moose.operator.mapper.UserInfoMapper;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.SmsCodeDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.UserInfoParam;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author taohua
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Resource
  private UserInfoMapper userInfoMapper;

  @Resource
  private AccountService accountService;

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveUserInfo(UserInfoDO userInfoDO) {
    if (!userInfoMapper.insertUserInfo(userInfoDO)) {
      throw new BusinessException(ResultCode.USER_INFO_SAVE_FAIL);
    }
    return Boolean.TRUE;
  }

  @Override public UserInfoDTO getUserInfoByAccountId(String accountId) {
    //Query query =
    //    Query.query(Criteria.where("account_id").is(accountId).and("account_name").is(accountName));
    //UserInfoDO userInfoDO = this.mongoTemplate.findOne(query, UserInfoDO.class);

    UserInfoDO userInfoDO = userInfoMapper.findByAccountId(accountId);
    if (ObjectUtils.isEmpty(userInfoDO)) {
      return null;
    }
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    BeanUtils.copyProperties(userInfoDO, userInfoDTO);
    return userInfoDTO;
  }

  @Override
  public UserInfoDTO getUserInfoByUserId(String userId) {
    UserInfoDO userInfoDO = userInfoMapper.findByUserId(userId);
    if (ObjectUtils.isEmpty(userInfoDO)) {
      return null;
    }
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    BeanUtils.copyProperties(userInfoDO, userInfoDTO);
    return userInfoDTO;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateUserInfo(UserInfoParam userInfoParam) {
    if (ObjectUtils.isEmpty(userInfoParam)) {
      throw new BusinessException(ResultCode.USER_INFO_NOT_EXIST);
    }

    UserInfoDTO userInfoDTO = new UserInfoDTO();
    BeanUtils.copyProperties(userInfoParam, userInfoDTO);

    Object principal = accountService.getPrincipal();
    CustomUserDetails userDetails = (CustomUserDetails) principal;
    AccountDTO accountDTO = userDetails.getAccountDTO();
    String accountId = accountDTO.getAccountId();

    if (!accountMapper.updateAccountNameByAccountId(userInfoDTO.getUserName(), accountId)) {
      throw new BusinessException(ResultCode.USER_INFO_UPDATE_FAIL);
    }

    UserInfoDO userInfoDO = new UserInfoDO();
    BeanUtils.copyProperties(userInfoDTO, userInfoDO);

    if (!userInfoMapper.updateUserInfoByAccountId(accountId, userInfoDO)) {
      throw new BusinessException(ResultCode.USER_INFO_UPDATE_FAIL);
    }
    return Boolean.TRUE;
  }

  @Override public UserInfoDTO getUserInfo() {
    AccountDTO accountDTO = accountService.getAccountInfo();
    String accountId = accountDTO.getAccountId();
    UserInfoDTO userInfoDTO = this.getUserInfoByAccountId(accountId);
    if (ObjectUtils.isEmpty(userInfoDTO)) {
      throw new BusinessException(ResultCode.USER_INFO_NOT_EXIST);
    }
    return userInfoDTO;
  }

  // TODO: 变更手机号码
  @Transactional(rollbackFor = Exception.class)
  @Override public boolean resetPhone(String phone, String smsCode) {
    if (StringUtils.isEmpty(phone)) {
      throw new BusinessException(ResultCode.PHONE_NUMBER_IS_EMPTY);
    }
    if (StringUtils.isEmpty(smsCode)) {
      throw new BusinessException(ResultCode.SMS_CODE_IS_EMPTY);
    }
    AccountDTO accountDTO = accountService.getAccountInfo();
    UserInfoDTO userInfoDTO = this.getUserInfoByAccountId(accountDTO.getAccountId());
    if (StringUtils.equals(phone, userInfoDTO.getPhone())) {
      throw new BusinessException(ResultCode.PHONE_EXITS_WITH_CURRENT);
    }

    String smsCodeKey =
        String.format(RedisKeyConstants.SMS_CODE_KEY, SmsTypeConstants.RESET_PHONE, phone);
    SmsCodeDTO smsCodeDTO = (SmsCodeDTO) redisTemplate.opsForValue().get(smsCodeKey);
    if (ObjectUtils.isEmpty(smsCodeDTO) || smsCodeDTO.getExpired()) {
      throw new BusinessException(ResultCode.SMS_CODE_ERROR);
    }

    if (accountService.updateAccountPhone(accountDTO.getAccountId(), phone)) {
      if (!userInfoMapper.updatePhoneByAccountId(accountDTO.getAccountId(), phone)) {
        throw new BusinessException(ResultCode.PHONE_RESET_FAIL);
      }
    }
    return Boolean.TRUE;
  }
}
