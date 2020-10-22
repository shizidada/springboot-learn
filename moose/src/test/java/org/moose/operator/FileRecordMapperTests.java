package org.moose.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.mapper.FileRecordMapper;
import org.moose.operator.model.domain.FileRecordDO;
import org.moose.operator.util.SnowflakeIdWorker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author taohua
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileRecordMapperTests {

  @Resource
  private FileRecordMapper fileRecordMapper;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Test
  public void testBatchInsertFileRecord() throws JsonProcessingException {
    List<FileRecordDO> fileRecordDOList = new ArrayList<>();
    String userId = "768140533590654976";
    FileRecordDO fileRecordDO = new FileRecordDO();
    fileRecordDO.setFrId(String.valueOf(snowflakeIdWorker.nextId()));
    fileRecordDO.setUserId(userId);
    fileRecordDO.setETag("E1BAEB4D2F7E0BB252F7198305F61572");
    fileRecordDO.setFileUrl(
        "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/2020-10-20/18215ff73bf33f7caee159b86ee6e105.png");

    FileRecordDO fileRecordDO2 = new FileRecordDO();
    fileRecordDO2.setFrId(String.valueOf(snowflakeIdWorker.nextId()));
    fileRecordDO2.setUserId(userId);
    fileRecordDO2.setETag("D58A90A7E11F06F5549FA10ED13C6F22");
    fileRecordDO2.setFileUrl(
        "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/2020-10-20/117069af6b078909cf8c3a929c8962f7.png");

    FileRecordDO fileRecordDO3 = new FileRecordDO();
    fileRecordDO3.setFrId(String.valueOf(snowflakeIdWorker.nextId()));
    fileRecordDO3.setUserId(userId);
    fileRecordDO3.setETag("46D7E8DC27A725DE4A6C2C9045498EEC");
    fileRecordDO3.setFileUrl(
        "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/2020-10-20/0dcceff4c4123145654b87f10851bcbc.png");

    fileRecordDOList.add(fileRecordDO);
    fileRecordDOList.add(fileRecordDO2);
    fileRecordDOList.add(fileRecordDO3);
    fileRecordMapper.batchInsertFileRecord(fileRecordDOList);
  }
}
