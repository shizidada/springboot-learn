package org.moose.operator.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.BeanUtils;
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
public class UserInfoServiceImpl implements UserInfoService {

  @Resource
  private MongoTemplate mongoTemplate;

  @Override
  public Boolean saveUserInfo(UserInfoDO userInfoDO) {
    UserInfoDO saveUser = mongoTemplate.save(userInfoDO);
    return ObjectUtils.isNotEmpty(saveUser);
  }

  @Override
  public UserInfoDTO getUserInfo(Long accountId, String accountName) {
    Query query =
        Query.query(Criteria.where("account_id").is(accountId).and("account_name").is(accountName));
    UserInfoDO userInfoDO = this.mongoTemplate.findOne(query, UserInfoDO.class);
    if (ObjectUtils.isEmpty(userInfoDO)) {
      return null;
    }
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    BeanUtils.copyProperties(userInfoDO, userInfoDTO);
    return userInfoDTO;
  }

  @Override
  public UserInfoDTO getUserByUserId(String userId) {
    Query query = Query.query(Criteria.where("user_id").is(userId));
    UserInfoDO userInfoDO = this.mongoTemplate.findOne(query, UserInfoDO.class);
    if (ObjectUtils.isEmpty(userInfoDO)) {
      return null;
    }
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    BeanUtils.copyProperties(userInfoDO, userInfoDTO);
    return userInfoDTO;
  }

  @Override
  public Object getUserProducts() {
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
}
