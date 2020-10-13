package org.moose.operator.web.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.configure.properties.OSSProperties;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.web.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author taohua
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

  @Resource
  protected OSSProperties ossProperties;

  @Resource
  private ObjectMapper objectMapper;

  @Override public void uploadFile(MultipartFile file) {
    if (ObjectUtils.isEmpty(file)) {
      throw new BusinessException(ResultCode.FILE_NOT_EMPTY);
    }

    String originalFilename = file.getOriginalFilename();
    if (StringUtils.isEmpty(originalFilename)) {
      throw new BusinessException(ResultCode.FILE_LEGITIMATE_ERROR);
    }

    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。 强烈建议您创建并使用RAM账号进行API访问或日常运维， 请登录
     * https://ram.console.aliyun.com 创建RAM账号。
     */
    // 创建OSSClient实例。
    OSS ossClient =
        new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
            ossProperties.getAccessKeySecret());

    // 创建PutObjectRequest对象。
    try {
      String uuid = UUID.randomUUID().toString();
      String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
      String fileName = uuid + suffix;
      // TODO:
      String fileDir = "user/avatar/" + fileName;

      PutObjectRequest putObjectRequest =
          new PutObjectRequest(ossProperties.getBucketName(), fileDir, file.getInputStream());

      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
      putObjectRequest.setMetadata(objectMetadata);
      PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
      log.info("文件上传成功 : {} url: {}", objectMapper.writeValueAsString(putObjectResult),
          "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/" + fileDir);
    } catch (IOException e) {
      e.printStackTrace();
      throw new BusinessException(
          String.format("[%s %s]", ResultCode.FILE_UPLOAD_ERROR.getMessage(), e.getMessage()),
          ResultCode.FILE_UPLOAD_ERROR.getCode());
    } finally {
      // 关闭 OSSClient
      ossClient.shutdown();
    }
  }
}
