package org.moose.operator;

import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.model.dto.UserGrowthDTO;
import org.moose.operator.web.service.UserGrowthService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-28 22:47:22:47
 * @see org.moose.operator
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserGrowthServiceTests {

  @Resource
  private UserGrowthService userGrowthService;

  @Test
  public void testAddUserGrowth() {
    UserGrowthDTO userGrowthDTO = new UserGrowthDTO();
    userGrowthDTO.setUgId("771135272648701898");
    userGrowthDTO.setUserId("771135272648704000");
    userGrowthDTO.setGrowth(100);
    userGrowthDTO.setCreateTime(LocalDateTime.now());
    userGrowthDTO.setUpdateTime(LocalDateTime.now());

    userGrowthService.addGrowth(userGrowthDTO);
  }

  @Test
  public void testUpdateUserGrowth() {
    UserGrowthDTO userGrowthDTO = new UserGrowthDTO();
    userGrowthDTO.setUserId("771135272648704000");
    userGrowthDTO.setGrowth(-1000);
    userGrowthService.updateGrowth(userGrowthDTO);
  }
}
