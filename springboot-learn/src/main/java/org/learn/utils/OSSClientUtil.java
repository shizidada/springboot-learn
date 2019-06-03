package org.learn.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.learn.common.api.ResultCode;
import org.learn.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * Aliyun OSS
 */
@Slf4j
public class OSSClientUtil {

    //阿里云API 内或外网域名
    private static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    //阿里云API的密钥Access Key ID
    private static final String ACCESS_KEY_ID = "LTAI8GpKG1CZsv4S";
    //阿里云API的密钥Access Key Secret
    private static final String ACCESS_KEY_SECRET = "p7FhsSiX31CWCfLNVmExP2AkaAOS4Z";
    //阿里云API的bucket名称
    private static final String BACKET_NAME = "xiaotaohua";
    //阿里云API的文件夹名称
    private static final String FOLDER_NAME = "xiaotaohua/";

    private OSSClient ossClient;

    public OSSClientUtil() {
        ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     * @throws BusinessException
     */
    public void uploadImage2Oss(String url) throws BusinessException, IOException {
        File fileOnServer = new File(url);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(inputStream, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new BusinessException(ResultCode.IMAGE_UPLOAD_FAILED);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public String uploadImage2Oss(MultipartFile file) throws BusinessException, IOException {
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_SIZE_BIG);
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param inputStream 文件流
     * @param fileName    文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream inputStream, String fileName) {
        String result = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER_NAME + fileName, inputStream, objectMetadata);
            result = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImageUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(FOLDER_NAME + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BACKET_NAME, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间
     * @return
     */
    public String createBucketName(String bucketName) {
        //存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建存储空间
            Bucket bucket = ossClient.createBucket(bucketName);
            log.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     *
     * @param bucketName 存储空间
     */
    public void deleteBucket(String bucketName) {
        ossClient.deleteBucket(bucketName);
        log.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     *
     * @param ossClient  oss连接
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名如"example/"
     * @return 文件夹名
     */
    public String createFolder(OSSClient ossClient, String bucketName, String folder) {
        //文件夹名
        final String keySuffixWithSlash = folder;
        //判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            log.info("创建文件夹成功");
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"example/"
     * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public void deleteFile(String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
        log.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    //测试
    public static void main(String[] args) throws FileNotFoundException {
        OSSClientUtil ossClientManager = new OSSClientUtil();
        //上传文件
        String fileStr = "/Users/taohua/Pictures/15699356.jpeg";
        String[] fileArray = fileStr.split(",");
        for (String filename : fileArray) {
            File file = new File(filename);
            InputStream inputStream = new FileInputStream(file);
            String fileName = file.getName();
            fileName = new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
            String md5Key = ossClientManager.uploadFile2OSS(inputStream, fileName);
            String imgUrl = ossClientManager.getImageUrl(fileName);
            log.info("md5Key: {}", md5Key);
            log.info("imgUrl : {}", imgUrl);
        }
    }
}
