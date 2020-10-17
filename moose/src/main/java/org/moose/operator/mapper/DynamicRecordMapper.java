package org.moose.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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
   */
  void insertDynamicRecord(DynamicRecordDO dynamicRecordDO);

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
  List<DynamicRecordDO> selectRecommendDynamicRecord();
}
