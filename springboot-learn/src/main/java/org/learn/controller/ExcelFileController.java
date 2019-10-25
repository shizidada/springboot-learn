package org.learn.controller;

import com.alibaba.excel.EasyExcel;
import java.io.IOException;
import java.io.InputStream;
import org.learn.common.api.AjaxResult;
import org.learn.excel.ExcelDataListener;
import org.learn.excel.ExcelInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelFileController {

  @RequestMapping(value = "/api/v1/excel/upload", method = {RequestMethod.POST})
  public AjaxResult upload(@RequestParam("file") MultipartFile file) throws IOException {
    InputStream inputStream = file.getInputStream();
    EasyExcel.read(inputStream, ExcelInfo.class, new ExcelDataListener()).sheet().doRead();
    return AjaxResult.success("success", null);
  }
}
