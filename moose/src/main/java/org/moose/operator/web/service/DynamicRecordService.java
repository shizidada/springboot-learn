package org.moose.operator.web.service;

import java.util.List;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.moose.operator.model.params.DynamicRecordParam;

/**
 * @author taohua
 */
public interface DynamicRecordService {

  /**
   * save dynamic record
   *
   * @param dynamicRecordParam record
   */
  void saveDynamicRecord(DynamicRecordParam dynamicRecordParam);

  /**
   * get dynamic record by user id
   *
   * @return list of dynamic record
   */
  List<DynamicRecordDTO> getDynamicRecordByUserId();
}
