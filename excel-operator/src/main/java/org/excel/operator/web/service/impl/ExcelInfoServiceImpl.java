package org.excel.operator.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.component.SnowflakeIdWorker;
import org.excel.operator.model.domain.ExcelInfoDO;
import org.excel.operator.es.repository.ExcelInfoRepository;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.ExcelInfoMapper;
import org.excel.operator.poi.ExcelOperator;
import org.excel.operator.utils.PageInfoUtil;
import org.excel.operator.web.service.ExcelInfoService;
import org.excel.operator.model.dto.ImportExcelDTO;
import org.excel.operator.model.dto.UploadInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:10
 * @see org.excel.operator.web.service.impl
 */
@Service
@Slf4j
public class ExcelInfoServiceImpl implements ExcelInfoService {
  private static final String SUBFIX_FILE_NAME = ".xlsx";

  private static final Long FILE_SIZE = 1024 * 1024 * 5L;

  @Resource
  private ExcelInfoMapper excelInfoMapper;

  @Resource
  private ExcelInfoRepository excelInfoRepository;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public Map<String, Object> selectAll(ImportExcelDTO importExcelModel) {
    if (importExcelModel.getPageNum() > 0 && importExcelModel.getPageSize() > 0) {
      PageHelper.startPage(importExcelModel.getPageNum(), importExcelModel.getPageSize());
    }
    ExcelInfoDO excelInfoDO = this.convertImportExcelDTO2ImportExcelDO(importExcelModel);
    List<ExcelInfoDO> list = excelInfoMapper.selectAll(excelInfoDO);
    PageInfo<ExcelInfoDO> page = new PageInfo<>(list);
    return PageInfoUtil.getBasePageInfo(page);
  }

  @Override public ImportExcelDTO selectByPrimaryKey(Long id) {
    ExcelInfoDO excelInfoDO = excelInfoMapper.selectByPrimaryKey(id);
    return this.convertModelFromDataObject(excelInfoDO);
  }

  @Override public ImportExcelDTO selectByImportExcel(ImportExcelDTO importExcelModel) {
    ExcelInfoDO excelInfoDO = this.convertImportExcelDTO2ImportExcelDO(importExcelModel);
    excelInfoDO = excelInfoMapper.selectByImportExcel(excelInfoDO);
    return this.convertModelFromDataObject(excelInfoDO);
  }

  @Override public int addImportExcelRecord(ImportExcelDTO importExcelModel) {
    ExcelInfoDO excelInfoDO = this.convertImportExcelDTO2ImportExcelDO(importExcelModel);
    return excelInfoMapper.addImportExcelRecord(excelInfoDO);
  }

  @Override
  public int addBatchImportExcelRecord(MultipartFile file, UploadInfoDTO uploadInfoDTO) {
    /**
     * 判断上传文件
     */
    this.estimateUploadFile(file);

    List<ImportExcelDTO> importExcelModels = null;
    try {
      importExcelModels = this.convertReadExcelToModel(file, uploadInfoDTO);
    } catch (IOException e) {
      log.error("导入 excel 文件失败", e);
      throw new BusinessException(ResultCode.EXCEL_IMPORT_FAIL);
    }

    List<ExcelInfoDO> excelInfoDOList = importExcelModels.stream().map(
        this::convertImportExcelDTO2ImportExcelDO).collect(Collectors.toList());

    int result = excelInfoMapper.addImportExcelRecordBatch(excelInfoDOList);

    // TODO: es 操作
    // Iterable<ImportExcelDoc> excelDocs =
    // excelInfoRepository.saveAll(excelInfoDOList);
    return result;
  }

  @Override public List<ImportExcelDTO> exportSameReceiverAndPhoneAndAddress() {
    List<ExcelInfoDO> excelInfoDOList =
        excelInfoMapper.selectSameReceiverAndPhoneAndAddress();

    return excelInfoDOList.stream().map(excelInfoDO -> {
      ImportExcelDTO importExcelModel = new ImportExcelDTO();
      BeanUtils.copyProperties(excelInfoDO, importExcelModel);
      return importExcelModel;
    }).collect(Collectors.toList());
  }

  @Override public List<ImportExcelDTO> exportDiffReceiverAndPhoneAndAddress() {
    List<ExcelInfoDO> excelInfoDOList =
        excelInfoMapper.selectDiffReceiverAndPhoneAndAddress();
    return excelInfoDOList.stream().map(this::convertModelFromDataObject).collect(Collectors.toList());
  }

  /**
   * 将 ImportExcelDTO 转为 ExcelInfoDO
   *
   * @return ExcelInfoDO
   */
  private ExcelInfoDO convertImportExcelDTO2ImportExcelDO(ImportExcelDTO importExcelModel) {
    if (importExcelModel == null) {
      return null;
    }
    ExcelInfoDO excelInfoDO = new ExcelInfoDO();
    BeanUtils.copyProperties(importExcelModel, excelInfoDO);
    return excelInfoDO;
  }

  /**
   * 将 ExcelInfoDO 转为 ImportExcelDTO
   *
   * @return ImportExcelDTO
   */
  private ImportExcelDTO convertModelFromDataObject(ExcelInfoDO excelInfoDO) {
    ImportExcelDTO importExcelModel = new ImportExcelDTO();
    BeanUtils.copyProperties(excelInfoDO, importExcelModel);
    return importExcelModel;
  }

  /**
   * 判断上传的文件
   *
   * @param file 文件
   */
  private void estimateUploadFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY);
    }
    String fileName = file.getOriginalFilename();
    if (fileName != null && !fileName.endsWith(SUBFIX_FILE_NAME)) {
      throw new BusinessException(ResultCode.FILE_NOT_SUPPORT);
    }
    if (file.getSize() > FILE_SIZE) {
      throw new BusinessException(ResultCode.FILE_MUCH);
    }
  }

  /**
   * 转换 model from excel file
   *
   * @throws IOException
   */
  private List<ImportExcelDTO> convertReadExcelToModel(MultipartFile file,
      UploadInfoDTO uploadInfoDTO)
      throws IOException {

    ExcelOperator excelOperator = new ExcelOperator();
    excelOperator.setSnowflakeIdWorker(snowflakeIdWorker);

    return excelOperator.importExcelFile(file.getInputStream(), uploadInfoDTO.getPlatform());
  }
}
