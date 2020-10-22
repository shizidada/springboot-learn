package org.moose.operator.web.service;

import java.util.List;
import java.util.Map;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.model.params.SearchParam;

/**
 * @author taohua
 */
public interface DynamicRecordService {

  /**
   * save dynamic record
   *
   * @param dynamicRecordParam record
   * @return success
   */
  boolean saveDynamicRecord(DynamicRecordParam dynamicRecordParam);

  /**
   * get my dynamic record
   *
   * @return list of dynamic record me
   */
  List<DynamicRecordDTO> getMyDynamicRecord();

  /**
   * get recommend dynamic record
   *
   * @return list of recommend dynamic record
   * @param searchParam search param
   */
  Map<String, Object> getRecommendDynamicRecord(SearchParam searchParam);

  /**
   * get recommend dynamic record by step
   *
   * @return list of recommend dynamic record
   * @param searchParam search param
   */
  Map<String, Object> getRecommendDynamicRecordByStep(SearchParam searchParam);
}
