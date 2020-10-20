package org.moose.operator.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.CommonConstants;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.mapper.FileRecordMapper;
import org.moose.operator.model.domain.DynamicRecordDO;
import org.moose.operator.model.domain.FileRecordDO;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.dto.FileUploadDTO;
import org.moose.operator.model.dto.UserBaseInfoDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.model.params.AttachmentParam;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.model.params.SearchParam;
import org.moose.operator.util.ListBeanUtils;
import org.moose.operator.util.PageInfoUtils;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.service.AccountService;
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
  private FileRecordMapper fileRecordMapper;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Transactional(rollbackFor = Exception.class)
  @Override public boolean saveDynamicRecord(DynamicRecordParam dynamicRecordParam) {
    List<AttachmentParam> attachmentIds = dynamicRecordParam.getAttachmentIds();
    if (ObjectUtils.isNotEmpty(attachmentIds)
        && attachmentIds.size() > CommonConstants.UPLOAD_ATTACHMENT_SIZE) {
      throw new BusinessException(ResultCode.UPLOAD_ATTACHMENT_SIZE_ERROR);
    }

    UserInfoDTO userInfo = userInfoService.getUserInfo();
    String userId = userInfo.getUserId();

    if (ObjectUtils.isNotEmpty(attachmentIds)) {
      this.checkAttachment(attachmentIds, userId);
    }

    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    BeanUtils.copyProperties(dynamicRecordParam, dynamicRecordDTO);
    if (ObjectUtils.isNotEmpty(attachmentIds)) {
      List<FileUploadDTO> attachmentList =
          ListBeanUtils.copyListProperties(attachmentIds, FileUploadDTO::new);
      dynamicRecordDTO.setAttachmentFiles(attachmentList);
    }

    DynamicRecordDO dynamicRecordDO = new DynamicRecordDO();
    BeanUtils.copyProperties(dynamicRecordDTO, dynamicRecordDO);
    dynamicRecordDO.setUserId(userId);
    dynamicRecordDO.setDrId(String.valueOf(snowflakeIdWorker.nextId()));

    // TODO: dynamicRecord rel fileRecord

    dynamicRecordMapper.insertDynamicRecord(dynamicRecordDO);
    return Boolean.TRUE;
  }

  @Override public List<DynamicRecordDTO> getMyDynamicRecord() {
    UserInfoDTO userInfo = userInfoService.getUserInfo();
    List<DynamicRecordDO> dynamicRecordList =
        dynamicRecordMapper.selectByUserId(userInfo.getUserId());
    return dynamicRecordList.stream().map(this::convertDTOFromDO).collect(Collectors.toList());
  }

  @Override public Map<String, Object> getRecommendDynamicRecord(SearchParam searchParam) {
    PageHelper.startPage(searchParam);
    List<DynamicRecordDO> dynamicRecordList = dynamicRecordMapper.selectRecommendDynamicRecord();
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

  private void checkAttachment(List<AttachmentParam> attachmentIds, String userId) {
    if (attachmentIds.size() > 0) {
      for (AttachmentParam attachmentParam : attachmentIds) {
        String attachmentId = attachmentParam.getAttachmentId();
        String tag = attachmentParam.getTag();
        FileRecordDO fileRecordDO =
            fileRecordMapper.selectByUserIdAndFrIdAndEtag(userId, attachmentId, tag);
        if (ObjectUtils.isEmpty(fileRecordDO)) {
          throw new BusinessException(ResultCode.UPLOAD_ATTACHMENT_RECORD_NOT_EXIST);
        }
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
