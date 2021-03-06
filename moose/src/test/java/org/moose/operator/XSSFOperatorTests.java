package org.moose.operator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.moose.operator.model.domain.ExcelInfoDO;
import org.moose.operator.mapper.ExcelInfoMapper;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 12:33
 * @see org.moose.operator.poi
 */
@Slf4j
public class XSSFOperatorTests {

  private SqlSession sqlSession;

  private ExcelInfoMapper importExcelMapper;

  public static void main(String[] args) {
    //String filePath = ExcelOperator.class.getResource("/").getPath() + "/excel/tianpeng.xlsx";
    //ExcelOperator xssfOperator = new ExcelOperator();
    //FileInputStream inputStream = new FileInputStream(filePath);
    //xssfOperator.importExcelFile(filePath);
    //xssfOperator.exportExcelFile();
  }

  private void init() {
    InputStream inputStream = null;
    importExcelMapper = sqlSession.getMapper(ExcelInfoMapper.class);
    try {
      inputStream = Resources.getResourceAsStream("mybatis-config.xml");
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      sqlSession = sqlSessionFactory.openSession();
    } catch (IOException e) {
      e.printStackTrace();
      log.error(e.getMessage());
    } finally {
      sqlSession.close();

      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
          log.error(e.getMessage());
        }
      }
    }
  }

  public List<ExcelInfoDO> importExcelFile(FileInputStream inputStream) {
    try {
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

      XSSFSheet sheet = workbook.getSheetAt(0);

      List<ExcelInfoDO> importExcelInfoList = new ArrayList<>();

      for (Row row : sheet) {
        // header
        if (row.getRowNum() == 0) {
          continue;
        }
        String iccId = row.getCell(0).getStringCellValue();
        String operators = row.getCell(1).getStringCellValue();
        String receiver = row.getCell(2).getStringCellValue();
        String phone = row.getCell(3).getStringCellValue();
        String address = row.getCell(4).getStringCellValue();

        ExcelInfoDO importExcelDO = new ExcelInfoDO();
        importExcelDO.setIccid(iccId);
        importExcelDO.setOperators(operators);
        importExcelDO.setReceiver(receiver);
        importExcelDO.setPhone(phone);
        importExcelDO.setAddress(address);

        //importExcelDO.setCreateTime(dateFormat.format(new Date()));
        importExcelDO.setCreateTime(LocalDateTime.now());

        //importExcelDO.setUpdateTime(dateFormat.format(new Date()));
        importExcelDO.setUpdateTime(LocalDateTime.now());

        importExcelInfoList.add(importExcelDO);
      }

      return importExcelInfoList;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void exportExcelFile(List<ExcelInfoDO> exportDiffList) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    // 创建表头
    XSSFSheet sheet = workbook.createSheet();
    XSSFRow titleRow = sheet.createRow(0);
    titleRow.createCell(0).setCellValue("iccid");
    titleRow.createCell(1).setCellValue("运营商");
    titleRow.createCell(2).setCellValue("收货人");
    titleRow.createCell(3).setCellValue("收货手机号");
    titleRow.createCell(4).setCellValue("收货地址");

    //List<ExcelInfoDO> exportDiffList = importExcelMapper.selectDiffReceiverAndPhoneAndAddress();
    //List<ExcelInfoDO> exportDiffList = importExcelMapper.selectSameReceiverAndPhoneAndAddress();
    for (ExcelInfoDO excelInfo : exportDiffList) {
      // 填充内容
      int lastRowNum = sheet.getLastRowNum();
      XSSFRow dataRow = sheet.createRow(lastRowNum + 1);
      dataRow.createCell(0).setCellValue(excelInfo.getIccid());
      dataRow.createCell(1).setCellValue(excelInfo.getOperators());
      dataRow.createCell(2).setCellValue(excelInfo.getReceiver());
      dataRow.createCell(3).setCellValue(excelInfo.getPhone());
      dataRow.createCell(4).setCellValue(excelInfo.getAddress());
    }
    try {
      FileOutputStream fileOutputStream = new FileOutputStream("D:\\upload\\相同.xlsx");
      workbook.write(fileOutputStream);
      workbook.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }
  }
}
