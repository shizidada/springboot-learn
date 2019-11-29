package org.excel.operator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.excel.operator.entity.ImportExcelDO;
import org.excel.operator.es.document.ImportExcelDoc;
import org.excel.operator.es.repository.ImportExcelRepository;
import org.excel.operator.mapper.ImportExcelMapper;
import org.excel.operator.service.ImportExcelService;
import org.excel.operator.service.model.ImportExcelModel;
import org.excel.operator.util.PageInfoUtils;
import org.excel.operator.util.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class ImportExcelServiceImpl implements ImportExcelService {

  @Resource
  private ImportExcelMapper importExcelMapper;

  @Resource
  private ImportExcelRepository importExcelRepository;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public Map<String, Object> selectAll(ImportExcelModel importExcelModel) {
    if (importExcelModel.getPageNum() > 0 && importExcelModel.getPageSize() > 0) {
      PageHelper.startPage(importExcelModel.getPageNum(), importExcelModel.getPageSize());
    }
    ImportExcelDO importExcelDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
    List<ImportExcelDO> list = importExcelMapper.selectAll(importExcelDO);
    PageInfo page = new PageInfo<>(list);
    Map<String, Object> basePageInfo = PageInfoUtils.getBasePageInfo(page);
    return basePageInfo;
  }

  @Override public ImportExcelModel selectByPrimaryKey(Long id) {
    ImportExcelDO importExcelDO = importExcelMapper.selectByPrimaryKey(id);
    ImportExcelModel importExcelModel = this.convertModelFromDataObject(importExcelDO);
    return importExcelModel;
  }

  @Override public ImportExcelModel selectByImportExcel(ImportExcelModel importExcelModel) {
    ImportExcelDO importExcelDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
    importExcelDO = importExcelMapper.selectByImportExcel(importExcelDO);
    ImportExcelModel excelModel = this.convertModelFromDataObject(importExcelDO);
    return excelModel;
  }

  @Override public int addImportExcelRecord(ImportExcelModel importExcelModel) {
    ImportExcelDO importExcelDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
    return importExcelMapper.addImportExcelRecord(importExcelDO);
  }

  @Override public int addImportExcelRecordBatch(List<ImportExcelModel> importExcelModels) {
    importExcelModels = importExcelModels.stream().map(importExcelMode -> {
      importExcelMode.setId(snowflakeIdWorker.nextId());
      return importExcelMode;
    }).collect(Collectors.toList());

    List<ImportExcelDO> importExcelDOList = importExcelModels.stream().map(importExcelModel -> {
      ImportExcelDO importExcelDO = this.convertImportExcelModel2ImportExcelDO(importExcelModel);
      return importExcelDO;
    }).collect(Collectors.toList());

    List<ImportExcelDoc> importExcelDocs = importExcelModels.stream().map(importExcelModel -> {
      ImportExcelDoc importExcelDoc = this.convertModel2Doc(importExcelModel);
      return importExcelDoc;
    }).collect(Collectors.toList());

    // es 操作
    // Iterable<ImportExcelDoc> excelDocs =
    importExcelRepository.saveAll(importExcelDocs);
    return importExcelMapper.addImportExcelRecordBatch(importExcelDOList);
  }

  @Override public List<ImportExcelModel> exportSameReceiverAndPhoneAndAddress() {
    List<ImportExcelDO> importExcelDOList =
        importExcelMapper.selectSameReceiverAndPhoneAndAddress();

    List<ImportExcelModel> importExcelModels = importExcelDOList.stream().map(importExcelDO -> {
      ImportExcelModel itemModel = this.convertModelFromDataObject(importExcelDO);
      return itemModel;
    }).collect(Collectors.toList());

    return importExcelModels;
  }

  @Override public List<ImportExcelModel> exportDiffReceiverAndPhoneAndAddress() {
    List<ImportExcelDO> importExcelDOList =
        importExcelMapper.selectDiffReceiverAndPhoneAndAddress();
    List<ImportExcelModel> importExcelModels = importExcelDOList.stream().map(importExcelDO -> {
      ImportExcelModel importExcelModel = this.convertModelFromDataObject(importExcelDO);
      return importExcelModel;
    }).collect(Collectors.toList());
    return importExcelModels;
  }

  private ImportExcelDoc convertModel2Doc(ImportExcelModel model) {
    ImportExcelDoc doc = new ImportExcelDoc();
    doc.setId(model.getId());
    doc.setIccid(model.getIccid());
    doc.setOperators(model.getOperators());
    doc.setPhone(model.getPhone());
    doc.setPlatform(model.getPlatform());
    doc.setAddress(model.getAddress());
    doc.setReceiver(model.getReceiver());
    doc.setCreateTime(model.getCreateTime());
    doc.setUpdateTime(model.getUpdateTime());
    return doc;
  }

  /**
   * 将 ImportExcelModel 转为 ImportExcelDO
   *
   * @return ImportExcelDO
   */
  private ImportExcelDO convertImportExcelModel2ImportExcelDO(ImportExcelModel importExcelModel) {
    if (importExcelModel == null) {
      return null;
    }
    ImportExcelDO importExcelDO = new ImportExcelDO();
    BeanUtils.copyProperties(importExcelModel, importExcelDO);
    return importExcelDO;
  }

  /**
   * 将 ImportExcelDO 转为 ImportExcelModel
   *
   * @return ImportExcelModel
   */
  private ImportExcelModel convertModelFromDataObject(ImportExcelDO importExcelDO) {
    ImportExcelModel importExcelModel = new ImportExcelModel();
    BeanUtils.copyProperties(importExcelDO, importExcelModel);
    return importExcelModel;
  }
}
