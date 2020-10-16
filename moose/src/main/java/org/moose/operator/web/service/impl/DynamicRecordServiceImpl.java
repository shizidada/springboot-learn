package org.moose.operator.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.service.DynamicRecordService;
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
  private RedisTemplate<String, Object> redisTemplate;

  @Resource
  private DynamicRecordMapper dynamicRecordMapper;

  @Resource
  private UserInfoService userInfoService;

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

  @Override public List<DynamicRecordDTO> getDynamicRecordByUserId() {
    UserInfoDTO userInfo = userInfoService.getUserInfo();
    List<DynamicRecordDO> dynamicRecordList =
        dynamicRecordMapper.selectByUserId(userInfo.getUserId());

    List<DynamicRecordDTO> dynamicRecordDTOList =
        dynamicRecordList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());

    String userLikedKey = String.format(RedisKeyConstants.USER_LIKED_KEY, userInfo.getUserId());
    dynamicRecordDTOList.forEach(d -> {
      String drId = d.getDrId();
      Integer liked = (Integer) redisTemplate.opsForHash().get(userLikedKey, drId);
      d.setLike((null == liked || liked == 0) ? 0 : 1);
    });

    return dynamicRecordDTOList;
  }

  private DynamicRecordDTO convertDTOFromDO(DynamicRecordDO dynamicRecordDO) {
    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordDO, dynamicRecordDTO);
    return dynamicRecordDTO;
  }
}
