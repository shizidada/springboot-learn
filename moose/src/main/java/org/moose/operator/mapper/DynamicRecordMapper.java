package org.moose.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moose.operator.model.domain.DynamicRecordDO;

/**
 * @author taohua
 */
@Mapper
public interface DynamicRecordMapper {

  /**
   * insert a dynamic record
   *
   * @param dynamicRecordDO dynamic do
   * @return is success
   */
  boolean insertDynamicRecord(DynamicRecordDO dynamicRecordDO);

  /**
   * select dynamic record by userId
   *
   * @param userId userId
   * @return all record
   */
  List<DynamicRecordDO> selectByUserId(String userId);

  /**
   * select dynamic record order by createTime
   *
   * @return all record
   */
  List<DynamicRecordDO> selectBaseDynamicRecordInfo();

  /**
   * select dynamic record by dynamicId
   *
   * @param dynamicId dynamicId
   * @return DynamicRecordDO
   */
  DynamicRecordDO selectDynamicRecordByDynamicId(@Param("dynamicId") String dynamicId);


  /**
   * select dynamic record order by createTime
   *
   * @return all record
   */
  List<DynamicRecordDO> selectDynamicRecordWithAssociationInfo();
}
