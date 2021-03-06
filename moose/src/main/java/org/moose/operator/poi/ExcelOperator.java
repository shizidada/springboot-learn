package org.moose.operator.poi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.moose.operator.model.dto.ImportExcelInfoDTO;
import org.moose.operator.util.SnowflakeIdWorker;

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
public class ExcelOperator {
  //  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private SnowflakeIdWorker snowflakeIdWorker;

  public void setSnowflakeIdWorker(SnowflakeIdWorker snowflakeIdWorker) {
    this.snowflakeIdWorker = snowflakeIdWorker;
  }

  public ExcelOperator() {
  }

  /**
   * 读取 excel 数据
   */
  public List<ImportExcelInfoDTO> importExcelFile(InputStream inputStream, String platform) {

    XSSFWorkbook workbook = null;
    try {
      // 创建 XSSFWorkbook 操作 xlsx xls ==> HSSFWorkbook
      workbook = new XSSFWorkbook(inputStream);

      // 获取第 0 个 Sheet
      XSSFSheet sheet = workbook.getSheetAt(0);

      List<ImportExcelInfoDTO> importExcelInfoList = new ArrayList<>();

      for (Row row : sheet) {
        // header
        if (row.getRowNum() == 0) {
          continue;
        }

        ImportExcelInfoDTO importExcelInfoDTO = new ImportExcelInfoDTO();

        Cell iccIdCell = row.getCell(0);
        Cell operatorsCell = row.getCell(1);
        Cell receiverCell = row.getCell(2);
        Cell phoneCell = row.getCell(3);
        Cell addressCell = row.getCell(4);

        // read excel cell will be null
        if (iccIdCell == null
            && operatorsCell == null
            && receiverCell == null
            && phoneCell == null
            && addressCell == null) {
          log.info("read excel cell null row num {} ", row.getRowNum());
          continue;
        }

        if (iccIdCell != null) {
          // SIM卡卡号
          iccIdCell.setCellType(CellType.STRING);
          String iccId = iccIdCell.getStringCellValue();
          importExcelInfoDTO.setIccid(iccId);
        }
        if (operatorsCell != null) {
          // 运营商
          operatorsCell.setCellType(CellType.STRING);
          String operators = operatorsCell.getStringCellValue();
          importExcelInfoDTO.setOperators(operators);
        }

        if (receiverCell != null) {
          // 收货人
          String receiver = receiverCell.getStringCellValue();
          importExcelInfoDTO.setReceiver(receiver);
        }

        if (phoneCell != null) {
          // 收货手机号
          phoneCell.setCellType(CellType.STRING);
          String phone = phoneCell.getStringCellValue();
          importExcelInfoDTO.setPhone(phone);
        }
        if (addressCell != null) {
          // 收货地址
          addressCell.setCellType(CellType.STRING);
          String address = addressCell.getStringCellValue();
          importExcelInfoDTO.setAddress(address);
        }

        /**
         * 设置 ID
         */
        importExcelInfoDTO.setId(snowflakeIdWorker.nextId());

        /**
         * 设置平台
         */
        importExcelInfoDTO.setPlatform(platform);

        //importExcelDO.setCreateTime(dateFormat.format(new Date()));
        importExcelInfoDTO.setCreateTime(LocalDateTime.now());

        //importExcelDO.setUpdateTime(dateFormat.format(new Date()));
        importExcelInfoDTO.setUpdateTime(LocalDateTime.now());

        // add to list, need to optimize list add any more data
        importExcelInfoList.add(importExcelInfoDTO);
      }
      return importExcelInfoList;
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    } finally {
      this.closeWorkbook(workbook);
    }
    return null;
  }

  /**
   * 写出 excel
   */
  public void exportExcelFile(List<ImportExcelInfoDTO> importExcelInfoDTOList, OutputStream outputStream) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    // 设置 XSSFCellStyle 样式
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

    for (ImportExcelInfoDTO importExcelInfoDTO : importExcelInfoDTOList) {
      // 填充内容
      int lastRowNum = sheet.getLastRowNum();
      XSSFRow dataRow = sheet.createRow(lastRowNum + 1);
      dataRow.createCell(0).setCellValue(importExcelInfoDTO.getIccid());
      dataRow.createCell(1).setCellValue(importExcelInfoDTO.getOperators());
      dataRow.createCell(2).setCellValue(importExcelInfoDTO.getReceiver());
      dataRow.createCell(3).setCellValue(importExcelInfoDTO.getPhone());
      dataRow.createCell(4).setCellValue(importExcelInfoDTO.getAddress());
    }
    try {
      workbook.write(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("workbook 写出失败。");
    } finally {
      this.closeWorkbook(workbook);
    }
  }

  /**
   * 关闭资源
   *
   * @param workbook XSSFWorkbook
   */
  private void closeWorkbook(XSSFWorkbook workbook) {
    if (workbook != null) {
      try {
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
        log.error("workbook 写出失败。");
      }
    }
  }
}
