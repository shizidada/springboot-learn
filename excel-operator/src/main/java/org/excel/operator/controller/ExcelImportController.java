package org.excel.operator.controller;

import com.alibaba.fastjson.JSON;
import org.excel.operator.common.api.ResponseResult;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.excel.operator.service.impl.ExcelInfoServiceImpl;
import org.excel.operator.service.model.UploadInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:06
 * @see org.excel.operator.controller
 */
@RestController
@RequestMapping(value = "/api/v1/excel")
public class ExcelImportController {

  private static final Logger logger = LoggerFactory.getLogger(ExcelImportController.class);

  @Resource
  private ExcelInfoServiceImpl importExcelService;

  /**
   * @param uploadInfoModel 上传文件表单
   */
  @PostMapping(value = "/import")
  public ResponseResult importFile(
      @RequestParam(value = "file") MultipartFile file,
      @Valid UploadInfoModel uploadInfoModel, BindingResult result) {
    logger.info(file.getOriginalFilename(), JSON.toJSONString(uploadInfoModel));
    // 存入数据库
    int size = importExcelService.addBatchImportExcelRecord(file, uploadInfoModel);
    return ResponseResult.success(size);
  }
}
