package org.moose.operator.web.service;

import java.util.Map;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.vo.DynamicRecordVO;
import org.moose.operator.model.vo.SearchVO;

/**
 * @author taohua
 */
public interface DynamicRecordService {

  /**
   * save dynamic record
   *
   * @param dynamicRecordVO record
   * @return success
   */
  boolean saveDynamicRecord(DynamicRecordVO dynamicRecordVO);

  /**
   * get my dynamic record
   *
   * @param searchVO search param
   * @return list of dynamic record me
   */
  Map<String, Object> getMyDynamicRecord(SearchVO searchVO);

  /**
   * get recommend dynamic record by step
   *
   * @param searchVO search param
   * @return list of recommend dynamic record
   */
  Map<String, Object> getRecommendDynamicRecord(SearchVO searchVO);

  /**
   * get detail dynamic
   *
   * @param dynamicId dynamic Id
   * @return DynamicRecordDTO
   */
  DynamicRecordDTO getDetailDynamicRecord(String dynamicId);
}
