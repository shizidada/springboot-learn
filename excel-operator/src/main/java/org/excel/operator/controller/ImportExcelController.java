package org.excel.operator.controller;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.poi.XSSFOperator;
import org.excel.operator.service.impl.ImportExcelServiceImpl;
import org.excel.operator.service.model.ImportExcelModel;
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
public class ImportExcelController {

  private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);

  private static final String SUBFIX_FILE_NAME = ".xlsx";

  private static final Long FILE_SIZE = 1024 * 1024 * 5L;

  @Resource
  private ImportExcelServiceImpl importExcelService;

  /**
   * @param uploadInfoModel 上传文件表单
   */
  @PostMapping(value = "/import")
  public ResponseResult importFile(
      @RequestParam(value = "file") MultipartFile file,
      @Valid UploadInfoModel uploadInfoModel, BindingResult result) {
    logger.info(JSON.toJSONString(uploadInfoModel));
    logger.info(file.getOriginalFilename());
    return ResponseResult.success();
  }

  /**
   * @param file 上传的文件
   */
  @PostMapping(value = "/upload")
  public ResponseResult upload(@RequestParam(value = "file") MultipartFile file) {
    if (file.isEmpty()) {
      throw new BusinessException(ResponseCode.FILE_NOT_EMPTY.getMessage(),
          ResponseCode.FILE_NOT_EMPTY.getCode());
    }
    String fileName = file.getOriginalFilename();
    if (!fileName.endsWith(SUBFIX_FILE_NAME)) {
      throw new BusinessException(ResponseCode.FILE_NOT_SUPPORT.getMessage(),
          ResponseCode.FILE_NOT_SUPPORT.getCode());
    }
    if (file.getSize() > FILE_SIZE) {
      throw new BusinessException(ResponseCode.FILE_MUCH.getMessage(),
          ResponseCode.FILE_MUCH.getCode());
    }
    try {
      XSSFOperator xssfOperator = new XSSFOperator();
      List<ImportExcelModel> importExcelModels =
          xssfOperator.importExcelFile(file.getInputStream());
      importExcelService.addImportExcelRecordBatch(importExcelModels);
      return ResponseResult.success();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("导入 excel 文件失败", e);
      throw new BusinessException(ResponseCode.EXCEL_IMPORT_FAIL.getMessage(),
          ResponseCode.EXCEL_IMPORT_FAIL.getCode());
    }
  }
}
