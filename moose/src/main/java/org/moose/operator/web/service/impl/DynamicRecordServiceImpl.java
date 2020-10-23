package org.moose.operator.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.CommonConstants;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.AttachmentRecordMapper;
import org.moose.operator.mapper.DynamicRecordAttachmentRelationMapper;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.model.domain.AttachmentRecordDO;
import org.moose.operator.model.domain.DynamicRecordAttachmentRelationDO;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.AttachmentUploadDTO;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.dto.UserBaseInfoDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.AttachmentParam;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.model.params.SearchParam;
import org.moose.operator.util.ListBeanUtils;
import org.moose.operator.util.PageInfoUtils;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.AttachmentRecordService;
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
  private UserInfoService userInfoService;

  @Resource
  private AccountService accountService;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Resource
  private DynamicRecordMapper dynamicRecordMapper;

  @Resource
  private DynamicRecordAttachmentRelationMapper attachmentRelationMapper;

  @Resource
  private AttachmentRecordMapper attachmentRecordMapper;

  @Resource
  private AttachmentRecordService attachmentRecordService;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveDynamicRecord(DynamicRecordParam dynamicRecordParam) {
    List<AttachmentParam> attachmentIds = dynamicRecordParam.getAttachmentIds();
    if (ObjectUtils.isNotEmpty(attachmentIds)
        && attachmentIds.size() > CommonConstants.UPLOAD_ATTACHMENT_SIZE) {
      throw new BusinessException(ResultCode.UPLOAD_ATTACHMENT_SIZE_ERROR);
    }

    UserInfoDTO userInfo = userInfoService.getUserInfo();
    String userId = userInfo.getUserId();

    if (ObjectUtils.isNotEmpty(attachmentIds) && attachmentIds.size() > 0) {
      this.checkAttachment(attachmentIds, userId);
    }

    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordParam, dynamicRecordDTO);

    DynamicRecordDO dynamicRecordDO = new DynamicRecordDO();
    BeanUtils.copyProperties(dynamicRecordDTO, dynamicRecordDO);
    dynamicRecordDO.setUserId(userId);
    dynamicRecordDO.setDrId(String.valueOf(snowflakeIdWorker.nextId()));
    dynamicRecordMapper.insertDynamicRecord(dynamicRecordDO);

    if (ObjectUtils.isNotEmpty(attachmentIds)) {
      List<AttachmentUploadDTO> attachDTOList =
          ListBeanUtils.copyListProperties(attachmentIds, AttachmentUploadDTO::new);
      dynamicRecordDTO.setAttachments(attachDTOList);

      List<DynamicRecordAttachmentRelationDO> attachRelaDOList =
          new ArrayList<>(attachDTOList.size());

      // dynamicRecord rela attachmentRecord
      for (AttachmentUploadDTO attachmentUploadDTO : attachDTOList) {
        DynamicRecordAttachmentRelationDO attachRelaDO = new DynamicRecordAttachmentRelationDO();
        attachRelaDO.setAttachId(attachmentUploadDTO.getAttachmentId());
        attachRelaDO.setDrId(dynamicRecordDO.getDrId());
        attachRelaDO.setDraId(String.valueOf(snowflakeIdWorker.nextId()));
        attachRelaDOList.add(attachRelaDO);
      }

      if (attachRelaDOList.size() > 0) {
        attachmentRelationMapper.batchInsertDynamicRecordRelation(attachRelaDOList);
      }
    }
    return Boolean.TRUE;
  }

  @Override
  public List<DynamicRecordDTO> getMyDynamicRecord() {
    UserInfoDTO userInfo = userInfoService.getUserInfo();
    List<DynamicRecordDO> dynamicRecordList =
        dynamicRecordMapper.selectByUserId(userInfo.getUserId());
    return dynamicRecordList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());
  }

  @Override public Map<String, Object> getRecommendDynamicRecord(SearchParam searchParam) {
    PageHelper.startPage(searchParam);
    List<DynamicRecordDO> dynamicRecordList = dynamicRecordMapper.selectBaseDynamicRecordInfo();
    PageInfo<DynamicRecordDO> pageInfo = new PageInfo<>(dynamicRecordList);
    if (ObjectUtils.isEmpty(pageInfo)) {
      return null;
    }
    List<DynamicRecordDTO> dynamicRecordDTOList =
        pageInfo.getList().stream().map(this::convertDTOFromDO).collect(Collectors.toList());
    Map<String, Object> basePageInfo = PageInfoUtils.getBasePageInfo(pageInfo);
    basePageInfo.put("lists", dynamicRecordDTOList);
    return basePageInfo;
  }

  @Override public Map<String, Object> getRecommendDynamicRecordByStep(SearchParam searchParam) {
    PageHelper.startPage(searchParam);
    List<DynamicRecordDO> dynamicRecordList = dynamicRecordMapper.selectBaseDynamicRecordInfo();
    PageInfo<DynamicRecordDO> pageInfo = new PageInfo<>(dynamicRecordList);
    List<DynamicRecordDO> dynamicRecordDOList = pageInfo.getList();
    List<DynamicRecordDTO> dynamicRecordDTOList =
        dynamicRecordDOList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());
    Map<String, Object> basePageInfo = PageInfoUtils.getBasePageInfo(pageInfo);
    basePageInfo.put("lists", dynamicRecordDTOList);
    return basePageInfo;
  }

  private void checkAttachment(List<AttachmentParam> attachmentIds, String userId) {
    for (AttachmentParam attachmentParam : attachmentIds) {
      String attachmentId = attachmentParam.getAttachmentId();
      String tag = attachmentParam.getTag();
      AttachmentUploadDTO attachmentRecord = attachmentRecordService.getAttachmentRecord(userId, attachmentId, tag);
      if (ObjectUtils.isEmpty(attachmentRecord)) {
        throw new BusinessException(ResultCode.UPLOAD_ATTACHMENT_RECORD_NOT_EXIST);
      }
    }
  }

  /**
   * convert DO to DTO
   *
   * @param dynamicRecordDO data object
   * @return dto
   */
  private DynamicRecordDTO convertDTOFromDO(DynamicRecordDO dynamicRecordDO) {
    if (ObjectUtils.isEmpty(dynamicRecordDO)) {
      return null;
    }

    String userId = dynamicRecordDO.getUserId();
    String drId = dynamicRecordDO.getDrId();

    List<DynamicRecordAttachmentRelationDO> attachmentRelationDOList =
        attachmentRelationMapper.selectByDynamicRecordId(drId);

    List<AttachmentUploadDTO> uploadDTOList =
        attachmentRelationDOList.stream().map((attachmentRelationDO) -> {
          String attachId = attachmentRelationDO.getAttachId();
          AttachmentRecordDO attachmentRecordDO = attachmentRecordMapper.selectByFrId(attachId);
          if (ObjectUtils.isEmpty(attachmentRecordDO)) {
            return null;
          }
          AttachmentUploadDTO attachmentUploadDTO = new AttachmentUploadDTO();
          attachmentUploadDTO.setAttachmentId(attachmentRecordDO.getAttachId());
          attachmentUploadDTO.setAttachmentUrl(attachmentRecordDO.getFileUrl());
          attachmentUploadDTO.setTag(attachmentRecordDO.getETag());
          return attachmentUploadDTO;
        }).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());

    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordDO, dynamicRecordDTO);
    dynamicRecordDTO.setAttachments(uploadDTOList);

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
