package org.moose.operator;

import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.mapper.DynamicRecordMapper;
import org.moose.operator.model.domain.DynamicRecordDO;
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

  @Test
  public void testSelectRecommendDynamicRecord() {
    List<DynamicRecordDO> recordDOList = dynamicRecordMapper.selectRecommendDynamicRecord2();
    log.info("{}", recordDOList);
  }
}
