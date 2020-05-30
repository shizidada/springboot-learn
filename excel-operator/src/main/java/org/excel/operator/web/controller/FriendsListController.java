package org.excel.operator.web.controller;

import java.util.List;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.mongo.entity.User;
import org.excel.operator.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@RestController
@RequestMapping("/friends")
public class FriendsListController {

  @Autowired
  private UserInfoService userInfoService;

  @GetMapping("/list")
  public ResponseResult<List<User>> getFriendsList() {
    List<User> allUsers = userInfoService.getAllUsers();
    return new ResponseResult<>(allUsers);
  }
}
