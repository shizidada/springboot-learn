package org.moose.operator.web.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.AccountMapper;
import org.moose.operator.mapper.UserInfoMapper;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.UserInfoParam;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.BeanUtils;
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

  @Override
  public void saveUserInfo(UserInfoDO userInfoDO) {
    userInfoMapper.insertUserInfo(userInfoDO);
  }

  @Override public UserInfoDTO getUserInfoByAccountId(String accountId) {
    //Query query =
    //    Query.query(Criteria.where("account_id").is(accountId).and("account_name").is(accountName));
    //UserInfoDO userInfoDO = this.mongoTemplate.findOne(query, UserInfoDO.class);

    UserInfoDO userInfoDO =
        userInfoMapper.findByAccountId(accountId);
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
  public Boolean updateUserInfo(UserInfoParam userInfoParam) {
    if (ObjectUtils.isEmpty(userInfoParam)) {
      throw new BusinessException(ResultCode.USER_INFO_NOT_EXIST);
    }

    UserInfoDTO userInfoDTO = new UserInfoDTO();
    BeanUtils.copyProperties(userInfoParam, userInfoDTO);

    Object principal = accountService.getPrincipal();
    CustomUserDetails userDetails = (CustomUserDetails) principal;
    AccountDTO accountDTO = userDetails.getAccountDTO();
    String accountId = accountDTO.getAccountId();

    accountMapper.updateAccountNameByAccountId(userInfoDTO.getUserName(), accountId);

    UserInfoDO userInfoDO = new UserInfoDO();
    BeanUtils.copyProperties(userInfoDTO, userInfoDO);
    return userInfoMapper.updateUserInfoByAccountId(accountId, userInfoDO);
  }
}
