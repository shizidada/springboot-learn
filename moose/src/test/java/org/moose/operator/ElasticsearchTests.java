package org.moose.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.es.entity.ExcelInfoEntity;
import org.moose.operator.es.entity.OrderEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author taohua
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {

  @Resource
  private ElasticsearchTemplate elasticsearchTemplate;

  @Resource
  private ObjectMapper objectMapper;

  @Test
  public void testCreateIndex() {
    // -Des.set.netty.runtime.available.processors=false
    //System.setProperty("es.set.netty.runtime.available.processors", "false");
    //boolean index = elasticsearchTemplate.createIndex(ExcelInfoEntity.class);
    //boolean mapping = elasticsearchTemplate.putMapping(ExcelInfoEntity.class);
    //log.info("create index :: {} :: {} ", index, mapping);
    boolean index = elasticsearchTemplate.createIndex(OrderEntity.class);
    boolean mapping = elasticsearchTemplate.putMapping(OrderEntity.class);
    log.info("create index :: {} :: {} ", index, mapping);
  }

  @Test
  public void getMapping() {
    Map<String, Object> mapping = elasticsearchTemplate.getMapping(ExcelInfoEntity.class);
    log.info("get mapping :: {}", mapping);
  }

  @Test
  public void testInsertExcelInfo() {

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
    IndexQuery indexQuery = new IndexQuery();
    indexQuery.setObject(infoEntity);

    ArrayList<IndexQuery> queries = new ArrayList<>();
    queries.add(indexQuery);

    elasticsearchTemplate.bulkIndex(queries);
  }

  @Test
  public void testDeleteIndex() {
    boolean deleteExcelInfoIndex = elasticsearchTemplate.deleteIndex(ExcelInfoEntity.class);
    boolean deleteOrderIndex = elasticsearchTemplate.deleteIndex(OrderEntity.class);
    log.info("delete {} {} ", deleteExcelInfoIndex, deleteOrderIndex);

    //this.testCreateIndex();

    //this.testInsertExcelInfo();
  }

  /**
   * matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
   * <p>
   * termQuery：不会对搜索词进行分词处理，而是作为一个整体与目标字段进行匹配，若完全匹配，则可查询到。
   */

  @Test
  public void testCriteriaQuery() {
    Criteria address = Criteria.where("phone").is("13654789658");
    CriteriaQuery criteriaQuery = new CriteriaQuery(address);

    //ExcelInfoEntity infoEntity =
    //    elasticsearchTemplate.queryForObject(criteriaQuery, ExcelInfoEntity.class);

    Page<ExcelInfoEntity> excelInfoEntities =
        elasticsearchTemplate.queryForPage(criteriaQuery, ExcelInfoEntity.class);

    List<ExcelInfoEntity> excelInfoEntityList = excelInfoEntities.getContent();
    log.info("search excel info :: {} ", excelInfoEntities);
    log.info("search excel info excelInfoEntityList :: {} ", excelInfoEntityList);
  }

  @Test
  public void testStringQuery() throws JsonProcessingException {
    String string = QueryBuilders.termQuery("platform", "xiaomi").toString();
    StringQuery stringQuery = new StringQuery(string);
    Page<ExcelInfoEntity> excelInfoEntities =
        elasticsearchTemplate.queryForPage(stringQuery, ExcelInfoEntity.class);
    log.info("search excel info testStringQuery :: {} ",
        objectMapper.writeValueAsString(excelInfoEntities));
  }

  @Test
  public void testCreateOrder() {

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setReceiver("taohua");
    orderEntity.setPhone("13659878265");
    orderEntity.setAddress("广西壮族自治区桂林市大厦国门");
    orderEntity.setCreateTime(LocalDateTime.now());
    orderEntity.setUpdateTime(LocalDateTime.now());
    IndexQuery indexQuery = new IndexQueryBuilder().withObject(orderEntity).build();

    OrderEntity orderEntity2 = new OrderEntity();
    orderEntity2.setReceiver("tom");
    orderEntity2.setPhone("13963257412");
    orderEntity2.setAddress("黑龙江黑河赵各庄");
    orderEntity2.setCreateTime(LocalDateTime.now());
    orderEntity2.setUpdateTime(LocalDateTime.now());
    IndexQuery indexQuery2 = new IndexQueryBuilder().withObject(orderEntity2).build();

    List<IndexQuery> queries = new ArrayList<>();
    queries.add(indexQuery);
    queries.add(indexQuery2);

    elasticsearchTemplate.bulkIndex(queries);
  }
}
