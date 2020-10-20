package org.moose.operator.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moose.operator.model.domain.FileRecordDO;

/**
 * @author taohua
 */
@Mapper
public interface FileRecordMapper {
  /**
   * select file record
   *
   * @param userId user id
   * @param frId   file record id
   * @param tag    file record eTag
   * @return FileRecordDO
   */
  FileRecordDO selectByUserIdAndFrIdAndEtag(
      @Param("userId") String userId,
      @Param("frId") String frId,
      @Param("eTag") String tag);

  /**
   * save upload file info
   *
   * @param fileRecordDOList List<FileRecordDO>
   * @return is success
   */
  boolean batchInsertFileRecord(List<FileRecordDO> fileRecordDOList);
}
