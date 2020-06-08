package org.excel.operator.web.controller;

import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.web.service.impl.ExcelInfoServiceImpl;
import org.excel.operator.model.dto.UploadInfoDTO;
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
 * @see org.excel.operator.web.controller
 */
@RestController
@RequestMapping(value = "/api/v1/excel")
@Slf4j
public class ExcelImportController {

  @Resource
  private ExcelInfoServiceImpl importExcelService;

  /**
   * @param uploadInfoDTO 上传文件表单
   */
  @PostMapping(value = "/import")
  public ResponseResult<Integer> importFile(
      @RequestParam(value = "file") MultipartFile file,
      @Valid UploadInfoDTO uploadInfoDTO, BindingResult result) {
    log.info(file.getOriginalFilename(), JSON.toJSONString(uploadInfoDTO));
    // 存入数据库
    int size = importExcelService.addBatchImportExcelRecord(file, uploadInfoDTO);
    return new ResponseResult<>(size);
  }
}
