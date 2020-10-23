package org.moose.operator.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.repository.ExcelInfoRepository;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.ExcelInfoMapper;
import org.moose.operator.model.domain.ExcelInfoDO;
import org.moose.operator.model.dto.ImportExcelInfoDTO;
import org.moose.operator.model.dto.ExcelUploadInfoDTO;
import org.moose.operator.poi.ExcelOperator;
import org.moose.operator.util.PageInfoUtils;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.service.ExcelInfoService;
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
 * @see org.moose.operator.web.service.impl
 */
@Service
@Slf4j
public class ExcelInfoServiceImpl implements ExcelInfoService {
  private static final String SUFFIX_FILE_NAME = ".xlsx";

  private static final Long FILE_SIZE = 1024 * 1024 * 5L;

  @Resource
  private ExcelInfoMapper excelInfoMapper;

  @Resource
  private ExcelInfoRepository excelInfoRepository;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public Map<String, Object> selectAll(ImportExcelInfoDTO importExcelInfoDTO) {
    if (importExcelInfoDTO.getPageNum() > 0 && importExcelInfoDTO.getPageSize() > 0) {
      PageHelper.startPage(importExcelInfoDTO.getPageNum(), importExcelInfoDTO.getPageSize());
    }
    ExcelInfoDO excelInfoDO = this.convertImportExcelDTO2ImportExcelDO(importExcelInfoDTO);
    List<ExcelInfoDO> list = excelInfoMapper.selectAll(excelInfoDO);
    PageInfo<ExcelInfoDO> page = new PageInfo<>(list);
    return PageInfoUtils.getBasePageInfo(page);
  }

  @Override public ImportExcelInfoDTO selectByPrimaryKey(Long id) {
    ExcelInfoDO excelInfoDO = excelInfoMapper.selectByPrimaryKey(id);
    return this.convertDTOFromDataObject(excelInfoDO);
  }

  @Override public ImportExcelInfoDTO selectByImportExcel(ImportExcelInfoDTO importExcelInfoDTO) {
    ExcelInfoDO excelInfoDO = this.convertImportExcelDTO2ImportExcelDO(importExcelInfoDTO);
    excelInfoDO = excelInfoMapper.selectByImportExcel(excelInfoDO);
    return this.convertDTOFromDataObject(excelInfoDO);
  }

  @Override public int addImportExcelRecord(ImportExcelInfoDTO importExcelInfoDTO) {
    ExcelInfoDO excelInfoDO = this.convertImportExcelDTO2ImportExcelDO(importExcelInfoDTO);
    return excelInfoMapper.addImportExcelRecord(excelInfoDO);
  }

  @Override
  public int addBatchImportExcelRecord(MultipartFile file, ExcelUploadInfoDTO excelUploadInfoDTO) {
    /**
     * 判断上传文件
     */
    this.checkUploadFile(file);

    List<ImportExcelInfoDTO> importExcelList = null;
    try {
      importExcelList = this.convertReadExcelToDTO(file, excelUploadInfoDTO);
    } catch (IOException e) {
      log.error("导入 excel 文件失败", e);
      throw new BusinessException(ResultCode.EXCEL_IMPORT_FAIL);
    }

    List<ExcelInfoDO> excelInfoDOList = importExcelList.stream().map(
        this::convertImportExcelDTO2ImportExcelDO).collect(Collectors.toList());

    int result = excelInfoMapper.addImportExcelRecordBatch(excelInfoDOList);

    // TODO: es 操作, 数据库数同步到 es
    // Iterable<ImportExcelDoc> excelDocs =
    // excelInfoRepository.saveAll(excelInfoDOList);
    return result;
  }

  @Override public List<ImportExcelInfoDTO> exportSameReceiverAndPhoneAndAddress() {
    List<ExcelInfoDO> excelInfoDOList =
        excelInfoMapper.selectSameReceiverAndPhoneAndAddress();

    return excelInfoDOList.stream().map(excelInfoDO -> {
      ImportExcelInfoDTO importExcelInfoDTO = new ImportExcelInfoDTO();
      BeanUtils.copyProperties(excelInfoDO, importExcelInfoDTO);
      return importExcelInfoDTO;
    }).collect(Collectors.toList());
  }

  @Override public List<ImportExcelInfoDTO> exportDiffReceiverAndPhoneAndAddress() {
    List<ExcelInfoDO> excelInfoDOList =
        excelInfoMapper.selectDiffReceiverAndPhoneAndAddress();
    return excelInfoDOList.stream()
        .map(this::convertDTOFromDataObject)
        .collect(Collectors.toList());
  }

  /**
   * 将 ImportExcelDTO 转为 ExcelInfoDO
   *
   * @return ExcelInfoDO
   */
  private ExcelInfoDO convertImportExcelDTO2ImportExcelDO(ImportExcelInfoDTO importExcelInfoDTO) {
    if (importExcelInfoDTO == null) {
      return null;
    }
    ExcelInfoDO excelInfoDO = new ExcelInfoDO();
    BeanUtils.copyProperties(importExcelInfoDTO, excelInfoDO);
    return excelInfoDO;
  }

  /**
   * 将 ExcelInfoDO 转为 ImportExcelDTO
   *
   * @return ImportExcelDTO
   */
  private ImportExcelInfoDTO convertDTOFromDataObject(ExcelInfoDO excelInfoDO) {
    ImportExcelInfoDTO importExcelInfoDTO = new ImportExcelInfoDTO();
    BeanUtils.copyProperties(excelInfoDO, importExcelInfoDTO);
    return importExcelInfoDTO;
  }

  /**
   * 判断上传的文件
   *
   * @param file 文件
   */
  private void checkUploadFile(MultipartFile file) {
    if (ObjectUtils.isEmpty(file)) {
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY);
    }
    String fileName = file.getOriginalFilename();
    if (fileName != null && !fileName.endsWith(SUFFIX_FILE_NAME)) {
      throw new BusinessException(ResultCode.FILE_NOT_SUPPORT);
    }
    if (file.getSize() > FILE_SIZE) {
      throw new BusinessException(ResultCode.FILE_TOO_LARGE);
    }
  }

  /**
   * 转换 model from excel file
   *
   * @throws IOException
   */
  private List<ImportExcelInfoDTO> convertReadExcelToDTO(MultipartFile file,
      ExcelUploadInfoDTO excelUploadInfoDTO)
      throws IOException {

    ExcelOperator excelOperator = new ExcelOperator();
    excelOperator.setSnowflakeIdWorker(snowflakeIdWorker);

    return excelOperator.importExcelFile(file.getInputStream(), excelUploadInfoDTO.getPlatform());
  }
}
