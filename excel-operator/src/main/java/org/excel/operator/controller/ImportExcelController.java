package org.excel.operator.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.excel.operator.common.ResponseResult;
import org.excel.operator.entity.ImportExcelDO;
import org.excel.operator.poi.XSSFOperator;
import org.excel.operator.service.impl.ImportExcelServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1/excel")
public class ImportExcelController {

  private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);

  private static final String SUBFIX_FILE_NAME = ".xlsx";

  @Resource
  private ImportExcelServiceImpl importExcelService;

  /**
   * @param file 上传的文件
   */
  @PostMapping(value = "/import")
  public ResponseResult importFile(@RequestParam(value = "file") MultipartFile file) {
    String fileName = file.getOriginalFilename();
    if (!fileName.endsWith(SUBFIX_FILE_NAME)) {
      return new ResponseResult(10000L, false, "the file not support. ", null);
    }
    try {
      XSSFOperator xssfOperator = new XSSFOperator();
      List<ImportExcelDO> importExcelDOList = xssfOperator.importExcelFile(file.getInputStream());
      importExcelService.addImportExcelRecordBatch(importExcelDOList);
      return new ResponseResult(200L, true, "success", null);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseResult(10000L, false, "fail", null);
    }
  }
}
