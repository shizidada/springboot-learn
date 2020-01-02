package org.excel.operator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.excel.operator.mongo.entity.Product;
import org.excel.operator.mongo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelOperatorApplicationTests {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Test
  public void testInsert() {
    User user = new User();
    user.setUsername("username");
    mongoTemplate.insert(user);
  }

  @Test
  public void testFind() {
    List<User> all = mongoTemplate.findAll(User.class);
    System.out.println(all);
  }

  @Test
  public void insertUser() {
    User user = new User();
    user.setUserId(ObjectId.get().toHexString());
    user.setAvatar("https://avatars1.githubusercontent.com/u/14831261?s=64&v=4");
    user.setUsername("小桃花");
    user.setEmail("xiaotaohua@gmail.com");
    user.setPhone("13611083015");
    user.setDescription("好开心😄");
    user.setGender("0");
    user.setCreateDate(new Date());
    user.setUpdateDate(new Date());
    mongoTemplate.insert(user);
  }

  @Test
  public void deleteUser() {
    List<User> users = mongoTemplate.findAll(User.class);
    log.info("DeleteResult {}", users);
  }

  @Test
  public void testInsertUserAndProduct() {
    Product product = new Product();
    product.setUserId("5e0b6d05424ac70ab28b70b6");
    product.setProductId(ObjectId.get().toHexString());
    product.setProductName("良品铺子 - 波力海苔");
    product.setDescription("良品铺子，高端零食，连续4年高端零食全国销售领先，买好零食，更多人到良品铺子");
    product.setPrice(new BigDecimal("1.89"));
    product.setCreateDate(new Date());
    product.setUpdateDate(new Date());
    mongoTemplate.insert(product);
  }

  @Test
  public void findProduct() {
    List<Product> products = mongoTemplate.findAll(Product.class);
    log.info("{}", products);
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
