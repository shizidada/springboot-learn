package org.excel.operator.web.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.poi.ExcelOperator;
import org.excel.operator.web.service.ExcelExportService;
import org.excel.operator.web.service.model.ImportExcelModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/12/9 22:56
 * @see org.excel.operator.web.service.impl
 */

@Service
@Slf4j
public class ExcelExportServiceImpl implements ExcelExportService {

  private static final String EXCEL_SAME = "same";

  private static final String EXCEL_DIFF = "diff";

  @Resource
  private ExcelInfoServiceImpl excelInfoService;

  @Override
  public void downLoadExportExcelFile(HttpServletResponse response, String type) {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    List<ImportExcelModel> exportList = new ArrayList<>();
    try {
      String fileName = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8");
      response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

      if (EXCEL_SAME.equals(type)) {
        exportList = excelInfoService.exportSameReceiverAndPhoneAndAddress();
      } else if (EXCEL_DIFF.equals(type)) {
        exportList = excelInfoService.exportDiffReceiverAndPhoneAndAddress();
      }
      ServletOutputStream outputStream = response.getOutputStream();

      ExcelOperator excelOperator = new ExcelOperator();
      excelOperator.exportExcelFile(exportList, outputStream);
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new BusinessException(ResultCode.EXCEL_EXPORT_FAIL.getMessage(),
          ResultCode.EXCEL_EXPORT_FAIL.getCode());
    }
  }
}
