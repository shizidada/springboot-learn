package org.excel.operator.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.excel.operator.common.api.ResultCode;
import org.excel.operator.component.SnowflakeIdWorker;
import org.excel.operator.entity.ExcelInfoDO;
import org.excel.operator.es.repository.ExcelInfoRepository;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.ExcelInfoMapper;
import org.excel.operator.poi.ExcelOperator;
import org.excel.operator.service.ExcelInfoService;
import org.excel.operator.service.model.ImportExcelModel;
import org.excel.operator.service.model.UploadInfoModel;
import org.excel.operator.util.PageInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:10
 * @see org.excel.operator.service.impl
 */
@Service
public class ExcelInfoServiceImpl implements ExcelInfoService {
  private static final Logger logger = LoggerFactory.getLogger(ExcelInfoServiceImpl.class);

  private static final String SUBFIX_FILE_NAME = ".xlsx";

  private static final Long FILE_SIZE = 1024 * 1024 * 5L;

  @Resource
  private ExcelInfoMapper excelInfoMapper;

  @Resource
  private ExcelInfoRepository excelInfoRepository;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public Map<String, Object> selectAll(ImportExcelModel importExcelModel) {
    if (importExcelModel.getPageNum() > 0 && importExcelModel.getPageSize() > 0) {
      PageHelper.startPage(importExcelModel.getPageNum(), importExcelModel.getPageSize());
    }
    ExcelInfoDO excelInfoDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
    List<ExcelInfoDO> list = excelInfoMapper.selectAll(excelInfoDO);
    PageInfo page = new PageInfo<>(list);
    Map<String, Object> basePageInfo = PageInfoUtils.getBasePageInfo(page);
    return basePageInfo;
  }

  @Override public ImportExcelModel selectByPrimaryKey(Long id) {
    ExcelInfoDO excelInfoDO = excelInfoMapper.selectByPrimaryKey(id);
    ImportExcelModel importExcelModel = this.convertModelFromDataObject(excelInfoDO);
    return importExcelModel;
  }

  @Override public ImportExcelModel selectByImportExcel(ImportExcelModel importExcelModel) {
    ExcelInfoDO excelInfoDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
    excelInfoDO = excelInfoMapper.selectByImportExcel(excelInfoDO);
    ImportExcelModel excelModel = this.convertModelFromDataObject(excelInfoDO);
    return excelModel;
  }

  @Override public int addImportExcelRecord(ImportExcelModel importExcelModel) {
    ExcelInfoDO excelInfoDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
    return excelInfoMapper.addImportExcelRecord(excelInfoDO);
  }

  @Override public int addBatchImportExcelRecord(MultipartFile file, UploadInfoModel uploadInfoModel) {
    /**
     * 判断上传文件
     */
    this.estimateUploadFile(file);

    List<ImportExcelModel> importExcelModels = null;
    try {
      importExcelModels = this.convertReadExcelToModel(file, uploadInfoModel);
    } catch (IOException e) {
      logger.error("导入 excel 文件失败", e);
      throw new BusinessException(ResultCode.EXCEL_IMPORT_FAIL.getMessage(),
          ResultCode.EXCEL_IMPORT_FAIL.getCode());
    }

    List<ExcelInfoDO> excelInfoDOList = importExcelModels.stream().map(importExcelModel -> {
      ExcelInfoDO excelInfoDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
      return excelInfoDO;
    }).collect(Collectors.toList());

    int result = excelInfoMapper.addImportExcelRecordBatch(excelInfoDOList);

    // TODO: es 操作
    // Iterable<ImportExcelDoc> excelDocs =
    // excelInfoRepository.saveAll(excelInfoDOList);
    return result;
  }

  @Override public List<ImportExcelModel> exportSameReceiverAndPhoneAndAddress() {
    List<ExcelInfoDO> excelInfoDOList =
        excelInfoMapper.selectSameReceiverAndPhoneAndAddress();

    List<ImportExcelModel> importExcelModels = excelInfoDOList.stream().map(excelInfoDO -> {
      ImportExcelModel itemModel = this.convertModelFromDataObject(excelInfoDO);
      return itemModel;
    }).collect(Collectors.toList());

    return importExcelModels;
  }

  @Override public List<ImportExcelModel> exportDiffReceiverAndPhoneAndAddress() {
    List<ExcelInfoDO> excelInfoDOList =
        excelInfoMapper.selectDiffReceiverAndPhoneAndAddress();
    List<ImportExcelModel> importExcelModels = excelInfoDOList.stream().map(excelInfoDO -> {
      ImportExcelModel importExcelModel = this.convertModelFromDataObject(excelInfoDO);
      return importExcelModel;
    }).collect(Collectors.toList());
    return importExcelModels;
  }
 

  /**
   * 将 ImportExcelModel 转为 ExcelInfoDO
   *
   * @return ExcelInfoDO
   */
  private ExcelInfoDO convertImportExcelModel2ImportExcelDO(ImportExcelModel importExcelModel) {
    if (importExcelModel == null) {
      return null;
    }
    ExcelInfoDO excelInfoDO = new ExcelInfoDO();
    BeanUtils.copyProperties(importExcelModel, excelInfoDO);
    return excelInfoDO;
  }

  /**
   * 将 ExcelInfoDO 转为 ImportExcelModel
   *
   * @return ImportExcelModel
   */
  private ImportExcelModel convertModelFromDataObject(ExcelInfoDO excelInfoDO) {
    ImportExcelModel importExcelModel = new ImportExcelModel();
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
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY.getMessage(),
          ResultCode.FILE_NOT_EMPTY.getCode());
    }
    String fileName = file.getOriginalFilename();
    if (!fileName.endsWith(SUBFIX_FILE_NAME)) {
      throw new BusinessException(ResultCode.FILE_NOT_SUPPORT.getMessage(),
          ResultCode.FILE_NOT_SUPPORT.getCode());
    }
    if (file.getSize() > FILE_SIZE) {
      throw new BusinessException(ResultCode.FILE_MUCH.getMessage(),
          ResultCode.FILE_MUCH.getCode());
    }
  }

  /**
   * 转换 model from excel file
   *
   * @throws IOException
   */
  private List<ImportExcelModel> convertReadExcelToModel(MultipartFile file,
      UploadInfoModel uploadInfoModel)
      throws IOException {

    ExcelOperator excelOperator = new ExcelOperator();
    excelOperator.setSnowflakeIdWorker(snowflakeIdWorker);

    List<ImportExcelModel> importExcelModels = excelOperator.importExcelFile(file.getInputStream(), uploadInfoModel.getPlatform());

    return importExcelModels;
  }
}
