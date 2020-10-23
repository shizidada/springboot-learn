package org.moose.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.mapper.AttachmentRecordMapper;
import org.moose.operator.model.domain.AttachmentRecordDO;
import org.moose.operator.util.SnowflakeIdWorker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author taohua
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachmentRecordMapperTests {

  @Resource
  private AttachmentRecordMapper attachmentRecordMapper;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Test
  public void testBatchInsertAttachmentRecord() throws JsonProcessingException {
    List<AttachmentRecordDO> attachmentRecordDOList = new ArrayList<>();
    String userId = "768140533590654976";
    AttachmentRecordDO attachmentRecordDO = new AttachmentRecordDO();
    attachmentRecordDO.setAttachId(String.valueOf(snowflakeIdWorker.nextId()));
    attachmentRecordDO.setUserId(userId);
    attachmentRecordDO.setETag("E1BAEB4D2F7E0BB252F7198305F61572");
    attachmentRecordDO.setFileUrl(
        "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/2020-10-20/18215ff73bf33f7caee159b86ee6e105.png");

    AttachmentRecordDO attachmentRecordDO2 = new AttachmentRecordDO();
    attachmentRecordDO2.setAttachId(String.valueOf(snowflakeIdWorker.nextId()));
    attachmentRecordDO2.setUserId(userId);
    attachmentRecordDO2.setETag("D58A90A7E11F06F5549FA10ED13C6F22");
    attachmentRecordDO2.setFileUrl(
        "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/2020-10-20/117069af6b078909cf8c3a929c8962f7.png");

    AttachmentRecordDO attachmentRecordDO3 = new AttachmentRecordDO();
    attachmentRecordDO3.setAttachId(String.valueOf(snowflakeIdWorker.nextId()));
    attachmentRecordDO3.setUserId(userId);
    attachmentRecordDO3.setETag("46D7E8DC27A725DE4A6C2C9045498EEC");
    attachmentRecordDO3.setFileUrl(
        "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/2020-10-20/0dcceff4c4123145654b87f10851bcbc.png");

    attachmentRecordDOList.add(attachmentRecordDO);
    attachmentRecordDOList.add(attachmentRecordDO2);
    attachmentRecordDOList.add(attachmentRecordDO3);
    attachmentRecordMapper.batchInsertAttachmentRecord(attachmentRecordDOList);
  }
}
