package org.moose.operator.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.configure.properties.OSSProperties;
import org.moose.operator.constant.CommonConstants;
import org.moose.operator.constant.OSSConstants;
import org.moose.operator.model.dto.FileUploadDTO;
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

  private OSSProperties ossProperties;

  private SnowflakeIdWorker snowflakeIdWorker;

  public void setOssProperties(OSSProperties ossProperties) {
    this.ossProperties = ossProperties;
  }

  public void setSnowflakeIdWorker(SnowflakeIdWorker snowflakeIdWorker) {
    this.snowflakeIdWorker = snowflakeIdWorker;
  }

  private OSS getOssClient() {
    // 创建OSSClient实例。
    return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
        ossProperties.getAccessKeySecret());
  }

  public FileUploadDTO uploadFile(MultipartFile file, String fileName) {
    return this.uploadFile(file, OSSConstants.ROOT_BUCKET_NAME_KEY, fileName);
  }

  /**
   * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。 强烈建议您创建并使用RAM账号进行API访问或日常运维， 请登录
   * https://ram.console.aliyun.com 创建RAM账号。
   */
  public FileUploadDTO uploadFile(MultipartFile file, String bucketName, String fileName) {
    // 创建OSSClient实例。
    OSS ossClient = this.getOssClient();

    FileUploadDTO fileUploadDTO = new FileUploadDTO();

    // 创建PutObjectRequest对象。
    try {
      InputStream inputStream = file.getInputStream();

      String uuid = UUID.randomUUID().toString();
      String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
      String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      // 用户上传文件时指定的前缀。
      String fileDir = bucketName + format + "/" + uuid + "." + suffix;

      PutObjectRequest putObjectRequest =
          new PutObjectRequest(ossProperties.getBucketName(), fileDir, inputStream);

      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
      putObjectRequest.setMetadata(objectMetadata);
      PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
      String fileUrl = "https://moose-plus.oss-cn-shenzhen.aliyuncs.com/" + fileDir;
      fileUploadDTO.setAttachmentUrl(fileUrl);
      fileUploadDTO.setTag(putObjectResult.getETag());
      fileUploadDTO.setAttachmentId(String.valueOf(snowflakeIdWorker.nextId()));
      fileUploadDTO.setSuccess(CommonConstants.SUCCESS);
    } catch (Exception e) {
      log.error("upload file to oss fail : {}", e.getMessage());
      // throw new BusinessException(message, ResultCode.FILE_UPLOAD_ERROR.getCode());
      // TODO: throw error or other
      String errorMessage = ResultCode.FILE_UPLOAD_ERROR.getMessage();
      String message = String.format("[%s %s]", errorMessage, e.getMessage());
      fileUploadDTO.setSuccess(CommonConstants.FAIL);
      fileUploadDTO.setErrMessage(message);
    } finally {
      // 关闭 OSSClient
      ossClient.shutdown();
    }
    return fileUploadDTO;
  }

  public Map<String, String> getSignature() {

    // 创建OSSClient实例。
    OSS ossClient = this.getOssClient();
    Map<String, String> respMap = null;
    try {
      String accessId = ossProperties.getAccessKeyId();
      String endpoint = ossProperties.getEndpoint();
      String bucket = ossProperties.getBucketName();
      // host的格式为 bucketname.endpoint
      String host = "https://" + bucket + "." + endpoint;
      String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      // 用户上传文件时指定的前缀。
      String dir = format + "/";

      long expireTime = 30;
      long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
      Date expiration = new Date(expireEndTime);
      // PostObject 请求最大可支持的文件大小为5 GB，即 CONTENT_LENGTH_RANGE 为5*1024*1024*1024。
      PolicyConditions policyConds = new PolicyConditions();
      policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
      policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

      String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
      byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
      String encodedPolicy = BinaryUtil.toBase64String(binaryData);
      String postSignature = ossClient.calculatePostSignature(postPolicy);

      respMap = new LinkedHashMap<>();
      respMap.put("accessid", accessId);
      respMap.put("policy", encodedPolicy);
      respMap.put("signature", postSignature);
      respMap.put("dir", dir);
      respMap.put("host", host);
      respMap.put("expire", String.valueOf(expireEndTime / 1000));
      respMap.put("code", String.valueOf(200));
    } catch (Exception e) {
      respMap = new LinkedHashMap<>();
      respMap.put("code", String.valueOf(400));
      respMap.put("message", e.getMessage());
    } finally {
      ossClient.shutdown();
    }
    return respMap;
  }
}
