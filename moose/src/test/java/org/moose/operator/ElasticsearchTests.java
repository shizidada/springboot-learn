package org.moose.operator;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.model.entity.ExcelInfoEntity;
import org.moose.operator.model.entity.OrderEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author taohua
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {

  @Resource
  private RestHighLevelClient client;

  @Resource
  private RequestOptions defaultRequestOptions;

  @Resource
  private ObjectMapper objectMapper;

  @Test
  public void ping() throws IOException {
    log.info("ping {}", client.ping(defaultRequestOptions));
  }

  @Test
  public void testCreateIndex() throws IOException {
    // -Des.set.netty.runtime.available.processors=false
    //System.setProperty("es.set.netty.runtime.available.processors", "false");
    IndexRequest indexRequest = new IndexRequest("order_info_service", "excel_record");
    IndexResponse index = client.index(indexRequest, defaultRequestOptions);
    log.info("testCreateIndex {}", index);
  }

  @Test
  public void testBulkRequest() throws IOException {
    ExcelInfoEntity infoEntity = new ExcelInfoEntity();
    infoEntity.setId(12132121321321321L);
    infoEntity.setIccid("123456");
    infoEntity.setPhone("13654789658");
    infoEntity.setReceiver("tom");
    infoEntity.setPlatform("xiaomi");
    infoEntity.setAddress("beijngshi haidianqu");
    infoEntity.setOperators("dianxin");
    infoEntity.setCreateTime(LocalDateTime.now());
    infoEntity.setUpdateTime(LocalDateTime.now());
    IndexRequest indexRequest = new IndexRequest("excel_info_service", "excel_record");
    indexRequest.source(objectMapper.writeValueAsString(infoEntity), XContentType.JSON);
    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(indexRequest);
    BulkResponse bulk = client.bulk(bulkRequest, defaultRequestOptions);
    log.info("testInsertExcelInfo {}", bulk);
  }

  @Test
  public void testBulkRequest2() throws Exception {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setReceiver("taohua");
    orderEntity.setPhone("13659878265");
    orderEntity.setAddress("广西壮族自治区桂林市大厦国门");
    orderEntity.setCreateTime(LocalDateTime.now());
    orderEntity.setUpdateTime(LocalDateTime.now());
    IndexRequest indexRequest = new IndexRequest("order_info_service", "order_record");
    indexRequest.source(objectMapper.writeValueAsString(orderEntity), XContentType.JSON);

    OrderEntity orderEntity2 = new OrderEntity();
    orderEntity2.setReceiver("tom");
    orderEntity2.setPhone("13963257412");
    orderEntity2.setAddress("黑龙江黑河赵各庄");
    orderEntity2.setCreateTime(LocalDateTime.now());
    orderEntity2.setUpdateTime(LocalDateTime.now());
    IndexRequest indexRequest2 = new IndexRequest("order_info", "order");
    indexRequest2.source(objectMapper.writeValueAsString(orderEntity2), XContentType.JSON);
    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(indexRequest);
    bulkRequest.add(indexRequest2);

    BulkResponse bulk = client.bulk(bulkRequest, defaultRequestOptions);
    log.info("testCreateOrder {}", objectMapper.writeValueAsString(bulk));
  }

  /**
   * matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
   * <p>
   * termQuery：不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到。
   */

  @Test
  public void testSearchRequest() throws IOException {
    SearchRequest searchRequest = new SearchRequest("order_info");
    SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
    searchBuilder.query(QueryBuilders.matchQuery("phone", "13654789658"));
    searchRequest.source(searchBuilder);
    SearchResponse search = client.search(searchRequest, defaultRequestOptions);
    log.info("testCriteriaQuery {} ", search);
  }

  @Test
  public void testSearchRequest2() throws IOException {
    SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
    searchBuilder.query(QueryBuilders.termQuery("platform", "xiaomi"));
    SearchRequest searchRequest = new SearchRequest(new String[] {"order_info"}, searchBuilder);
    SearchResponse search = client.search(searchRequest, defaultRequestOptions);
    log.info("search excel info testStringQuery :: {} ", objectMapper.writeValueAsString(search));
  }

  @Test
  public void testQueryPoetry() throws IOException {
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.matchQuery("poetry_author", "李白"));
    SearchRequest searchRequest = new SearchRequest(new String[] {"shi_ci_ming_ju"}, sourceBuilder);
    SearchResponse search = client.search(searchRequest, defaultRequestOptions);
    log.info("testQueryPoetry {}", objectMapper.writeValueAsString(search));
  }

  @Test
  public void testBoolQuery() throws IOException {
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
        .must(QueryBuilders.matchQuery("poetry_author", "李白"))
        .must(QueryBuilders.matchQuery("poetry_num", 117));
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(boolQueryBuilder);
    SearchRequest searchRequest = new SearchRequest(new String[] {"shi_ci_ming_ju"}, sourceBuilder);
    SearchResponse search = client.search(searchRequest, defaultRequestOptions);
    log.info("testBoolQuery {}", objectMapper.writeValueAsString(search));
  }
}
