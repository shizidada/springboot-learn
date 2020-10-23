package org.moose.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moose.operator.model.domain.AttachmentRecordDO;

/**
 * @author taohua
 */
@Mapper
public interface AttachmentRecordMapper {
  /**
   * select attachment record
   *
   * @param userId user id
   * @param attachId   attachment record id
   * @param tag    attachment record eTag
   * @return AttachmentRecordDO
   */
  AttachmentRecordDO selectByUserIdAndFrIdAndEtag(
      @Param("userId") String userId,
      @Param("attachId") String attachId,
      @Param("eTag") String tag);

  /**
   * save upload file info
   *
   * @param attachmentRecordDOList List<AttachmentRecordDO>
   * @return is success
   */
  void batchInsertAttachmentRecord(List<AttachmentRecordDO> attachmentRecordDOList);

  /**
   * select attachment record by attachment record id
   *
   * @param attachId attachment record id
   * @return AttachmentRecordDO
   */
  AttachmentRecordDO selectByFrId(String attachId);
}
