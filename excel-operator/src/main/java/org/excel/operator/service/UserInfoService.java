package org.excel.operator.service;

import org.excel.operator.mongo.entity.User;

public interface UserInfoService {

  User getUser(String userId);

  Object getUserProducts();

  Object getAllUsers();
}
