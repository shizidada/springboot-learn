package org.moose.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.moose.operator.model.domain.FileRecordDO;

/**
 * @author taohua
 */
@Mapper
public interface FileRecordMapper {
  /**
   * select file record
   *
   * @param frId file record id
   * @return FileRecordDO
   */
  FileRecordDO selectById(String frId);

  /**
   * save upload file info
   *
   * @param fileRecordDOList List<FileRecordDO>
   * @return is success
   */
  boolean batchSaveFileRecord(List<FileRecordDO> fileRecordDOList);
}
