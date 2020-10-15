package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.web.service.DynamicRecordService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/dynamic")
public class DynamicController {

  @Resource
  private DynamicRecordService dynamicRecordService;

  @PostMapping("/create")
  public ResponseResult<Object> create(@Valid DynamicRecordParam dynamicRecordParam,
      BindingResult result) {
    dynamicRecordService.saveDynamicRecord(dynamicRecordParam);
    return new ResponseResult<Object>(Boolean.TRUE, "创建动态");
  }

  @PostMapping("/list")
  public ResponseResult<Object> list() {
    return new ResponseResult<Object>(dynamicRecordService.getDynamicRecordByUserId(), "获取动态列表");
  }
}
