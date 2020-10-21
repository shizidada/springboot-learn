package org.moose.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.moose.operator.model.domain.DynamicRecordAttachmentRelationDO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-21 22:03:22:03
 * @see org.moose.operator.mapper
 */
@Mapper
public interface DynamicRecordAttachmentRelationMapper {

  /**
   * dynamic record and file record relation
   *
   * @param attachmentRelationDOList List<DynamicRecordAttachmentRelationDO>
   * @return is success
   */
  boolean batchInsertDynamicRecordRelation(
      List<DynamicRecordAttachmentRelationDO> attachmentRelationDOList);
}