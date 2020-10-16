package org.moose.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.model.dto.DynamicRecordDTO;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-16 13:26:13:26
 * @see org.moose.operator
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTests {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Resource
  private ObjectMapper objectMapper;

  // string
  @Test
  public void testRedisOps() throws JsonProcessingException {
    String userLikedKey = "moose:user:liked:764507445744631808";
    //redisTemplate.opsForValue().set("moose:string", "temp");
    //redisTemplate.opsForHash().put("moose:user:liked:764507445744631808", "766462045200580606", 1);
    //redisTemplate.opsForHash().put("moose:user:liked:764507445744631808", "766462045200580607", 0);
    //redisTemplate.opsForHash().put("moose:user:liked:764507445744631808", "766462045200580608", 1);\

    List<DynamicRecordDTO> recordDTOList = new ArrayList<>();

    DynamicRecordDTO dynamicRecordDTO = new DynamicRecordDTO();
    dynamicRecordDTO.setDrId("766462045200580606");
    dynamicRecordDTO.setTitle("你今天吃饭了吗？");
    dynamicRecordDTO.setDescription("戴上耳机 \uD83C\uDFA7 \n 点击左上角的最大化按钮");

    DynamicRecordDTO dynamicRecordDTO2 = new DynamicRecordDTO();
    dynamicRecordDTO2.setDrId("766462045200580607");
    dynamicRecordDTO2.setTitle("走，一起去月亮上看看");
    dynamicRecordDTO2.setDescription("等待放假的日子里\n 百无聊赖的雀雀\n 用文档给大家做了一个游戏");

    DynamicRecordDTO dynamicRecordDTO3 = new DynamicRecordDTO();
    dynamicRecordDTO3.setDrId("766462045200580608");
    dynamicRecordDTO3.setTitle("先处理情绪，再解决事情");
    dynamicRecordDTO3.setDescription(
        "最近我正尝试着换个角度来看待成长这件事情，去思考，别人因为我的存在学到了什么东西，别人因为我的存在做成了什么事情，别人因为我的存在解决了什么问题，这就有点意思了，也就是说，我用他人的成长来反映我的成长，而他人的成长是受我影响的，这需要彻底打破之前的观念。");

    recordDTOList.add(dynamicRecordDTO);
    recordDTOList.add(dynamicRecordDTO2);
    recordDTOList.add(dynamicRecordDTO3);

    recordDTOList.forEach(d -> {
      String drId = d.getDrId();
      Integer liked = (Integer) redisTemplate.opsForHash().get(userLikedKey, drId);
      d.setLike((null == liked || liked == 0) ? 0 : 1);
    });

    log.info("recordDTOList :: {} ", objectMapper.writeValueAsString(recordDTOList));
  }
}
