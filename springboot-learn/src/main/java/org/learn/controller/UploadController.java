//package org.learn.controller;
//
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class UploadController {
//
//    @Value("${upload.path}")
//    private String filePath;
//
//    @RequestMapping("/uploadFile")
//    @ResponseBody
//    public String getFile(MultipartFile file) {
//        String originalName = file.getOriginalFilename();
//        String fileName = originalName.substring(originalName.lastIndexOf("."));
//        fileName = UUID.randomUUID() + fileName;
//        try {
//            InputStream inputStream = file.getInputStream();
//            File targetFile = new File(filePath);
//            if (!targetFile.exists()) {
//                targetFile.mkdirs();
//            }
//            FileOutputStream outputStream = new FileOutputStream(targetFile + "\\" + fileName);
//            IOUtils.copy(inputStream, outputStream);
//            inputStream.close();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileName;
//    }
//}
