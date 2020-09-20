package org.moose.operator;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.moose.operator.model.domain.ProductDO;
import org.moose.operator.model.domain.UserInfoDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelOperatorTests {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Test
  public void testInsert() {
    UserInfoDO user = new UserInfoDO();
    user.setAccountName("username");
    mongoTemplate.insert(user);
  }

  @Test
  public void testFindUserById() {
    //User user = mongoTemplate.findById("5e1533374af7df1d503039e4", User.class);
    //User user2 = mongoTemplate.findById("5e1533374af7df1d503039e4", User.class, "user");
    //log.info("user {}", user);
    //log.info("user2 {}", user2);
  }

  @Test
  public void testFind() {
    List<UserInfoDO> all = mongoTemplate.findAll(UserInfoDO.class);
    System.out.println(all);
  }

  @Test
  public void insertUser() {
    UserInfoDO user = new UserInfoDO();
    user.setUserId(ObjectId.get().toHexString());
    user.setAvatar("https://avatars1.githubusercontent.com/u/14831261?s=64&v=4");
    user.setAccountName("小桃花");
    user.setEmail("xiaotaohua@gmail.com");
    user.setPhone("13611083015");
    user.setDescription("好开心😄");
    user.setGender("0");
    user.setCreateTime(LocalDateTime.now());
    user.setUpdateTime(LocalDateTime.now());
    mongoTemplate.insert(user);
  }

  @Test
  public void deleteUser() {
    List<UserInfoDO> users = mongoTemplate.findAll(UserInfoDO.class);
    log.info("DeleteResult {}", users);
  }

  @Test
  public void testInsertUserAndProduct() {
    ProductDO productDO = new ProductDO();
    productDO.setUserId("5e0b6d05424ac70ab28b70b6");
    productDO.setProductId(ObjectId.get().toHexString());
    productDO.setProductName("良品铺子 - 波力海苔");
    productDO.setDescription("良品铺子，高端零食，连续4年高端零食全国销售领先，买好零食，更多人到良品铺子");
    productDO.setPrice(new BigDecimal("1.89"));
    productDO.setCreateDate(new Date());
    productDO.setUpdateDate(new Date());
    mongoTemplate.insert(productDO);
  }

  @Test
  public void findProduct() {
    List<ProductDO> productDOS = mongoTemplate.findAll(ProductDO.class);
    log.info("{}", productDOS);
  }

  @Test
  public void findAggregation() {
    List<AggregationOperation> operations = new ArrayList<>();

    LookupOperation product_aggregation = LookupOperation.newLookup()
        .from("product")
        .localField("userId")
        .foreignField("userId")
        .as("products");

    operations.add(product_aggregation);

    Aggregation aggregation = Aggregation.newAggregation(operations);
    List<Map> user = mongoTemplate.aggregate(aggregation, "user", Map.class).getMappedResults();
    log.info("user {}", user);
  }
}
