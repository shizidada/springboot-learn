package org.learn.controller;

import com.alibaba.excel.EasyExcel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.learn.common.api.AjaxResult;
import org.learn.entity.ImportExcelInfoDO;
import org.learn.excel.easyexcel.ExcelInfo;
import org.learn.excel.easyexcel.UploadDataListener;
import org.learn.excel.easyexcel.util.ExcelFileUtil;
import org.learn.service.ImportExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EasyExcelImportController {
  private static final Logger logger = LoggerFactory.getLogger(EasyExcelImportController.class);

  @Resource ImportExcelService importExcelService;

  @RequestMapping(value = "/api/v1/easy/excel/upload", method = {RequestMethod.POST})
  public AjaxResult excelUpload(@RequestParam("file") MultipartFile file) {
    InputStream inputStream = null;
    try {
      inputStream = file.getInputStream();
      EasyExcel.read(inputStream, ExcelInfo.class, new UploadDataListener(importExcelService))
          .sheet()
          .doRead();
      return AjaxResult.success("success", null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return AjaxResult.success("fail", null);
  }

  @GetMapping("/api/v1/excel/easy/download/same")
  public void sameDownload(HttpServletResponse response) throws IOException {
    this.writeDownLoadExcelFile(response, "same", "same");
  }

  @GetMapping("/api/v1/excel/easy/download/diff")
  public void diffDownload(HttpServletResponse response) throws IOException {
    this.writeDownLoadExcelFile(response, "diff", "diff");
  }

  private void writeDownLoadExcelFile(HttpServletResponse response, String downloadName,
      String type) {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = null;
    List<ImportExcelInfoDO> importExcelInfoDOS = new ArrayList<>();
    try {
      fileName = URLEncoder.encode(downloadName, "UTF-8");
      response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

      if ("same".equals(type)) {
        importExcelInfoDOS =
            importExcelService.selectSameReceiverAndPhoneAndAddress();
      } else if ("diff".equals(type)) {
        importExcelInfoDOS =
            importExcelService.selectDiffReceiverAndPhoneAndAddress();
      }

      String templateName = ExcelFileUtil.getPath() + "excel" + File.separator + "template2.xlsx";
      EasyExcel.write(response.getOutputStream())
          .withTemplate(templateName)
          .sheet("Sheet0")
          .doWrite(importExcelInfoDOS);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
