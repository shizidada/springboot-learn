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
   * dynamic record and attachment record relation
   *
   * @param attachmentRelationDOList List<DynamicRecordAttachmentRelationDO>
   */
  void batchInsertDynamicRecordRelation(
      List<DynamicRecordAttachmentRelationDO> attachmentRelationDOList);

  /**
   * select dynamic record bind attachment file
   *
   * @param drId dynamic record id
   * @return DynamicRecordAttachmentRelationDO
   */
  List<DynamicRecordAttachmentRelationDO> selectByDynamicRecordId(String drId);
}
