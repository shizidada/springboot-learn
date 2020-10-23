package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.R;
import org.moose.operator.model.vo.DynamicRecordVO;
import org.moose.operator.model.vo.SearchVO;
import org.moose.operator.web.service.DynamicRecordService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public R<Object> create(@Valid DynamicRecordVO dynamicRecordVO, BindingResult result) {
    return R.ok(dynamicRecordService.saveDynamicRecord(dynamicRecordVO), "创建动态");
  }

  @GetMapping("/recommend/list")
  public R<Object> recommendList(SearchVO searchVO) {
    return R.ok(dynamicRecordService.getRecommendDynamicRecord(searchVO), "获取推荐动态列表");
  }

  @GetMapping("/my/list")
  public R<Object> my(SearchVO searchVO) {
    return R.ok(dynamicRecordService.getMyDynamicRecord(searchVO), "获取我的动态列表");
  }

  @GetMapping("/detail/{dynamicId}")
  public R<Object> detailDynamic(@PathVariable("dynamicId") String dynamicId) {
    return R.ok(dynamicRecordService.getDetailDynamicRecord(dynamicId), "获取动态详情");
  }
}
