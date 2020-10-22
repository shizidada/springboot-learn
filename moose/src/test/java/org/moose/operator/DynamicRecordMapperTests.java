package org.moose.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.mapper.DynamicRecordAttachmentRelationMapper;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.model.params.SearchParam;
import org.moose.operator.web.service.DynamicRecordService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author taohua
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicRecordMapperTests {

  @Resource
  private DynamicRecordMapper dynamicRecordMapper;

  @Resource
  private DynamicRecordAttachmentRelationMapper recordAttachmentRelationMapper;

  @Resource
  private DynamicRecordService dynamicRecordService;

  @Resource
  private ObjectMapper objectMapper;

  @Test
  public void testSelectRecommendDynamicRecord() throws JsonProcessingException {
    //List<DynamicRecordDO> recordDOList = dynamicRecordMapper.selectDynamicRecordWithAssociationInfo();
    //List<DynamicRecordDO> recordDOList = dynamicRecordMapper.selectBaseDynamicRecordInfo();

    //List<DynamicRecordAttachmentRelationDO> attachmentRelationDOS =
    //    recordAttachmentRelationMapper.selectByDynamicRecordId("766462045200580606");

    SearchParam searchParam = new SearchParam();
    Map<String, Object> recommendDynamicRecordList = dynamicRecordService.getRecommendDynamicRecordByStep(searchParam);
    log.info("{}", objectMapper.writeValueAsString(recommendDynamicRecordList));
  }
}
