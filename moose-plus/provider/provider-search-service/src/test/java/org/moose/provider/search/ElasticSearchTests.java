package org.moose.provider.search;

import com.alibaba.fastjson.JSON;
import com.github.jsonzou.jmockdata.JMockData;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.order.model.domain.OrderDO;
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
  private static final String ORDER_INDEX = "order_service";

  private static final RequestOptions defaultOption = RequestOptions.DEFAULT;

  @Resource
  private RestHighLevelClient restHighLevelClient;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Test
  public void testCreateIndex() throws IOException {
    CreateIndexRequest createIndexRequest = new CreateIndexRequest(ORDER_INDEX);
    CreateIndexResponse response =
        restHighLevelClient.indices().create(createIndexRequest, defaultOption);
    log.info(response.index());
  }

  @Test
  public void testMock() {
    OrderDO mock = JMockData.mock(OrderDO.class);
    log.info("mock data {}", mock);
  }

  @Test
  public void testSnowflakeIdWorker() {
    for (int i = 0; i < 1000; i++) {
      long nextId = snowflakeIdWorker.nextId();
      log.info("current generator id: {}", nextId);
    }
  }

  @Test
  public void testAddData() throws IOException {
    IndexRequest indexRequest = new IndexRequest(ORDER_INDEX, "order_info");

    OrderDO order = new OrderDO();
    order.setOrderId(snowflakeIdWorker.nextId());
    order.setAccountId(snowflakeIdWorker.nextId());
    order.setProductId(snowflakeIdWorker.nextId());
    order.setOrderStatus(0);
    order.setPayStatus(0);
    order.setOrderAmount(new BigDecimal("1299.78"));
    order.setCreateTime(LocalDateTime.now());
    order.setUpdateTime(LocalDateTime.now());

    IndexRequest source = indexRequest.source(JSON.toJSONString(order), XContentType.JSON);
    source.id(String.valueOf(order.getOrderId()));

    IndexResponse index = restHighLevelClient.index(source);
    log.info("current json data : {}", JSON.toJSONString(index));
  }

  @Test
  public void testGetRequest() throws IOException {
    GetRequest request = new GetRequest(ORDER_INDEX, "order_info", "698184454709231616");
    GetResponse response = restHighLevelClient.get(request, defaultOption);
    Map<String, Object> source = response.getSource();
    log.info("get result {}", JSON.toJSONString(source));
  }

  @Test
  public void bulkSave() throws IOException {
    BulkRequest request = new BulkRequest();
    for (int i = 0; i < 10000; i++) {
      request.add(new IndexRequest(ORDER_INDEX, "order_info").source(
          JSON.toJSONString(JMockData.mock(OrderDO.class)), XContentType.JSON));
    }
    restHighLevelClient.bulk(request, defaultOption);
  }

  @Test
  public void testDelete() throws IOException {
    DeleteRequest request = new DeleteRequest(ORDER_INDEX);
    request.type("order_info");
    request.id("698184454709231616");
    DeleteResponse deleteResponse = restHighLevelClient.delete(request, defaultOption);
    log.info("delete :: {}", JSON.toJSONString(deleteResponse));
  }

  @Test
  public void testSearchRequest() throws IOException {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    MatchQueryBuilder query = QueryBuilders.matchQuery("accountId", "698184454709231617");
    searchSourceBuilder.query(query);

    SearchRequest searchRequest = new SearchRequest(ORDER_INDEX);
    searchRequest.types("order_info");
    searchRequest.source(searchSourceBuilder);

    //response 为查询结果集
    SearchResponse response = restHighLevelClient.search(searchRequest);
    SearchHit[] hits = response.getHits().getHits();
    for (SearchHit hit : hits) {
      OrderDO orderDO = JSON.parseObject(hit.getSourceAsString(), OrderDO.class);
      log.info("search hit : {}", orderDO);
    }
  }
}