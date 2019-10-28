package org.excel.operator.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.excel.operator.entity.ImportExcelDO;
import org.excel.operator.mapper.ImportExcelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 12:33
 * @see org.excel.operator.poi
 */
public class XSSFOperator {

  private static final Logger logger = LoggerFactory.getLogger(XSSFOperator.class);

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private SqlSession sqlSession;

  private ImportExcelMapper importExcelMapper;

  public XSSFOperator() {
    init();
    importExcelMapper = sqlSession.getMapper(ImportExcelMapper.class);
    //  String filePath = XSSFOperator.class.getResource("/").getPath() + "anqin2.xlsx";
  }

  private void init() {
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream("mybatis-config.xml");
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      sqlSession = sqlSessionFactory.openSession();
    } catch (IOException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
          logger.error(e.getMessage());
        }
      }
    }
  }

  public void importExcelFile(String filePath) {

    try {
      // 1、获取文件输入流
      FileInputStream inputStream = new FileInputStream(filePath);

      // 2、获取Excel工作簿对象
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

      XSSFSheet sheet = workbook.getSheetAt(0);

      List<ImportExcelDO> importExcelInfoList = new ArrayList<>();

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

        ImportExcelDO importExcelDO = new ImportExcelDO();
        importExcelDO.setIccid(iccId);
        importExcelDO.setOperators(operators);
        importExcelDO.setReceiver(receiver);
        importExcelDO.setPhone(phone);
        importExcelDO.setAddress(address);

        //importExcelDO.setCreateTime(dateFormat.format(new Date()));
        importExcelDO.setCreateTime(new Date());

        //importExcelDO.setUpdateTime(dateFormat.format(new Date()));
        importExcelDO.setUpdateTime(new Date());

        importExcelInfoList.add(importExcelDO);
      }

      importExcelMapper.addImportExcelInfoBatch(importExcelInfoList);
      sqlSession.commit();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      sqlSession.rollback();
    } catch (IOException e) {
      e.printStackTrace();
      sqlSession.rollback();
    } finally {
      if (sqlSession != null) {
        sqlSession.close();
      }
    }
  }

  public void exportExcelFile() {

    //ImportExcelDO importExcelDO = importExcelMapper.selectByPrimaryKey(14607L);

    //List<ImportExcelDO> importExcelDOList = importExcelMapper.selectDiffReceiverAndPhoneAndAddress();

    //List<ImportExcelDO> importExcelDOList = importExcelMapper.selectSameReceiverAndPhoneAndAddress();

    XSSFWorkbook workbook = new XSSFWorkbook();

    //XSSFCellStyle cellStyle = workbook.createCellStyle();
    //XSSFColor color = new XSSFColor(new java.awt.Color(255, 0, 0));
    //cellStyle.setFillForegroundColor(color);
    //cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    // 创建表头
    XSSFSheet sheet = workbook.createSheet();
    XSSFRow titleRow = sheet.createRow(0);
    titleRow.createCell(0).setCellValue("iccid");
    titleRow.createCell(1).setCellValue("运营商");
    titleRow.createCell(2).setCellValue("收货人");
    titleRow.createCell(3).setCellValue("收货手机号");
    titleRow.createCell(4).setCellValue("收货地址");

    List<ImportExcelDO> exportDiffList = importExcelMapper.selectDiffReceiverAndPhoneAndAddress();
    //List<ImportExcelDO> exportDiffList = importExcelMapper.selectSameReceiverAndPhoneAndAddress();
    for (ImportExcelDO excelInfo : exportDiffList) {
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
      if (sqlSession != null) {
        sqlSession.close();
      }
    }
  }
}
