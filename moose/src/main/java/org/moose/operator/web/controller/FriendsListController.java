package org.moose.operator.web.controller;

import java.util.List;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.mongo.entity.User;
import org.moose.operator.mongo.service.UserService;
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
  private UserService userService;

  @GetMapping("/list")
  public ResponseResult<List<User>> getFriendsList() {
    return new ResponseResult<>(userService.getAllUsers());
  }
}
