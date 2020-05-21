package org.excel.operator.web.service;

import java.util.List;
import org.excel.operator.mongo.entity.User;

public interface UserInfoService {

  User getUser(String userId);

  Object getUserProducts();

  List<User> getAllUsers();
}
