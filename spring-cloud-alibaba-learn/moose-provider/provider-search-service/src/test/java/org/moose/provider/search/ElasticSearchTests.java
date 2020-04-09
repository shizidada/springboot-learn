package org.moose.provider.search;

import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/9 23:17
 * @see org.moose.provider.search
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ElasticSearchTests {

  @Resource
  private RestHighLevelClient restHighLevelClient;

  @Test
  public void testCreateIndex() throws IOException {
    CreateIndexRequest createIndexRequest = new CreateIndexRequest("order_index");
    CreateIndexResponse response =
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    log.info(response.index());
  }
}
