package org.learn.controller;

import com.alibaba.excel.EasyExcel;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.learn.common.api.AjaxResult;
import org.learn.entity.ImportExcelInfoDO;
import org.learn.excel.ExcelInfo;
import org.learn.excel.UploadDataListener;
import org.learn.service.ImportExcelFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImportExcelFileController {
  private static final Logger logger = LoggerFactory.getLogger(ImportExcelFileController.class);

  @Resource ImportExcelFileService importExcelFileService;

  @RequestMapping(value = "/api/v1/excel/upload", method = {RequestMethod.POST})
  public AjaxResult excelUpload(@RequestParam("file") MultipartFile file) throws IOException {
    InputStream inputStream = file.getInputStream();
    EasyExcel.read(inputStream, ExcelInfo.class, new UploadDataListener(importExcelFileService))
        .sheet()
        .doRead();
    return AjaxResult.success("success", null);
  }

  @GetMapping("/api/v1/excel/download/same")
  public void sameDownload(HttpServletResponse response) throws IOException {
    // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    String fileName = URLEncoder.encode("same", "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
    List<ImportExcelInfoDO> importExcelInfoDOS =
        importExcelFileService.selectSameReceiverAndPhoneAndAddress();
    EasyExcel.write(response.getOutputStream()).sheet("Sheet1").doWrite(importExcelInfoDOS);
  }

  @GetMapping("/api/v1/excel/download/diff")
  public void diffDownload(HttpServletResponse response) throws IOException {
    // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    String fileName = URLEncoder.encode("diff", "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
    List<ImportExcelInfoDO> importExcelInfoDOS =
        importExcelFileService.selectDiffReceiverAndPhoneAndAddress();
    EasyExcel.write(response.getOutputStream()).sheet("Sheet1").doWrite(importExcelInfoDOS);
  }
}
