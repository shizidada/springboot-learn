package org.excel.operator.mongo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.excel.operator.mongo.entity.User;
import org.excel.operator.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author taohua
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override public User getUser(String userId) {
    //Query query = new Query();
    //query.addCriteria(Criteria.where("user_id").is(userId));

    Query query = Query.query(Criteria.where("user_id").is(userId));
    return this.mongoTemplate.findOne(query, User.class);
  }

  @Override public Object getUserProducts() {
    List<AggregationOperation> operations = new ArrayList<>();
    LookupOperation productAggregation = LookupOperation.newLookup()
        .from("product")
        .localField("user_id")
        .foreignField("user_id")
        .as("products");

    operations.add(productAggregation);

    AggregationOperation userIdOperation =
        Aggregation.match(Criteria.where("user_id").is("5e0b6d05424ac70ab28b70b6"));
    operations.add(userIdOperation);

    operations.add(Aggregation.project("username", "user_id", "products").andExclude("_id"));

    Aggregation aggregation = Aggregation.newAggregation(operations);

    List<Map> user =
        this.mongoTemplate.aggregate(aggregation, "user", Map.class).getMappedResults();
    return user;
  }

  @Override
  public List<User> getAllUsers() {
    return mongoTemplate.findAll(User.class);
  }
}
