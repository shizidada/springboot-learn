package org.moose.operator.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.R;
import org.moose.operator.model.dto.ExcelUploadInfoDTO;
import org.moose.operator.web.service.impl.ExcelInfoServiceImpl;
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
 * @see org.moose.operator.web.controller
 */
@RestController
@RequestMapping(value = "/api/v1/excel")
@Slf4j
public class ExcelImportController {

  @Resource private ExcelInfoServiceImpl importExcelService;

  @Resource private ObjectMapper objectMapper;

  /**
   * @param excelUploadInfoDTO 上传文件表单
   */
  @PostMapping(value = "/import")
  public R<Integer> importFile(
      @RequestParam(value = "file") MultipartFile file,
      @Valid ExcelUploadInfoDTO excelUploadInfoDTO, BindingResult result) throws Exception {
    log.info(file.getOriginalFilename(), objectMapper.writeValueAsString(excelUploadInfoDTO));
    return R.ok(importExcelService.addBatchImportExcelRecord(file, excelUploadInfoDTO));
  }
}
