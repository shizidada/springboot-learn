package org.moose.operator.mongo.service;

import java.util.List;
import org.moose.operator.mongo.entity.User;

/**
 * @author taohua
 */
public interface UserService {

  /**
   * mongo 数据库获取 User
   *
   * @param userId user id
   * @return User
   */
  User getUser(String userId);

  /**
   * 获取 User 对应 Product
   *
   * @return User 对应 Product
   */
  Object getUserProducts();

  /**
   * 获取全部 User
   *
   * @return 全部 User
   */
  List<User> getAllUsers();
}
