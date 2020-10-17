package org.moose.operator.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.dto.UserBaseInfoDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.DynamicRecordService;
import org.moose.operator.web.service.UserInfoCacheService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author taohua
 */
@Service
public class DynamicRecordServiceImpl implements DynamicRecordService {

  @Resource
  private UserInfoService userInfoService;

  @Resource
  private AccountService accountService;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Resource
  private DynamicRecordMapper dynamicRecordMapper;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Transactional(rollbackFor = Exception.class)
  @Override public void saveDynamicRecord(DynamicRecordParam dynamicRecordParam) {
    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordParam, dynamicRecordDTO);

    DynamicRecordDO dynamicRecordDO = new DynamicRecordDO();
    BeanUtils.copyProperties(dynamicRecordDTO, dynamicRecordDO);
    dynamicRecordDO.setDrId(String.valueOf(snowflakeIdWorker.nextId()));

    UserInfoDTO userInfo = userInfoService.getUserInfo();
    dynamicRecordDO.setUserId(userInfo.getUserId());
    dynamicRecordMapper.insertDynamicRecord(dynamicRecordDO);
  }

  @Override public List<DynamicRecordDTO> getMyDynamicRecord() {
    UserInfoDTO userInfo = userInfoService.getUserInfo();
    List<DynamicRecordDO> dynamicRecordList =
        dynamicRecordMapper.selectByUserId(userInfo.getUserId());
    return dynamicRecordList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());
  }

  @Override public List<DynamicRecordDTO> getRecommendDynamicRecord() {
    List<DynamicRecordDO> dynamicRecordList =
        dynamicRecordMapper.selectRecommendDynamicRecord();
    return dynamicRecordList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());
  }

  /**
   * convert DO to DTO
   * @param dynamicRecordDO data object
   * @return dto
   */
  private DynamicRecordDTO convertDTOFromDO(DynamicRecordDO dynamicRecordDO) {
    if (ObjectUtils.isEmpty(dynamicRecordDO)) {
      return null;
    }
    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordDO, dynamicRecordDTO);
    String userId = dynamicRecordDO.getUserId();
    String drId = dynamicRecordDO.getDrId();

    UserInfoDO author = dynamicRecordDO.getAuthor();
    if (ObjectUtils.isNotEmpty(author)) {
      UserBaseInfoDTO userBaseInfo = new UserBaseInfoDTO();
      BeanUtils.copyProperties(author, userBaseInfo);
      dynamicRecordDTO.setAuthor(userBaseInfo);
    }
    String likedKey = String.format(RedisKeyConstants.USER_LIKED_KEY, userId);
    Integer liked = (Integer) redisTemplate.opsForHash().get(likedKey, drId);
    dynamicRecordDTO.setLike((null == liked || liked == 0) ? 0 : 1);
    return dynamicRecordDTO;
  }
}
