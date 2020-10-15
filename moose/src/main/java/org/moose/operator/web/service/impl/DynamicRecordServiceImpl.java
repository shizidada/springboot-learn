package org.moose.operator.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.service.DynamicRecordService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author taohua
 */
@Service
public class DynamicRecordServiceImpl implements DynamicRecordService {

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
    return dynamicRecordList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());
  }

  private DynamicRecordDTO convertDTOFromDO(DynamicRecordDO dynamicRecordDO) {
    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordDO, dynamicRecordDTO);
    return dynamicRecordDTO;
  }
}
