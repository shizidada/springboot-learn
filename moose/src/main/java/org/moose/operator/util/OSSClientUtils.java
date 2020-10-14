package org.moose.operator.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.configure.properties.OSSProperties;
import org.moose.operator.constant.OSSConstants;
import org.moose.operator.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-14 22:26:22:26
 * @see org.moose.operator.util
 */
@Slf4j
public class OSSClientUtils {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private OSSProperties ossProperties;

  public void setOssProperties(OSSProperties ossProperties) {
    this.ossProperties = ossProperties;
  }

  public String uploadFile(MultipartFile file, String fileName) {
    return this.uploadFile(file, OSSConstants.ROOT_BUCKET_NAME_KEY, fileName);
  }

  public String uploadFile(MultipartFile file, String bucketName, String fileName) {

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
      InputStream inputStream = file.getInputStream();

      String uuid = UUID.randomUUID().toString();
      String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
      String fileDir = bucketName + uuid + "." + suffix;

      PutObjectRequest putObjectRequest =
          new PutObjectRequest(ossProperties.getBucketName(), fileDir, inputStream);

      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
      putObjectRequest.setMetadata(objectMetadata);
      PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
      return "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/" + fileDir;
    } catch (Exception e) {
      e.printStackTrace();
      String message =
          String.format("[%s %s]", ResultCode.FILE_UPLOAD_ERROR.getMessage(), e.getMessage());
      throw new BusinessException(message, ResultCode.FILE_UPLOAD_ERROR.getCode());
    } finally {
      // 关闭 OSSClient
      ossClient.shutdown();
    }
  }
}
