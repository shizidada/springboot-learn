package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.R;
import org.moose.operator.model.params.DynamicRecordParam;
import org.moose.operator.model.params.SearchParam;
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
  public R<Object> create(@Valid DynamicRecordParam dynamicRecordParam, BindingResult result) {
    return R.ok(dynamicRecordService.saveDynamicRecord(dynamicRecordParam), "创建动态");
  }

  @PostMapping("/getRecommendList")
  public R<Object> recommendList(SearchParam searchParam) {
    return R.ok(dynamicRecordService.getRecommendDynamicRecordByStep(searchParam), "获取推荐动态列表");
  }

  @PostMapping("/my")
  public R<Object> my() {
    return R.ok(dynamicRecordService.getMyDynamicRecord(), "获取我的动态列表");
  }
}
