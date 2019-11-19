package org.excel.operator.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.poi.XSSFOperator;
import org.excel.operator.service.impl.ImportExcelServiceImpl;
import org.excel.operator.service.model.ImportExcelModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/excel")
public class ImportExcelController {

  private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);

  private static final String SUBFIX_FILE_NAME = ".xlsx";

  private static final Long FILE_SIZE = 1024 * 1024 * 5L;

  @Resource
  private ImportExcelServiceImpl importExcelService;

  /**
   * @param file 上传的文件
   */
  @PostMapping(value = "/import")
  public ResponseResult importFile(@RequestParam(value = "file") MultipartFile file) {
    String fileName = file.getOriginalFilename();
    if (!fileName.endsWith(SUBFIX_FILE_NAME)) {
      return ResponseResult.fail("该上传不支持，请重新上传。");
    }
    if (file.getSize() > FILE_SIZE) {
      return ResponseResult.fail("该上传太大，请重新上传。");
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
      return ResponseResult.fail(e);
    }
  }
}
